#!/usr/bin/env python
# -*- coding: utf-8 -*-
import getopt
import os
import sys

from pkgphase import *
from pkgphase import pkgenv
from pkgtool import *


def main():
    phase = ''
    workspace = ''
    input_apk = ''
    clean = False
    opts, args = getopt.getopt(sys.argv[1:], "chr:d:e:b:l:p:P:w:i:I:C:")
    for op, value in opts:
        if op == '-r':
            pkgenv.ENV.rootDir = value
        elif op == '-d':
            pkgenv.ENV.lvmmDebug = value
        # elif op == '-e':
        #     pkgenv.ENV.lvmmEnv = value
        elif op == '-b':
            pkgenv.ENV.lvmmBuildType = value
        elif op == '-l':
            pkgenv.ENV.channellist = value
        elif op == '-p':
            pkgenv.ENV.pic_dir = value
        elif op == '-h':
            print 'apk package utils'
            sys.exit()
        elif op == '-c':
            clean = True
            # sys.exit()
        elif op == '-P':
            phase = value
        elif op == '-w':
            workspace = value
        elif op == '-i':
            input_apk = value
        elif op == '-I':
            pkgenv.ENV.lvmmBuildId = value
        elif op == '-C':
            pkgenv.ENV.cmDebug = value

    # 在工程目录中使用可不设置-w -r
    if not workspace:
        workspace = os.getcwd()
    if not pkgenv.ENV.rootDir:
        pkgenv.ENV.rootDir = os.path.join(workspace, 'scripts/apk_package')

    os.chdir(pkgenv.ENV.rootDir)

    # logger('pkgentrance', 'pkgenv.ENV.lvmmEnv:{}'.format(pkgenv.ENV.lvmmEnv))
    logger('pkgentrance', 'pkgenv.ENV.lvmmDebug:{}'.format(pkgenv.ENV.lvmmDebug))
    logger('pkgentrance', 'pkgenv.ENV.lvmmBuildType:{}'.format(pkgenv.ENV.lvmmBuildType))
    logger('pkgentrance', 'pkgenv.ENV.channellist:{}'.format(pkgenv.ENV.channellist))
    logger('pkgentrance', 'pkgenv.ENV.pic_dir:{}'.format(pkgenv.ENV.pic_dir))
    logger('pkgentrance', 'pkgenv.ENV.rootDir:{}'.format(pkgenv.ENV.rootDir))
    logger('pkgentrance', 'pkgenv.ENV.lvmmBuildId:{}'.format(pkgenv.ENV.lvmmBuildId))
    logger('pkgentrance', 'pkgenv.ENV.cmDebug:{}'.format(pkgenv.ENV.cmDebug))
    logger('pkgentrance', 'workspace:{}'.format(workspace))

    if clean:
        clean_build()

    if input_apk:
        logger('pkgentrance', 'output:{}'.format(BuildChain(globals()[phase]).proceed(input_apk)))
    else:
        logger('pkgentrance', 'output:{}'.format(BuildChain(globals()[phase]).proceed(workspace)))


def build_baseline_apk(workspace):
    return BaselinePackage(
        PrimitivePackage(PrePackage(workspace).build().build_output).build().build_output).build().build_output


def dev_dist(workspace):
    return DevelopPackage(BaselinePackage(
        PrimitivePackage(
            PrePackage(workspace).build().build_output).build().build_output).build().build_output).build().build_output


class BuildChain(object):
    def __init__(self, phase):
        self._phase = phase

    def proceed(self, ipt):
        # print 'proceed ipt:{}'.format(ipt)
        if self._phase.depend:
            return self.build(self._phase, BuildChain(self._phase.depend).proceed(ipt))
        else:
            return self.build(self._phase, ipt)

    @staticmethod
    def build(phase, ipt):
        return phase(ipt).build().build_output


class CleanDevPackage(BasePackagePhase):
    depend = DevelopPackage

    def build(self):
        clean_build()
        self.build_output = self.build_input
        return self

    def self_space(self):
        pass


class ChannelPackageWithExistApk(BasePackagePhase):
    depend = None

    def build(self):
        self.build_output = ChannelPackage(self.build_input).build().build_output
        return self

    def self_space(self):
        pass


class ReplaceSplashWithChannelApks(BasePackagePhase):
    depend = None

    def self_space(self):
        pass

    def build(self):
        self.build_output = SplashPackage(self.build_input).build().build_output
        return self


if __name__ == '__main__':
    main()

    # print BuildChain(DevelopDist).proceed(r'D:\studioprojects\combine')
    # print BuildChain(CleanDevBuild).proceed(r'D:\studioprojects\combine')

    # pkgenv.ENV.rootDir = 'aa'
    # print os.getcwd()
    # print open(os.path.join(os.getcwd(), 'channel_list'), 'rb').read()
    # os.chdir(r'D:\test')
    # print os.getcwd()
    # print os.path.join(os.getcwd(), 'channel_list')
    # print open(os.path.join(os.getcwd(), 'channel_list'), 'rb').read()
