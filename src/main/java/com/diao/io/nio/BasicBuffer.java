package com.diao.io.nio;

import java.nio.IntBuffer;

/**
 * @author: chenzhidiao
 * @date: 2020/8/17 22:39
 * @description:测试buffer的使用
 * @version: 1.0
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个buffer，容量为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(8);

        //向buffer中存放数据
        for (int i = 0;i<5;i++){
            intBuffer.put(i*2);

        }
        //读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
