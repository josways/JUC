package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Lock
 * 一、用于解决多线程安全问题的方式：
 *
 * synchronized：隐式锁
 * 1.同步代码块
 * 2.同步方法
 *
 * jdk 1.5 后：
 * 3.同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁。
 *
 * @author JOSWAY
 * @date 2021/2/22 8:41
 */
public class TestLock {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();

    }

}

class Ticket implements Runnable{

    private int ticket = 1000;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        //上锁
        lock.lock();
        try {
            while (ticket > 0) {
                try {
                    Thread.sleep(5);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 完成售票，余票为："+--ticket);
            }
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
