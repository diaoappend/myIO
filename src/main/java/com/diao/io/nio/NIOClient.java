package com.diao.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 23:43
 * @description: 测试NIO的客户端
 * @version: 1.0
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8090);
        //如果客户端连接服务端不成功
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("如果没有连接成功，客户端不会阻塞，可以做其他工作...");
            }
        }
        //如果连接成功
        System.out.println("客户端连接服务端成功");
        String str="客户端向服务端发送数据";
        //创建Buffer,这里的wrap()方法，可以根据输入数据自动创建Buffer，而不用我们手动指定Buffer的大小
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据，将Buffer数据写入SocketChannel
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
