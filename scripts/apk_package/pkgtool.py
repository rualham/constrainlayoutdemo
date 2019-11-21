#!/usr/bin/env python
# -*- coding: utf-8 -*-

import functools
import hashlib
import logging
import os
import platform
import shutil
import stat
import subprocess
import time
import zipfile

ANDROID_BUILD_TOOLS_VERSION = '28.0.3'


def execute_cmd(*args):
    if platform.system() == 'Windows':
        subprocess.check_output(args, shell=True)
    else:
        subprocess.check_output(args, shell=False)


def profile(tag):
    r"""
    log输出函数执行时间
    :param tag:
    :return:
    """
    logging.basicConfig(level=logging.INFO)
    log = logging.getLogger(tag)
    log.addHandler(logging.NullHandler())

    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            log.info(u'start {}'.format(func.__name__))
            start = time.time()
            result = func(*args, **kw)
            log.info(u'end {}(cost {:.2f}s)'.format(func.__name__, time.time() - start))
            return result

        return wrapper

    return decorator


def logger(tag, msg):
    logging.basicConfig(level=logging.INFO)
    log = logging.getLogger(tag)
    log.addHandler(logging.NullHandler())
    log.info(msg)


@profile('buildtool')
def write_channel_info(apk, channel):
    r"""
    写入渠道，之后需要重签名，但是不需要重新对齐
    :param apk:
    :param channel:
    :return:
    """
    logger('buildtool', 'write_channel_info to {} ...'.format(apk))

    zipped = zipfile.ZipFile(apk, 'a', zipfile.ZIP_DEFLATED)
    empty_channel_file = "META-INF/lvmchannel{}".format(channel)
    zipped.writestr(empty_channel_file, '')
    zipped.close()

    logger('buildtool', 'write_channel_info {} done'.format(apk))


def file_sha1(file_path):
    with open(file_path, 'rb') as f:
        sha1 = hashlib.sha1()
        sha1.update(f.read())
        return sha1.hexdigest()


@profile('buildtool')
def sign_v1_v2(source_apk, signed_apk, keystore, pwd):
    # apksigner sign --ks lvmama_android.keystore --out signed.apk --v1-signing-enabled true --v2-signing-enabled true -v --ks-pass pass:198798 ANDROID_MEIZU_7.8.0.apk
    apksigner_exe = os.path.join(os.environ['ANDROID_HOME'],
                                 'build-tools/{}/{}'.format(ANDROID_BUILD_TOOLS_VERSION,
                                                            'apksigner.bat' if platform.system() == 'Windows' else 'apksigner'))
    logger('buildtool', 'apksigner_exe:{}'.format(apksigner_exe))
    execute_cmd(apksigner_exe, 'sign',
                '--ks', keystore,
                '--out', signed_apk,
                '--v1-signing-enabled', 'true',
                '--v2-signing-enabled', 'true',
                '--ks-pass', 'pass:{}'.format(pwd),
                source_apk)


@profile('buildtool')
def zipalign(infile, outfile):
    # zipalign [-f] [-v] 4 infile.apk outfile.apk 对齐
    # zipalign -c -v 4 existing.apk 验证

    zipalign_exe = os.path.join(os.environ['ANDROID_HOME'],
                                'build-tools/{}/{}'.format(ANDROID_BUILD_TOOLS_VERSION,
                                                           'zipalign.exe' if platform.system() == 'Windows' else 'zipalign'))
    logger('buildtool', 'zipalign_exe:{}'.format(zipalign_exe))
    execute_cmd(zipalign_exe, '-f', '4', infile, outfile)


def copy_file(src, tgt):
    with open(src, 'rb') as source:
        with open(tgt, 'wb') as target:
            target.write(source.read())


def search_files_with_ext(dir_, suffix_arr):
    """搜索目录下指定后缀文件，返回文件的完整路径"""
    result = []
    for root, dirs, file_names in os.walk(dir_):
        d = [os.path.join(root, file_name) for file_name in file_names if os.path.splitext(file_name)[1] in suffix_arr]
        if len(d) > 0:
            result.extend(d)
    return result


@profile('buildtool')
def delete_dir(path):
    for root, dirs, file_names in os.walk(path):  # 删除文件
        for file_name in file_names:
            ab_path = os.path.join(root, file_name)
            if not os.access(ab_path, os.W_OK):
                os.chmod(ab_path, stat.S_IWUSR)
            os.remove(ab_path)
    for root, dirs, file_names in os.walk(path):  # 删除文件夹
        for dir_name in dirs:
            ab_path = os.path.join(root, dir_name)
            if not os.access(ab_path, os.W_OK):
                os.chmod(ab_path, stat.S_IWUSR)
            shutil.rmtree(ab_path)
    shutil.rmtree(path)


def clean_build():
    from pkgphase import pkgenv
    if os.path.isdir(os.path.join(pkgenv.ENV.rootDir, 'build')):
        delete_dir(os.path.join(pkgenv.ENV.rootDir, 'build'))


if __name__ == '__main__':
    # clean_build()
    print  os.path.join(os.environ['ANDROID_HOME'],
                        'build-tools/{}/{}'.format(ANDROID_BUILD_TOOLS_VERSION,
                                                   'zipalign.exe' if platform.system() == 'Darwin' else 'zipalign'))
    import os

    print os.path.join(os.environ['ANDROID_HOME'],
                       'build-tools/{}/{}'.format(ANDROID_BUILD_TOOLS_VERSION,
                                                  'apksigner.bat' if platform.system() == 'Windows' else 'apksigner'))
