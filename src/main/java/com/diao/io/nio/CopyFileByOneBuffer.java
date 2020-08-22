package com.diao.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 21:17
 * @description: 通过一个buffer将文件1的内容拷贝到文件2
 * @version: 1.0
 */
public class CopyFileByOneBuffer {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("1.txt");
        FileChannel fileInputChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("2.txt");
        FileChannel fileOutputChannel = fos.getChannel();
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){//循环读取
            //清空缓冲，这一步很重要！！！
            //如果不清空缓冲，position和limit会一直相等，read一直为0，从而陷入死循环（read永远不会等于-1）
            buffer.clear();
            //将数据读取到Buffer
            int read = fileInputChannel.read(buffer);
            if(read==-1){
                break;
            }
            //转换读写模式
            buffer.flip();
            //将Buffer中的数据写入outputChannel
            fileOutputChannel.write(buffer);
        }
        fos.close();
        fis.close();

    }
}
