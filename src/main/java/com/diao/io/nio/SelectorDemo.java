package com.diao.io.nio;

/**
 * @author: chenzhidiao
 * @date: 2020/8/19 22:43
 * @description: Selector和线程关联，每次调用select方法，会返回一个selectionKey的集合，
 * 每个selectionKey对应一个Channel，即返回哪些Channel对应的服务端文件描述符已准备好了
 * select方法调用后，会阻塞直到有selectionKey返回
 * @version: 1.0
 */
public class SelectorDemo {
    public static void main(String[] args) {

    }
}
