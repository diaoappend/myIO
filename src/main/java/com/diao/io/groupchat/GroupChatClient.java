package com.diao.io.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author: chenzhidiao
 * @date: 2020/8/22 15:44
 * @description:
 * @version: 1.0
 */
public class GroupChatClient {
    private final String HOST="127.0.0.1";
    private final int PORT=8090;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient()throws IOException {
        selector= Selector.open();
        //连接服务器
        socketChannel=SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        //注册读取事件到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username+"is ok ...");

    }

    //向服务器发送消息
    public void send(String ms){
        ms = username + "说："+ms;
        try {
            socketChannel.write(ByteBuffer.wrap(ms.getBytes()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readInfo(){
        try {
            int read = selector.select();
            if(read>0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        //得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);

                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            }else{
                //System.out.println("没有可以用的通道");
            }

        }catch (Exception e){

        }
    }

    public static void main(String[] args) throws Exception {
         final GroupChatClient groupChatClient = new GroupChatClient();

        //启动一个线程,每隔3秒读取从服务器发送的数据
        new Thread(){
            public void run(){
                while (true){
                    groupChatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String ms = scanner.nextLine();
            groupChatClient.send(ms);
        }
    }
}
