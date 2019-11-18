:生成基准apk(发布) debug=false online环境 assembleRelease cmDebug=false
cd ../../..
@call python %~dp0\..\pkgentrance.py -P BaselinePackage -d false -b assembleRelease -C false -c %*
cd %~dp0
pause