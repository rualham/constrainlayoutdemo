:多渠道打包 -p <闪屏图片文件夹> -i <渠道apk文件夹>
:例如replace_splash_with_apk.bat -p C:\Users\zengzifeng\Desktop\result -i D:\studioprojects\combine\scripts\apk_package\build\outputs
:不额外输出apk,直接替换原apk
cd ../../..
@call python %~dp0\..\pkgentrance.py -P ReplaceSplashWithChannelApks %*
cd %~dp0
pause