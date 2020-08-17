package com.diao.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: chenzhidiao
 * @date: 2020/8/17 23:44
 * @description:
 * @version: 1.0
 */
public class FileChannelDemo {
    public static void main(String[] args) throws Exception {
        read();

    }

    public static void writeFile() throws Exception{
        //FileChannel测试本地文件写数据
        String str = "hello diao ,this is FileChannel test";
        //创建一个输出流
        FileOutputStream fos = new FileOutputStream("d:\\nio\\filechannel.txt");
        //通过输出流获取文件Channel
        java.nio.channels.FileChannel fileChannel = fos.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放到ByteBuffer
        byteBuffer.put(str.getBytes());

        //对ByteBuffer切换读写模式
        byteBuffer.flip();
        //将ByteBuffer中的数据写入FileChannel
        fileChannel.write(byteBuffer);
        //关闭流
        fos.close();
    }

    //通过FileChannel读取本地文件
    public static void read() throws Exception{
        //创建一个文件输入流
        FileInputStream fis = new FileInputStream(new File("d:\\nio\\filechannel.txt"));
        //获取FileChannel
        FileChannel fileChannel = fis.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //从通道读取数据
        fileChannel.read(byteBuffer);
        //将buffer中的字节转为字符串并输出
        System.out.println(new String(byteBuffer.array()));
        fis.close();

    }
}
