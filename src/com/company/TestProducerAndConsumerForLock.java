package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试生产者和消费者用同步锁
 *
 * @author JOSWAY
 * @date 2021/2/22 9:20
 */
public class TestProducerAndConsumerForLock {

    public static void main(String[] args) {

        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(producer, "生产者A").start();
        new Thread(consumer, "消费者B").start();

        new Thread(producer, "生产者C").start();
        new Thread(consumer, "消费者D").start();

    }

}



//店员
class Clerk {

    private final Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private int product = 0;

    //进货
    public void get() {

        lock.lock();
        try {
            //while 为了避免虚假唤醒问题，应该总是使用在循环中
            while (product >= 1) {
                System.out.println("产品已满！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    // 卖货
    public void sale() {

        lock.lock();
        try {
            while (product <= 0) {
                System.out.println("缺货！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

}

// 生产者
class Producer implements Runnable {

    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

// 消费者
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
