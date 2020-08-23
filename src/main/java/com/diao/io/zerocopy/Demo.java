package com.diao.io.zerocopy;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: chenzhidiao
 * @date: 2020/8/22 16:55
 * @description:
 * @version: 1.0
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        File file = new File("d:test.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        byte[] arr = new byte[(int) file.length()];
        raf.read(arr);

        Socket socket = new ServerSocket(8090).accept();
        socket.getOutputStream().write(arr);
    }
}
