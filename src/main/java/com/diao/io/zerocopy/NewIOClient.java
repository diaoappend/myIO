package com.diao.io.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: chenzhidiao
 * @date: 2020/8/22 21:25
 * @description: 测试Java NIO零拷贝的客户端
 * @version: 1.0
 */
public class NewIOClient {
    public static void main(String[] args)throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8090));
        String filename = "E:\\eula.1028.txt";
        //获取一个文件channel
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        //记录开始时间
        long startTime = System.currentTimeMillis();
        //在linux下一个transferTo方法就可以完成传输
        //在windows下使用transferTo方法一次只能传输8M，就需要分段传输文件，而且要注意传输时的位置

        //transferTo方法就是用了零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送字节总数："+transferCount+" 耗时：" + (System.currentTimeMillis()-startTime));

        fileChannel.close();

    }
}
