package com.diao.io.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: chenzhidiao
 * @date: 2020/8/22 10:38
 * @description: 群聊小程序的服务端
 * @version: 1.0
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT=8090;

    public GroupChatServer(){
        try{
            selector = Selector.open();
            listenChannel= ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);
            //将listenChannel注册到selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //监听
    public void listen(){
        try{
            //循环处理
            while (true){
                int count = selector.select();
                if (count>0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出selectionKey
                        SelectionKey selectionKey = iterator.next();
                        //如果监听到连接事件
                        if (selectionKey.isAcceptable()){
                            //获取SocketChannel
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            //提示某个用户上线
                            System.out.println(socketChannel.getLocalAddress()+"上线");
                            //如果是读事件
                        }else if(selectionKey.isReadable()){
                            //读取数据
                            readData(selectionKey);
                        }

                        //将当前的key从集合中删除，防止重复处理
                        iterator.remove();
                    }
                }else{
                    System.out.println("等待...");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }

    }

    private void readData(SelectionKey selectionKey){
        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            //取到关联的channel
            channel=(SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count!=-1){

            }
            //如果读取到了数据
            if (count>0){
                String ms = new String(buffer.array());
                System.out.println("form 客户端"+ms);
                //向其他客户端转发消息(排除自己)
                transMessage(ms,channel);
            }
        }catch (IOException e){//如果发生异常，说明转发的客户端已经离线
            try {
                System.out.println(channel.getRemoteAddress()+"离线了。。。");
                //如果某个客户端离线了，要取消注册
                selectionKey.cancel();
                //然后关闭cannel
                channel.close();

            }catch (IOException e2){
                e2.printStackTrace();
            }
        }

    }

    //转发消息给其他客户端（通道）
    public void transMessage(String ms,SocketChannel self)throws IOException{
        System.out.println("服务器转发消息中...");
        //获取所有注册到selecor中的channel
        Set<SelectionKey> keys = selector.keys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()){
            //获取每个客户端对应的selectionKey
            SelectionKey selectionKey = iterator.next();
            //获取该selectionKey绑定的channel
            Channel channel = selectionKey.channel();
            //排除自己（不必转发给自己）
            if (channel instanceof SocketChannel&& channel!=self){
                //转型
                SocketChannel dest = (SocketChannel)channel;
                //将数据写入buffer
                ByteBuffer byteBuffer = ByteBuffer.wrap(ms.getBytes());
                //将buffer数据写入通道
                dest.write(byteBuffer);
            }
        }

    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
