:生成基准apk(测试) debug=true online环境 assembleRelease cmDebug=true
cd ../../..
@call python %~dp0\..\pkgentrance.py -P BaselinePackage -d true -b assembleRelease -C true -c %*
cd %~dp0
pause