#!/usr/bin/env python
# -*- coding: UTF-8 -*-

"""
导入Tinify
 pip install --upgrade tinify
"""

import os
import os.path
import tinify

# 去https://tinypng.com/developers申请API KEY
tinify.key = "你申请到的API KEY"		# API KEY

# 压缩的核心
def compress_core(input_file, output_file, img_width, img_height):
	source = tinify.from_file(input_file)
	if img_width is not -1:
		resized = source.resize(
			method = "scale",
			width  = img_width,
			height = img_height
		)
		resized.to_file(output_file)
	else:
		source.to_file(output_file)

# 压缩一个文件夹下的图片
def compress_path(path, width, height):
	print ("compress_path-------------------------------------")
	if not os.path.isdir(path):
		print ("not a dir!")
		return
	else:
		input_path = path 				# 源路径
		output_path = path+"/tiny" 		# 输出路径
		print ("input_path = %s" %input_path)
		print ("output_path = %s" %output_path)

		for root, dirs, files in os.walk(input_path):
			print ("root = %s" %root)
			print ("dirs = %s" %dirs)
			print ("files = %s" %files)
			for name in files:
				file_name, file_suffix = os.path.splitext(name)
				if file_suffix == '.png' or file_suffix == '.jpg' or file_suffix == '.jpeg':
					output_full_path = output_path + root[len(input_path):]
					output_full_name = output_path + '/' + name
					if os.path.isdir(output_full_path):
						pass
					else:
						os.mkdir(output_full_path)
					compress_core(root + '/' + name, output_full_name, width, height)
				break					# 仅遍历当前目录

if __name__ == "__main__":
	compress_path(os.getcwd(), -1, -1)