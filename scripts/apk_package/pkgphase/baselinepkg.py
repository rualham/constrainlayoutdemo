#!/usr/bin/env python
# -*- coding: utf-8 -*-

from basepkgphase import BasePackagePhase
from pkgtool import *
from primitivepkg import PrimitivePackage


class BaselinePackage(BasePackagePhase):
    r"""
    生成基准apk 包括设置默认渠道号，重签名 输出基准apk路径
    """
    depend = PrimitivePackage
    DEFAULT_CHANNEL = 'LVMM'
    #默认是LVMM，由于WIN8风格CMS是没有DEFAULT渠道的

    def self_space(self):
        return 'build/intermediates/baseline'

    @profile('BaselinePackage')
    def build(self):
        if not os.path.exists(self.self_space()):
            os.makedirs(self.self_space())

        self.copy_and_write_channel()
        # print 'zipalign in:{} out:{}'.format(self.output_apk().replace('.apk', '_unzipalign.apk'), self.output_apk())
        # os.rename(self.output_apk(), self.output_apk().replace('.apk', '_unzipalign.apk'))
        # zipalign(self.output_apk().replace('.apk', '_unzipalign.apk'), self.output_apk())
        self.resign()
        self.build_output = self.output_apk()
        return self

    def __init__(self, build_input, keystore='res/lvmama_android.keystore',
                 default_channel=DEFAULT_CHANNEL, keystore_pwd='198798'):
        BasePackagePhase.__init__(self, build_input)
        self._default_channel = default_channel
        self._keystore = keystore
        self._keystore_pwd = keystore_pwd

    def copy_and_write_channel(self):
        logger('BaselinePackage', 'copy file from:{} to:{}'.format(self.build_input, self.output_apk()))
        copy_file(self.build_input, self.output_apk())
        write_channel_info(self.output_apk(), self._default_channel)

    def resign(self):
        sign_v1_v2(self.output_apk(), self.output_apk(), self._keystore, self._keystore_pwd)

    def output_apk(self):
        return os.path.join(self.self_space(), os.path.basename(self.build_input))


if __name__ == '__main__':
    baseline = BaselinePackage('build/intermediates/primitive/DEBUG_7.8.1_onlineT_1611041021.apk')
    # # baseline.copy_and_write_channel()
    # # baseline.resign()
    baseline.build()

    # os.rename(r'D:\PycharmProjects\apk_package\build/intermediates/baseline\DEBUG_7.8.1_229F.apk',
    #           r'D:\PycharmProjects\apk_package\build/intermediates/baseline\DEBUG_7.8.1_229F_unzipalign.apk')
    # zipalign(r'D:\PycharmProjects\apk_package\build\intermediates\primitive\DEBUG_7.8.1_229F.apk', r'D:\PycharmProjects\apk_package\build\tgt.apk')
