:生成基准apk并发布到服务器,更新下载页 debug=true online环境 assembleDebug cmDebug=true
cd ../../..
@call python %~dp0\..\pkgentrance.py -P DevelopPackage -d true -b assembleDebug -C true -c %*
cd %~dp0
pause