package com.diao.io.nio;

import java.nio.ByteBuffer;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 21:44
 * @description: 将普通Buffer转为只读Buffer
 * @version: 1.0
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i =0;i<64;i++){
            buffer.put((byte) i);
        }
        buffer.flip();
        //将普通Buffer转为只读Buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        //测试读取，只能读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }

        readOnlyBuffer.put((byte)5);//这里会报错
    }
}
