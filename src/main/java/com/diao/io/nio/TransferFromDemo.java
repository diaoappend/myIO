package com.diao.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 21:30
 * @description: 测试transferFrom方法，将数据从一个通道中复制到另一个通道中
 * @version: 1.0
 */
public class TransferFromDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("1.txt");
        FileChannel fileInputChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("4.txt");
        FileChannel fileOutputChannel = fos.getChannel();

        //使用transferFrom方法将输入管道的数据拷贝到输出管道，指定开始位置和拷贝的大小
        fileOutputChannel.transferFrom(fileInputChannel,0,fileInputChannel.size());

        fos.close();
        fis.close();
    }
}
