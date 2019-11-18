Tinker生成修复包 -- 本地bug分支

一：

先执行下面脚本命令

```
python D:\Codes\lvmmgit\lvmm-release\scripts\apk_package\pkgentrance.py -P BaselinePackage -c -d false -b assembleRelease -I '785' -C false
```
(命令中相关参数可阅读scripts\apk_package\README.md；
 -I '785' 需要参考基线包中的lvmmBuildId
 -C false CmDebug是否开启，release包false)

上面会在app中创建bakApk及outputs文件夹

scripts\apk_package\build也会生成apk

打印信息是上面脚本执行的结果
```
INFO:BaselinePackage:start build
INFO:BaselinePackage:copy file from:build/intermediates/primitive\release_8.2.40_F.apk to:build/intermediates/baseline\release_8.2.40_F.apk
INFO:buildtool:start write_channel_info
INFO:buildtool:write_channel_info to build/intermediates/baseline\release_8.2.40_F.apk ...
INFO:buildtool:write_channel_info build/intermediates/baseline\release_8.2.40_F.apk done
INFO:buildtool:end write_channel_info(cost 0.08s)
INFO:buildtool:start sign_v1_v2
INFO:buildtool:apksigner_exe:C:\AndroidSDk\build-tools/28.0.3/apksigner.bat
INFO:buildtool:end sign_v1_v2(cost 5.07s)
INFO:BaselinePackage:end build(cost 5.29s)
INFO:pkgentrance:output:build/intermediates/baseline\release_8.2.40_F.apk
```

二：

将基线包对应的三个文件 xx.apk, xx-mapping.txt, xx-R.txt 复制到上面bakApk里

三：

a)修改出现问题的代码

b)primitivepkg.py中注释掉
```
#, '-DapkDst={}'.format(self.self_space())
#, 'clean'
#, 'cleanBuildCache'
```
c)tinker-settings.gradle
```
gitRev 修改成固定值，需要查看基线包中AndroidManifext.xml中的tinker_id值
baseApkName 修改成基线包的具体名称
```

四：

在Android Studio中Terminal 或在本地分支代码主目录中执行cmd，然后执行下面脚本命令
```
gradlew.bat -DlvmmDebug=false -DlvmmBuildId='785' -DcmDebug=false tinkerPatchRelease --stacktrace
```
生成的修复包路径是 app\build\outputs\apk\tinkerPatch\release

tempPatchedDexes文件夹中的classes.dex可查看修改的代码

patch_signed_7zip.apk 是7z压缩后的修复包

最后将生成的xx7z.apk 重命名 约定修复包格式
`版本号_tinker_patch.jar`
如 8.2.40_tinker_patch.jar