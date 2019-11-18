#!/usr/bin/env python
# coding=UTF-8
'''
Created by lihong on 2018/06/04.
用于找出项目中的所有相似度比较高的图片，用于相同图片的去重
使用时需要安装numpy和opencv，分别执行pip install numpy和pip install opencv-python
原理是利用感知哈希和汉明距离来判断图片的相似度，参考的标准通过standard_degree设定大于此值就被认定为不一样，否则认为是一样的图片
'''
import cv2
import numpy as np
import os
import os.path

standard_degree = 3
picList = []
picHashDic = {}
resultDic = {}

moduleList = ["app", "lvmm-search", "lvmm-nearby", "lvmm-travels", "lvmm-ticket", "lvmm-special", "lvmm-ship",
              "lvmm-share",
              "lvmm-search", "lvmm-route", "lvmm-reactnative", "lvmm-qrcode", "lvmm-pay", "lvmm-mine", "lvmm-main",
              "lvmm-hybrid", "lvmm-hotel", "lvmm-foundation",
              "lvmm-coupon", "lvmm-comminfo", "lvmm-comment", "lvmm-account", "other"]


def file_extension(path):
    return os.path.splitext(path)[1]


def handler_file(file):
    suffix = file_extension(file).lower()
    if suffix == ".png" or suffix == ".jpg":
        picList.append(file)


def handler_dir(path):
    parents = os.listdir(path)
    for parent in parents:
        if parent.startswith('.') or parent == 'build':
            continue
        child = os.path.join(path, parent)
        if os.path.isdir(child):
            handler_dir(child)
        else:
            handler_file(child)


def get_degree(image1, image2):
    if picHashDic.has_key(image1):
        hash1 = picHashDic[image1]
    else:
        hash1 = perceptual_hash(image1)
        picHashDic[image1] = hash1
    if picHashDic.has_key(image2):
        hash2 = picHashDic[image2]
    else:
        hash2 = perceptual_hash(image2)
        picHashDic[image2] = hash2
    return get_hamming_distance(hash1, hash2)


def perceptual_hash(image):
    image = cv2.imread(image)
    cv2.imshow('image', image)
    image = cv2.resize(image, (32, 32))
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    # 将灰度图转为浮点型，再进行dct变换
    dct = cv2.dct(np.float32(gray))
    # 取左上角的8*8，这些代表图片的最低频率
    # 这个操作等价于c++中利用opencv实现的掩码操作
    # 在python中进行掩码操作，可以直接这样取出图像矩阵的某一部分
    dct_roi = dct[0:8, 0:8]
    return get_hash(dct_roi)


def get_hash(image):
    average = np.mean(image)
    hash = []
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            if image[i, j] > average:
                hash.append(1)
            else:
                hash.append(0)
    return hash


def get_hamming_distance(hash1, hash2):
    num = 0
    for index in range(len(hash1)):
        if hash1[index] != hash2[index]:
            num += 1
    return num


def get_key(hash):
    key = ""
    for n in hash:
        key = key + str(n)
    return key


def search_same_pic():
    for i in range(len(picList)):
        sfile = picList[i]
        if sfile == "done":
            continue
        resultDic[get_key(perceptual_hash(sfile))] = [sfile]
        picList[i] = "done"
        for j in range(len(picList)):
            if sfile == "done":
                continue
            try:
                degree = get_degree(sfile, picList[j])
            except Exception, e:
                degree = 10
                print e.message
            global standard_degree
            if degree < standard_degree:
                resultDic[get_key(perceptual_hash(sfile))].append(picList[j])
                picList[j] = "done"

    modulePicDic = {}
    for module in moduleList:
        modulePicDic[module] = [[]]
    for value in resultDic.values():
        if len(value) > 1:
            firstPic = value[0]
            isHas = False
            for key in modulePicDic.keys():
                if key in firstPic:
                    modulePicDic[key].append(value)
                    isHas = True
                    break
            if not isHas:
                modulePicDic["other"].append(value)

    for moduleKey in modulePicDic.keys():
        print moduleKey
        for pics in modulePicDic[moduleKey]:
            if len(pics) > 0:
                print pics
        print


if __name__ == '__main__':
    path = os.getcwd()
    handler_dir(path)
    search_same_pic()
