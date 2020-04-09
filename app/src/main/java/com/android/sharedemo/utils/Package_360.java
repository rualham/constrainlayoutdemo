package com.android.sharedemo.utils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Package_360 {
    /*关键代码*/
    public static void main(String[] args) throws Exception {
//      写入渠道号
      args = "-path D:/111.apk -outdir D:/111/ -contents googleplay;m360; -password 12345678".split(" ");
//      查看工具程序版本号
//      args = "-version".split(" ");
//      读取渠道号
//      args = "-path D:/111_m360.apk -password 12345678".split(" ");
        long time = System.currentTimeMillis();
        String cmdPath = "-path";
        String cmdOutdir = "-outdir";
        String cmdContents = "-contents";
        String cmdPassword = "-password";
        String cmdVersion = "-version";
        String help = "用法：java -jar MCPTool.jar [" + cmdPath + "] [arg0] [" + cmdOutdir + "] [arg1] [" + cmdContents + "] [arg2] [" + cmdPassword + "] [arg3]"
                + "\n" + cmdPath + "        APK文件路径"
                + "\n" + cmdOutdir + "      输出路径（可选），默认输出到APK文件同一级目录"
                + "\n" + cmdContents + "    写入内容集合，多个内容之间用“;”分割（linux平台请在“;”前加“\\”转义符），如：googleplay;m360; 当没有" + cmdContents + "”参数时输出已有文件中的contents"
                + "\n" + cmdPassword + "    加密密钥（可选），长度8位以上，如果没有该参数，不加密"
                + "\n" + cmdVersion + " 显示MCPTool版本号"
                + "\n例如："
                + "\n写入：java -jar MCPTool.jar -path D:/test.apk -outdir ./ -contents googleplay;m360; -password 12345678"
                + "\n读取：java -jar MCPTool.jar -path D:/test.apk -password 12345678";

        if (args.length == 0 || args[0] == null || args[0].trim().length() == 0) {
            System.out.println(help);
        } else {
            if (args.length > 0) {
                if (args.length == 1 && cmdVersion.equals(args[0])) {
//                    System.out.println("version: " + VERSION_1_1);
                    System.out.println("version: " + "1.0.0");
                } else {
                    Map<String, String> argsMap = new LinkedHashMap<String, String>();
                    for (int i = 0; i < args.length; i += 2) {
                        if (i + 1 < args.length) {
                            if (args[i + 1].startsWith("-")) {
                                throw new IllegalStateException("args is error, help: \n" + help);
                            } else {
                                argsMap.put(args[i], args[i + 1]);
                            }
                        }
                    }
                    System.out.println("argsMap = " + argsMap);
                    File path = argsMap.containsKey(cmdPath) ? new File(argsMap.get(cmdPath)) : null;
                    String parent = path == null ? null : (path.getParent() == null ? "./" : path.getParent());
                    File outdir = parent == null ? null : new File(argsMap.containsKey(cmdOutdir) ? argsMap.get(cmdOutdir) : parent);
                    String[] contents = argsMap.containsKey(cmdContents) ? argsMap.get(cmdContents).split(";") : null;
                    String password = argsMap.get(cmdPassword);
                    if (path != null) {
                        System.out.println("path: " + path);
                        System.out.println("outdir: " + outdir);
                        if (contents != null && contents.length > 0) {
                            System.out.println("contents: " + Arrays.toString(contents));
                        }
                        System.out.println("password: " + password);
                        if (contents == null || contents.length == 0) { // 读取数据；
//                            System.out.println("content: " + readContent(path, password));
                        } else { // 写入数据；
                            String fileName = path.getName();
                            int dot = fileName.lastIndexOf(".");
                            String prefix = fileName.substring(0, dot);
                            String suffix = fileName.substring(dot);
                            for (String content : contents) {
                                File target = new File(outdir, prefix + "_" + content + suffix);
//                                if (nioTransferCopy(path, target)) {
//                                    write(target, content, password);
//                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("time：" + (System.currentTimeMillis() - time));
    }
}
