package com.diao.io.zerocopy;

import com.sun.security.ntlm.Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: chenzhidiao
 * @date: 2020/8/22 21:19
 * @description: 测试使用NIO的零拷贝的服务端
 * @version: 1.0
 */
public class NewIOServer {
    public static void main(String[] args) throws Exception{
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8090);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(inetSocketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        System.out.println("服务端已启动，等待连接...");
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("获取连接");
            int readcount=0;
            while (-1 != readcount){
                try {
                    readcount=socketChannel.read(byteBuffer);
                }catch (Exception e){
                    break;
                }
            }
            byteBuffer.remaining();//position变成0，mark标志作废

        }

    }
}
