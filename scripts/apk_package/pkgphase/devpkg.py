#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import os
import re
import sftptransfer as sftp
# added at 2017-2-16 14:50:30
from qrcode_util import make_qr

import pkgenv
from baselinepkg import BaselinePackage
from basepkgphase import *
from pkgtool import *

DEFAULT_REMOTE_APKS_DIR = '/data/nfsroot/client/android/apks/lvmama'
# 二维码图片存放目录
DEFAULT_URL = 'http://192.168.0.96/android/apks/lvmama'
DEFAULT_REMOTE_INDEX = '/data/nfsroot/client/android/package/index.php'


class DevelopPackage(BasePackagePhase):
    r"""
    开发阶段打包，包括打时间戳，上传，更新apk下载页 输出打好时间戳的apk路径
    """
    depend = BaselinePackage

    def self_space(self):
        return 'build/intermediates/developdist'

    @profile('DevelopPackage')
    def build(self):
        if not os.path.exists(self.self_space()):
            os.makedirs(self.self_space())

        dvd_apk = os.path.join(self.self_space(), os.path.basename(self.build_input))
        logger('DevelopPackage', 'copy from:{} to:{}'.format(self.build_input, dvd_apk))
        copy_file(self.build_input, dvd_apk)
        renamed_apk_path = self.rename(dvd_apk)
        # 上传apk
        sftp.upload(DEFAULT_REMOTE_APKS_DIR + '/{}'.format(os.path.basename(renamed_apk_path)), renamed_apk_path)
        # 生成并上传二维码图片
        remote_url = DEFAULT_URL + '/{}'.format(os.path.basename(renamed_apk_path))
        img_name = os.path.basename(renamed_apk_path).replace('.apk', '.png')
        qrcode_url = DEFAULT_URL + '/{}'.format(os.path.basename(img_name))
        self.upload_qrcode(remote_url, renamed_apk_path, img_name)

        self.update_index_page(os.path.basename(renamed_apk_path), qrcode_url)
        self.build_output = renamed_apk_path
        return self

    @profile('qrcode')
    def upload_qrcode(self, url, apk_name, img_name):
        make_qr(url, img_name)
        sftp.upload(DEFAULT_REMOTE_APKS_DIR + '/{}'.format(os.path.basename(img_name)), img_name)

    @profile('developdist')
    def update_index_page(self, new_apk_name, img_url):
        """
        更新index.php
        :param new_apk_name:
        :return:
        """

        dvd_dir = 'build/intermediates/developdist'
        local_php = os.path.join(dvd_dir, 'index_old.php')
        sftp.download(DEFAULT_REMOTE_INDEX, local_php)
        head = ''
        tail = ''
        head_line_num = 0
        with open(local_php, 'rb') as php_index:
            lines = php_index.readlines()
            for line in lines:
                # print line
                head += line
                head_line_num += 1
                if line.strip() == '</thead>':
                    break
                    # else:
                    #     head += os.linesep
            # print 'headLineNum={}'.format(headLineNum)
            for line in lines[head_line_num:]:
                tail += line
        version_name = get_apk_version_name(self.build_input)
        debug_txt = 'DEBUG' if pkgenv.ENV.lvmmBuildType == 'assembleDebug' else 'RELEASE'
        new_tr = self.generate_new_tr(version_name, debug_txt,
                                      '../apks/lvmama/{}'.format(new_apk_name), pkgenv.ENV.lvmmBuildId, img_url)

        new_php = head + new_tr + os.linesep + tail
        # print new_php
        new_php_path = 'build/intermediates/developdist/index_new.php'
        with open(new_php_path, 'wb') as index_new:
            index_new.write(new_php)
        sftp.upload(DEFAULT_REMOTE_INDEX, new_php_path)

    @profile('developdist')
    def generate_new_tr(self, version_name, status, path, build_id, img_url):
        with open('res/tr_template.txt', 'rb') as template:
            return template.read().replace('#version#', version_name).replace('#status#', status).replace('#path#',
                                                                                                          path).replace(
                '#buildId#', build_id).replace('#imgUrl#', img_url)

    @staticmethod
    def rename(apk_path):
        r"""
        重命名apk 打上时间戳
        'DEBUG_7.8.1_onlineT.apk' ->
        'DEBUG_7.8.1_onlineT_1611071436.apk'
        :return:
        """
        new_apk_name = os.path.basename(apk_path).replace('.apk', '_{}.apk'.format(
            datetime.datetime.now().strftime("%y%m%d%H%M")))
        path = os.path.join(os.path.dirname(apk_path), new_apk_name)
        os.rename(apk_path, path)
        return path


def get_apk_version_name(apk_path):
    pattern = re.compile('_[^_]*_', re.IGNORECASE)
    return pattern.findall(os.path.basename(apk_path))[0][1:-1]


if __name__ == '__main__':
    dvd = DevelopPackage('build/intermediates/baseline/DEBUG_7.8.1_onlineT.apk', None)
    dvd.dev_dist()
    # dvd.rename()
    # dvd.update_index_page('DEBUG_7.8.1_onlineT_1611071436.apk')
    # print datetime.datetime.now().strftime("%y%m%d%H%M")

    # print get_apk_version_name(dvd.build_input)
