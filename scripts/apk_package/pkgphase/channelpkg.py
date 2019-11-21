#!/usr/bin/env python
# -*- coding: utf-8 -*-
import re
from multiprocessing import Pool

import devpkg
import pkgenv
from baselinepkg import BasePackagePhase
from baselinepkg import BaselinePackage
from pkgtool import *


class ChannelPackage(BasePackagePhase):
    r"""
    打渠道包 包括从配置文件读取渠道号列表，替换apk中渠道号，对齐，签名 输出为渠道apk文件夹
    """
    UNZIPALIGN_SUFFIX = '_unzipalign'

    depend = BaselinePackage

    def __init__(self, build_input, keystore='res/lvmama_android.keystore',
                 keystore_pwd='198798'):
        BasePackagePhase.__init__(self, build_input)
        self._keystore = keystore
        self._keystore_pwd = keystore_pwd

    def self_space(self):
        return 'build/intermediates/channelpkg'

    def build(self):
        if not os.path.exists(self.self_space()):
            os.makedirs(self.self_space())
        version_name = devpkg.get_apk_version_name(self.build_input)
        channels = read_channel_list(pkgenv.ENV.channellist)
        logger('ChannelPackage', 'channels={}'.format(channels))
        pool = Pool()
        for ch in channels:
            unzipalign_apk = os.path.join(self.self_space(),
                                          'ANDROID_{}_{}{}.apk'.format(ch, version_name, self.UNZIPALIGN_SUFFIX))
            pool.apply_async(repack_single_apk,
                             args=(unzipalign_apk, ch, self.build_input, self._keystore, self._keystore_pwd))
        pool.close()
        pool.join()
        self.build_output = 'build/outputs/apk'
        return self


@profile('ChannelPackage')
def repack_single_apk(unzipalign_apk, channel, source_apk, keystore, keystore_pwd):
    r"""加入渠道号并对齐,重签名"""
    logger('ChannelPackage', 'channel={} base_apk={}'.format(channel, source_apk))
    channeled_apk = reset_channel(source_apk, channel, unzipalign_apk)

    outputs = 'build/outputs/apk'
    if not os.path.exists(outputs):
        os.makedirs(outputs)
    zipaligned_apk = os.path.join(outputs,
                                  os.path.basename(channeled_apk.replace(ChannelPackage.UNZIPALIGN_SUFFIX, '')))
    zipalign(channeled_apk, zipaligned_apk)
    sign_v1_v2(zipaligned_apk, zipaligned_apk, keystore, keystore_pwd)
    return zipaligned_apk


@profile('ChannelPackage')
def read_channel_list(channel_file):
    with open(channel_file, 'rb') as f:
        channels = []
        in_comment_block = False
        lines = [line.strip() for line in f.readlines() if line.strip() and
                 not line.strip().startswith(r'//')]
        pattern = re.compile('\/\/.*', re.IGNORECASE)

        for line in lines:
            # print line
            if line.startswith(r'/*'):
                in_comment_block = True

            if line.endswith(r'*/'):
                in_comment_block = False
                continue
            if pattern.findall(line):
                line = line[:-len(pattern.findall(line)[0])]

            if not in_comment_block:
                channels.append(line)
        return channels


def reset_channel(source_apk, channel, target_apk=None, channel_prefix='META-INF/lvmchannel'):
    r"""新增或替换渠道"""
    if not target_apk:
        tmpzip = os.path.join(os.path.dirname(source_apk), file_sha1(source_apk) + '.apk')
    with zipfile.ZipFile(source_apk, 'r', zipfile.ZIP_DEFLATED) as zin:
        with zipfile.ZipFile(target_apk if target_apk else tmpzip, 'w', zipfile.ZIP_DEFLATED) as zout:
            for info in zin.infolist():
                content = zin.read(info.filename)
                if not info.filename.startswith(channel_prefix):
                    zout.writestr(info, content)
            zout.writestr(channel_prefix + channel, '')
    if not target_apk:
        os.remove(source_apk)
        os.rename(tmpzip, source_apk)
    return target_apk if target_apk else source_apk


if __name__ == '__main__':
    # ChannelPackage('res/channel_list.txt')
    # read_channel_list('res/channel_list.txt')
    # write_channel_info('build/DEBUG_7.8.1_229F_1611091003.apk',None)

    # print file_sha1('build/DEBUG_7.8.1_229F_1611091003.apk')
    # print file_sha1('build/tgt.apk')

    # update_zip('res/drawable-xxhdpi-v4/splash.jpg', open('build/splash.jpg', 'rb').read(),
    #            'build/DEBUG_7.8.1_229F_1611091003.apk',
    #            'build/tgt.apk', 'res/drawable-xxhdpi-v4/splash1.jpg')
    # update_zip('res/drawable-xxhdpi-v4/splash.jpg', open('build/splash.jpg', 'rb').read(),
    #            'build/DEBUG_7.8.1_229F_1611091003.apk')

    # sign_v1_v2('build/tgt.apk','build/tgt_s.apk','res/lvmama_android.keystore','198798')

    # sign(None,None)

    # update_zip('res/drawable-xxhdpi-v4/splashaa.jpg', open('build/splash.jpg', 'rb').read(),
    #            'build/DEBUG_7.8.1_229F_1611091003.apk')

    ChannelPackage('build/DEBUG_7.8.1_229F_1611091003.apk').build()


    # channel_prefix = r'/META-INF/lvmchannel'

    # print 'ANDROID_XIANXIAHD_7.8.1_unzipalign.apk'.replace('_unzipalign','')
    # pattern = re.compile(channel_prefix, re.IGNORECASE)
    # reset_channel('build/intermediates/apk/ANDROID_DEFAULT_7.8.1.apk', '888', 'build/tgt.apk')
    # reset_channel('build/tgt.apk', '999')
