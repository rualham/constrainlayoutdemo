#coding:utf-8
'''
Python将Url生成二维码图片

安装PIL：pip install pillow
安装qrcode：pip install qrcode
'''
 
__author__ = 'Jinlin'

import qrcode


#生成二维码图片
def make_qr(url,save):
    qr=qrcode.QRCode(
        version=5,  #生成二维码尺寸的大小 1-40  1:21*21（21+(n-1)*4）
        error_correction=qrcode.constants.ERROR_CORRECT_M, #L:7%的字码可被容错 M:15%的字码可被容错 Q:25%的字码可被容错 H:30%的字码可被容错
        box_size=10, #每个格子的像素大小
        border=1, #边框的格子宽度大小（默认是4）
    )
    qr.add_data(url)
    qr.make(fit=True)
 
    img=qr.make_image()
    img.save(save)
 
if __name__=='__main__':
    save_path='theqrcode.png' #生成后的保存文件
    str = '测试'
    # if sys.version_info < (3, ):
    #     str=raw_input('请输入要生成二维码的文本内容：')
    # else :
    #     str=input('请输入要生成二维码的文本内容：')
 
    make_qr(str, save_path)