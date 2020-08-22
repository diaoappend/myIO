package com.diao.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 22:14
 * @description: 测试MappedBuffer，可以直接在内存中对文件进行修改，即操作系统并不需要拷贝一次，该内存指堆外内存
 * @version: 1.0
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("5.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 表示使用读写模式
         * 参数2: 0 表示可以修改的起始位置
         * 参数3: 5 表示映射到内存的大小，即5.txt的多少个字节映射到内存，可以直接修改的范围就是5，即从0开始，最多修改到下标为4的字节
         */
        MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedBuffer.put(0,(byte)'d');
        mappedBuffer.put(3,(byte)'9');

        randomAccessFile.close();


    }
}
