#!/usr/bin/env python
# coding=UTF-8

"""
Created by lihong on 2018/05/29.
用于查找项目中的大图，具体的标准可以通过maxLength的大小来设置
"""
import os
import os.path

# 100kb
maxLength = 100 * 1024
picCount = 0


def file_extension(path):
    return os.path.splitext(path)[1]


def handler_file(file):
    suffix = file_extension(file).lower()
    if suffix == ".png" or suffix == ".jpg":
        global picCount
        picCount += 1
        try:
            size = os.path.getsize(file)
            global maxLength
            if size > maxLength:
                print "big pic: %s size is %d" % (file, size)

        except Exception, e:
            print e.message


def handler_dir(path):
    parents = os.listdir(path)
    for parent in parents:
        if parent.startswith('.') or parent == 'build':
            continue
        child = os.path.join(path, parent)
        if os.path.isdir(child):
            handler_dir(child)
        else:
            handler_file(child)


def main():
    root = os.getcwd()
    os.chdir(root)
    handler_dir(root)
    print "%s%d" % ('total pic count is :', picCount)


if __name__ == "__main__":
    main()
