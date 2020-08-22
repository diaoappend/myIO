package com.diao.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 23:15
 * @description: 测试NIO的服务端
 * @version: 1.0
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();

        //ServerSocketChannel绑定一个端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8090));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把ServerSocketChannel注册到Selector，关系事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true){
            //如果select()方法返回的selectionKey数量为0，说明还没有客户端请求连接，这里的参数1000表示等待1秒
            if (selector.select(1000)==0){
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //如果返回的selectionKey数量大于0，则说明有客户端连接该服务端，那么就获取所有的selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //通过selectionkey反向获取SocketChannel
            //遍历selectionKey集合
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                //获取到SocketChannel
                SelectionKey selectionKey = keyIterator.next();
                //根据获取到的不同类型的事件做相应的处理
                //如果是连接事件
                if(selectionKey.isAcceptable()){
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功，生成一个SocketChannel "+socketChannel.hashCode());
                    //将当前的SocketChannel注册到Selector上，绑定一个读取事件，同时给这个SocketChannel创建一个Buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                //如果是读取事件
                }else if(selectionKey.isReadable()){
                    //获取读取事件对应的Channel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //获取到该Channel关联的Buffer
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端"+ new String(buffer.array()));
                }

                //从selectionKey集合中移除已经处理的key，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
