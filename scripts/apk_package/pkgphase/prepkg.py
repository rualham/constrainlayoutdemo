#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os

from basepkgphase import *
from pkgtool import *


class PrePackage(BasePackagePhase):
    r"""
    打包准备，生成'local.properties' 输出工程目录
    """
    depend = None

    def self_space(self):
        return None

    def build(self):
        self.create_local_prop()
        self.build_output = self.build_input
        return self

    def create_local_prop(self):
        if not self.build_input or not os.path.isdir(self.build_input):
            raise ValueError('illegal workspace:{}'.format('None' if not self.build_input else self.build_input))
        # if not os.path.dirname()
        prop = os.path.join(self.build_input, 'local.properties')
        if not os.path.isfile(prop) or os.path.getsize(prop) <= 0:
            logger('PrePackage', 'local.properties not exist creating...')
            with open(prop, 'wb') as f:
                f.write('sdk.dir={}'.format(os.environ['ANDROID_HOME']))


if __name__ == '__main__':
    pre = PrePackage('fake_project', None)
    pre.build()
    # print pre.build_input
    # pre.build()
    print pre.build_output
