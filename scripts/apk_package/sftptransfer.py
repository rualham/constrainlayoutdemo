#!/usr/bin/env python
# -*- coding: utf-8 -*-
import paramiko
from pkgtool import *

DEFAULT_HOST = '192.168.0.97'
DEFAULT_USERNAME = 'root'
DEFAULT_PWD = 'admin508956'


def upload(remote_path, local_path, host_name=DEFAULT_HOST, user_name=DEFAULT_USERNAME, password=DEFAULT_PWD):
    logger('sftp', 'upload {} to {}'.format(local_path, remote_path))
    transport = paramiko.Transport((host_name, 22))
    transport.connect(username=user_name, password=password)
    with paramiko.SFTPClient.from_transport(transport) as sftp:
        sftp.put(local_path, remote_path)
    logger('sftp', 'upload end')


def download(remote_path, local_path, host_name=DEFAULT_HOST, user_name=DEFAULT_USERNAME, password=DEFAULT_PWD):
    logger('sftp', 'download {} to {}'.format(remote_path, local_path))
    transport = paramiko.Transport((host_name, paramiko.config.SSH_PORT))
    transport.connect(username=user_name, password=password)
    with paramiko.SFTPClient.from_transport(transport) as sftp:
        sftp.get(remote_path, local_path)
    logger('sftp', 'download done')


def execute_cmd(cmd, host_name=DEFAULT_HOST, user_name=DEFAULT_USERNAME, password=DEFAULT_PWD):
    with paramiko.SSHClient() as client:
        client.load_system_host_keys()
        client.connect(hostname=host_name, username=user_name, password=password)
        stdin, stdout, stderr = client.exec_command(cmd)
        return stdout.read(), stderr.read()


if __name__ == '__main__':
    # upload('/data/nfsroot/client/android/apks/signed2.apk', r'D:\test\signed2.apk')
    # download('/data/nfsroot/client/android/apks/signed2.apk', 'D:\\test\\aaa.apk')
    # stdout, stderr = execute_cmd('ls -l /data/nfsroot/client/android/apks')
    # stdout, stderr = execute_cmd('ls -l "/data/nfsroot/client/android/apks" | grep ".apk" | wc -l')  # 统计apk个数
    # print 'stdout:' + stdout
    # print 'stderr:' + stderr

    import pkgphase

    pkgphase.SplashPackage
