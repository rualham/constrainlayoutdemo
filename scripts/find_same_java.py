#!/usr/bin/python
# -*- coding: UTF-8 -*-

"""
    Created by J!nl!n on 2016/10/20.
    Copyright © 1990-2016 J!nl!n™ Inc. All rights reserved.
"""

import os

names = set()

def check_file(name):
    if ".java" in name:
        if name in names:
            print name
        else:
            names.add(name)


def find_file(level, name):
    rrrrdir = level + name
    srcfiles = os.listdir(rrrrdir)
    for srcfile in srcfiles:
        srcfilepath = level + name + "/" + srcfile
        if os.path.isdir(srcfilepath):
            find_file(level + name + "/", srcfile)
        else:
            if "/." not in srcfilepath:
                if "/java" in srcfilepath:
                    if "build" not in srcfilepath:
                        if "Test" not in srcfilepath:
                            check_file(srcfile)


srcdir = os.getcwd() 
name1 = ""
find_file(srcdir, name1)
