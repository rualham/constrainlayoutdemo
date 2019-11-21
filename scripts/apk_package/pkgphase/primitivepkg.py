#!/usr/bin/env python
# -*- coding: utf-8 -*-
import getopt
import os
import platform
import sys

import pkgenv
from basepkgphase import *
from pkgtool import *
from prepkg import PrePackage


class PrimitivePackage(BasePackagePhase):
    r"""
    通过gradle打原始apk 需要在app build.gradle中设置apk输出目录为'System.properties['apkDst']' 输出原始apk路径
    """
    depend = PrePackage

    def self_space(self):
        return 'build/intermediates/primitive'

    def build(self):
        if not os.path.exists(self.self_space()):
            os.makedirs(self.self_space())
        self.build_primitive_apk()
        self.build_output = search_files_with_ext(self.self_space(), '.apk')[0]  # 'build/intermediates/primitive'中的apk
        return self

    @profile('PrimitiveBuild')
    def build_primitive_apk(self):
        """构建apk 通过gradle复制apk文件到build/primitive文件夹下"""
        gradlew = os.path.join(self.build_input, 'gradlew.bat' if platform.system() == 'Windows' else 'gradlew')
        st = os.stat(gradlew)
        if not platform.system() == 'Windows':
            logger('PrimitivePackage', 'Not windows system, chmod a+x of file:{}'.format(gradlew))
            os.chmod(gradlew, st.st_mode | stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH)  # chmod a+x
        execute_cmd(gradlew,
                    '-DlvmmDebug={}'.format(pkgenv.ENV.lvmmDebug)
                    # , '-DlvmmEnv={}'.format(pkgenv.ENV.lvmmEnv)
                    , '-DapkDst={}'.format(self.self_space())
                    , '-DlvmmBuildId={}'.format(pkgenv.ENV.lvmmBuildId)
                    , '-DcmDebug={}'.format(pkgenv.ENV.cmDebug)
                    , 'clean'
                    , 'cleanBuildCache'
                    , pkgenv.ENV.lvmmBuildType
                    , '--stacktrace'
                    , '-b'
                    , os.path.join(self.build_input, 'build.gradle'))


def main():
    opts, args = getopt.getopt(sys.argv[1:], "hi:o:")
    for op, value in opts:
        if op == '-i':
            ipt = value
        elif op == '-o':
            opt = value
        elif op == '-h':
            sys.exit()
    PrimitivePackage(ipt, opt).build_primitive_apk()


# python primitivepkg.py -i D:\studioprojects\combine -o D:\PycharmProjects\apk_package\build\intermediates\primitive
if __name__ == '__main__':
    main()
    # pb = PrimitiveBuild(r'D:\studioprojects\combine',
    #                     os.path.join(BuildEnv.ENV.rootDir, r'build/intermediates/primitive'))
    # pb.build_primitive_apk()
