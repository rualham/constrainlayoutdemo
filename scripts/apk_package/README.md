### apk打包脚本 ###
可用于打原始akp，基准apk，上传服务器并更新下载页，打渠道包，替换闪屏图

#### 环境 ####
1. `python2.7`
2. 安装`python`模块`paramiko`(上传服务器需要) 可以通过`pip install paramiko`
3. `ANDROID_HOME`环境变量，指向android-sdk目录,包含`25.0.0`版本的`build-tools`
4. 可正常编译的代码

#### 参数说明 ####
-P 编译阶段，PrimitivePackage，BaselinePackage等
-d app中的debug开关(手动在代码中修改过可能无效)
-b assembleDebug or assembleRelease
-c 编译之前清除`apk_package/build`文件夹，建议加上
-l 渠道列表文件
-i apk目录(使用已存在apk打渠道包或更换闪屏图时需要）
-I build id 在jenkins编译时使用其自带环境变量{BUILD_DISPLAY_NAME}
-C cm debug 开关，打开则关闭cm统计

#### 原始apk ####
*一般不使用,等同于gradlew -D参数 方式生成apk*
用`Android Studio`生成的原始`apk`,签名渠道等参数取决于工程`gradle`文件配置
在工程根目录下执行命令(参数可调整)
`python scripts/apk_package/pkgentrance.py -P PrimitivePackage -d false -b assembleDebug -C true -c`
输出在`apk_package/build/intermediates/primitive`文件夹中

#### 基准apk ####
用于上传到服务器做测试包或者打正式包，使用默认渠道，正式签名
在工程根目录下执行命令(参数可调整)
`python scripts/apk_package/pkgentrance.py -P BaselinePackage -d false -b assembleDebug -C false -c`
也可以通过双击执行`batch_scripts`目录下的`baseline`开头的脚本直接运行
输出在`apk_package/build/intermediates/baseline`文件夹中

#### 测试阶段apk ####
生成基准包、打上时间戳并上传到服务器，更新下载页，使用默认渠道(`DEFAULT`)，正式签名
在工程根目录下执行命令(参数可调整)
`python scripts/apk_package/pkgentrance.py -P DevelopPackage -d false -b assembleDebug -C true -c`
也可以通过双击执行`batch_scripts`目录下的`dev_dist.bat`直接运行
输出在`apk_package/build/intermediates/developdist`文件夹中
(需要注意电脑安装的杀毒或者卫士之类的安全软件提示时要选择 全部允许操作

#### 渠道包 ####
#### 1. 已经有原始apk ####
在工程根目录下执行命令(参数可调整)
`python scripts/apk_package/pkgentrance.py -P ChannelPackageWithExistApk -l <渠道列表文件> -i <原始apk>`
或者使用`channel_dist_with_apk.bat`脚本
在`batch_scripts`目录下执行
`channel_dist_with_apk.bat -l <渠道列表文件> -i <原始apk>`
#### 2. 从源码生成 ####
在工程根目录下执行命令(参数可调整)
`python scripts/apk_package/pkgentrance.py -P ChannelPackage -d false -b assembleRelease -l <渠道列表文件> -C false -c`

输出apk在`apk_package/build/outputs/apk`文件夹中

#### 闪屏图 ####
##### 1. 已经有渠道apk #####
将闪屏图重命名为 渠道+.jpg 例如：`LSTV2.jpg`放入同一文件夹
在工程根目录下执行命令(参数可调整)
`python scripts/apk_package/pkgentrance.py -P ReplaceSplashWithChannelApks -p <闪屏图片文件夹> -i <渠道apk文件夹> -c`
或者使用`replace_splash_with_apk.bat`脚本
在`batch_scripts`目录下执行
`replace_splash_with_apk.bat -p <闪屏图片文件夹> -i <渠道apk文件夹>`
不产生额外输出,将替换原apk

#### 2.从源码生成 ####
在工程根目录下执行命令(参数可调整)
`python scripts/apk_package/pkgentrance.py -P SplashPackage -d false -b assembleRelease -l <渠道列表文件> -p <闪屏图片文件夹> -C false -c`
输出apk在`apk_package/build/outputs/apk`文件夹中

####常用命令如下
注意是在`batch_scripts`目录下执行命令
(1)有原始apk，生成渠道包
channel_dist_with_apk.bat -l <渠道列表文件> -i <原始apk>

(2)有渠道apk，替换图片
replace_splash_with_apk.bat -p <闪屏图片文件夹> -i <渠道apk文件夹>