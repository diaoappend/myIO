package com.diao.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: chenzhidiao
 * @date: 2020/8/17 21:07
 * @description:
 * @version: 1.0
 */
public class BIODemo {
    public static void main(String[] args) throws IOException {
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建一个ServerSocket
        ServerSocket serverSocket = new ServerSocket(8090);
        System.out.println("服务器启动");
        while (true){
            //监听，等待客户端连接，此时会阻塞
            System.out.println("等待连接...");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程池
            executorService.execute(new Runnable() {
                public void run() {
                    //可以和客户端进行通信
                    handler(socket);
                }
            });

        }
    }

    //编写一个handler方法和客户端进行通讯
    public static void handler(Socket socket)  {
        try {
            System.out.println("线程信息 id="+Thread.currentThread().getId());
            System.out.println("线程信息 name="+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while (true){
                //如果没有读到数据，会一直阻塞
                int read = inputStream.read(bytes);
                //只要read不为-1,说明还能读到数据
                if (read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭和客户端的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
