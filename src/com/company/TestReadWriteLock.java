package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁
 * 1.ReadWriteLock：读写锁
 * 写写/读写 需要“互斥”
 * 读读 不需要“互斥”
 *
 * @author JOSWAY
 * @date 2021/2/24 8:48
 */
public class TestReadWriteLock {
    public static void main(String[] args) {

        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLockDemo.set((int) (Math.random() * 101));
            }
        }, "写锁：").start();


        for (int i = 1; i <= 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.get();
                }
            }, "读锁：").start();
        }


    }
}

class ReadWriteLockDemo {

    private int number = 0;

    ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    Lock readLock = reentrantReadWriteLock.readLock();
    Lock writeLock = reentrantReadWriteLock.writeLock();

    //读
    public void get() {

        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + number);
        } finally {
            readLock.unlock();
        }

    }

    //写
    public void set(int number) {

        writeLock.lock();
        try {
            this.number = number;
            System.out.println(Thread.currentThread().getName() + number);
        } finally {
            writeLock.unlock();
        }

    }


}