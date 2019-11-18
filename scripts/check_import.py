#!/usr/bin/env python
# -*- coding: UTF-8 -*-

"""
 Created by  xns on 2017/2/10.
此脚本可以检测一个module中所有java文件中对符合特定规则的类依赖
可选输入参数：
--match：匹配的的关键字，如'com.lvmama.base'，若不输入则match="com.lvmama.base"
--root:仓库的根目录，如‘...\combine\repos\module_ticket’，若不输入则选取脚本本间路径位置
--out:输出文件路径，如不指定则选取当前脚本位置

使用示例：python check_import.py --root=D:\WorkSpace\lvmama\combine\repos\module_ticket --match=com.lvmama.util
"""
import getopt
import os

import sys

DEFAULT_MATCH = "com.lvmama.base"

EXCLUDE_MODULE = ["lib_base"]


def main():
    opts, args = getopt.getopt(sys.argv[1:], "h", ["match=", "root=", "out="])
    match = DEFAULT_MATCH
    root = os.getcwd()
    out = root + os.sep + "detection.txt"
    for key, value in opts:
        if key == "--match":
            match = value
        elif key == "--root":
            root = value
        elif key == "--out":
            out = value

    data_dict = perform_detection(root, match)
    write_result(data_dict, out)


def perform_detection(path, match):
    print("root=%s  \n match=%s" % (path, match))
    data_dict = {}
    for parent, dirnames, filenames in os.walk(path):
        # print("parent:%s,dirnames%s,filenames%s" % (parent, dirnames, filenames))
        if (os.sep + "build" + os.sep) in parent:
            continue
        if parent.endswith(os.sep + "res"):
            continue
        if is_exclude(parent):
            continue

        for file in filenames:
            if not file.endswith(".java"):
                continue
            else:
                file_path = parent + os.sep + file
                file_parser = FileParser(file_path, match)
                file_parser.parse()
                for reference in file_parser.match_list:
                    put_dict(data_dict, reference)

    print("detection finished")
    return data_dict


def is_exclude(parent):
    for module in EXCLUDE_MODULE:
        if (os.sep + module + os.sep) in parent:
            return True
        else:
            return False


def put_dict(data_dict, key):
    count = data_dict.get(key)
    if count:
        count += 1
    else:
        count = 1

    data_dict[key] = count


def write_result(result, out):
    if not result:
        raise Exception("无数据")
    sorted_result = sorted(result.items(), key=lambda item: item[1])
    writer = open(out, "w", encoding="utf-8")
    writer.write("class".ljust(80, " ") + "count\n")
    for item in sorted_result:
        key = item[0]
        value = item[1]
        print("class=%s,count=%d" % (key, value))
        aline = str(key).ljust(80, " ") + str(value) + "\n"
        writer.write(aline)

    writer.close()
    pass


class FileParser:
    _file = ''
    _match = ''

    def __init__(self, path, match=DEFAULT_MATCH):
        if os.path.isfile(path):
            self._file = path
            self._match = match
            self._match_list = []
        else:
            raise Exception("输入文件错误：%s" % path)

    @property
    def match_list(self):
        return self._match_list

    def parse(self):
        file = open(self._file, "r", encoding="utf-8")
        for line in file:
            if "class" in line:
                break
            if self._match in line and "import" in line:
                java_name = line.replace("import", "").replace("static", "").replace(";", "")
                self._match_list.append(java_name.strip())

        print("file%s parse finished" % self._file)
        file.close()


if __name__ == '__main__':
    main()
