#!/usr/bin/env python
# -*- coding: utf-8 -*-
from multiprocessing import Pool

from basepkgphase import BasePackagePhase
from channelpkg import ChannelPackage
from pkgtool import *
import re
import pkgenv


class SplashPackage(BasePackagePhase):
    r"""
    更换闪屏图，从指定文件夹中匹配图片，更换图片，对齐，签名，输出为渠道apk文件夹
    """
    depend = ChannelPackage

    def __init__(self, build_input, keystore='res/lvmama_android.keystore', keystore_pwd='198798'):
        BasePackagePhase.__init__(self, build_input)
        self._keystore = keystore
        self._keystore_pwd = keystore_pwd

    def self_space(self):
        return 'build/intermediates/splash'

    def build(self):
        if not os.path.exists(self.self_space()):
            os.makedirs(self.self_space())

        apks = search_files_with_ext(self.build_input, ['.apk'])
        pics = search_files_with_ext(pkgenv.ENV.pic_dir, ['.webp'])
        infos = get_repack_info(apks, pics)
        if not infos:
            logger('SplashPackage', 'no splash pic match')
        pool = Pool()
        for info in infos:
            unzipalignapk = os.path.join(self.self_space(),
                                         os.path.basename(info.apk_path).replace('.apk', '{}.apk'.format(
                                             ChannelPackage.UNZIPALIGN_SUFFIX)))
            pool.apply_async(repack_single_apk,
                             args=(info.apk_path, unzipalignapk, self._keystore, self._keystore_pwd, info.pic_path))
        pool.close()
        pool.join()
        self.build_output = 'build/outputs/apk'
        return self


class RepackInfo(object):
    def __init__(self, apk_path, pic_path, channel):
        self._apk_path = apk_path
        self._pic_path = pic_path
        self._channel = channel

    @property
    def apk_path(self):
        return self._apk_path

    @property
    def pic_path(self):
        return self._pic_path

    @property
    def channel(self):
        return self._channel


def repack_single_apk(source_apk, unzipalign_apk, keystore, keystore_pwd, pic):
    reset_splash(source_apk, pic, unzipalign_apk)
    outputs = 'build/outputs/apk'
    if not os.path.exists(outputs):
        os.makedirs(outputs)
    zipaligned_apk = os.path.join(outputs,
                                  os.path.basename(unzipalign_apk.replace(ChannelPackage.UNZIPALIGN_SUFFIX, '')))
    zipalign(unzipalign_apk, zipaligned_apk)
    sign_v1_v2(zipaligned_apk, zipaligned_apk, keystore, keystore_pwd)
    return zipaligned_apk


def get_repack_info(apks, pics):
    """对应apk和启动图 返回apk完整路径、图片完整路径、渠道"""
    infos = []
    for apk in apks:
        apk_name = os.path.basename(apk)
        pattern = re.compile('_\d*\..*.apk', re.IGNORECASE)  # 搜索后缀'_7.7.3.apk'
        suffix_arr = pattern.findall(apk_name)
        if len(suffix_arr) == 1:
            channel = apk_name[len('ANDROID_'):-len(suffix_arr[0])]
        else:
            raise ValueError('illegal apk name:{}'.format(apk_name))

        for pic in pics:
            pic_name = os.path.basename(pic)
            if pic_name.startswith(channel):
                infos.append(RepackInfo(apk, pic, channel))
    return infos


def reset_splash(source_apk, splash_pic, target_apk=None, splash_filename='res/drawable-xxhdpi-v4/splash.webp'):
    if not target_apk:
        tmpzip = os.path.join(os.path.dirname(source_apk), file_sha1(source_apk) + '.apk')
    with zipfile.ZipFile(source_apk, 'r', zipfile.ZIP_DEFLATED) as zin:
        with zipfile.ZipFile(target_apk if target_apk else tmpzip, 'w', zipfile.ZIP_DEFLATED) as zout:
            for info in zin.infolist():
                content = zin.read(info.filename)
                if info.filename == splash_filename:
                    with open(splash_pic, 'rb') as pic:
                        zout.writestr(info, pic.read())
                else:
                    zout.writestr(info, content)
    if not target_apk:
        os.remove(source_apk)
        os.rename(tmpzip, source_apk)
    return target_apk if target_apk else source_apk


if __name__ == '__main__':
    # reset_splash('build/DEBUG_7.8.1_229F_1611091003.apk', 'build/splash.jpg', 'build/tgt.apk')
    SplashPackage('build/outputs/apk', r'C:\Users\zengzifeng\Desktop\result').build()
