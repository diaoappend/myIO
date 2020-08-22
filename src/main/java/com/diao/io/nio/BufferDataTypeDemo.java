package com.diao.io.nio;

import java.nio.ByteBuffer;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 21:37
 * @description: 测试Buffer中存放数据的类型，存放时的类型和获取时的类型必须一一对应
 * 否则可能产生 java.nio.BufferUnderflowException 异常
 * @version: 1.0
 */
public class BufferDataTypeDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(512);
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('d');
        buffer.putShort((short)4);

        //读取
        buffer.flip();
        //取出数据时，数据类型的顺序必须和放入的一致
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());


    }
}
