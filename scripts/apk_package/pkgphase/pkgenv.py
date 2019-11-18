#!/usr/bin/env python
# -*- coding: utf-8 -*-


class PackageEnv(object):
    def __init__(self):
        self._lvmmDebug = ''
        # self._lvmmEnv = ''
        self._lvmmBuildType = ''
        self._lvmmBuildId = ''
        self._cmDebug = ''
        self._rootDir = ''
        self._channellist = ''
        self._pic_dir = ''

    @property
    def lvmmDebug(self):
        return self._lvmmDebug

    @lvmmDebug.setter
    def lvmmDebug(self, lvmmDebug):
        self._lvmmDebug = lvmmDebug

    # @property
    # def lvmmEnv(self):
    #     return self._lvmmEnv

    # @lvmmEnv.setter
    # def lvmmEnv(self, lvmmEnv):
    #     self._lvmmEnv = lvmmEnv

    @property
    def lvmmBuildType(self):
        return self._lvmmBuildType

    @lvmmBuildType.setter
    def lvmmBuildType(self, lvmmBuildType):
        self._lvmmBuildType = lvmmBuildType

    @property
    def lvmmBuildId(self):
        return self._lvmmBuildId

    @lvmmBuildId.setter
    def lvmmBuildId(self, lvmmBuildId):
        self._lvmmBuildId = lvmmBuildId

    @property
    def cmDebug(self):
        return self._cmDebug

    @cmDebug.setter
    def cmDebug(self, cmDebug):
        self._cmDebug = cmDebug

    @property
    def rootDir(self):
        return self._rootDir

    @rootDir.setter
    def rootDir(self, rootDir):
        self._rootDir = rootDir

    @property
    def channellist(self):
        return self._channellist

    @channellist.setter
    def channellist(self, channellist):
        self._channellist = channellist

    @property
    def pic_dir(self):
        return self._pic_dir

    @pic_dir.setter
    def pic_dir(self, pic_dir):
        self._pic_dir = pic_dir


ENV = PackageEnv()
