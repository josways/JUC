package com.company;

/**
 * 测试 volatile
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 相较于 synchronized 是一种较为轻量级的同步策略。
 * <p>
 * 注意：
 * 1.volatile 不具备“互斥性”
 * 2.volatile 不能保证变量的"原子性"
 *
 * @author JOSWAY
 * @date 2021/2/5 17:24
 */
public class TestVolatile {
    public static void main(String[] args) {

        VolatileDemo volatileDemo = new VolatileDemo();
        new Thread(volatileDemo).start();

        while (true) {
//            效率非常低
//            synchronized (threadDemo) {
            if (volatileDemo.isFlag()) {
                System.out.println("---------------");
                break;
//                }
            }

        }

    }
}

class VolatileDemo implements Runnable {

    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;

        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
