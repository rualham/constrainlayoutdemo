:多渠道打包 -l <渠道列表文件> -i <原始apk>
:例如channel_dist_with_apk.bat -l C:\Users\zengzifeng\Desktop\channel_list -i C:\Users\zengzifeng\Desktop\RELEASE_7.8.1_onlineF_1611110953.apk
:输出apk在/scripts/apk_package/build/outputs/apk
cd ../../..
@call python %~dp0\..\pkgentrance.py -P ChannelPackageWithExistApk %*
cd %~dp0
pause