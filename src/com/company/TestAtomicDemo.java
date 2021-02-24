package com.company;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试原子性：
 * 一、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
 * int i=10;
 * i=i++;//10
 * <p>
 * int temp=i;
 * i=i+1;
 * i=temp;
 * <p>
 * 二、原子变量：jdk 1.5 后 java.util.concurrent.atomic 包下提供了常用的原子变量：
 * 1.volatile 保证内存可见性
 * 2.CAS (Compare-And-Swap) 算法保证数据的原子性
 * CAS 算法是硬件对于并发操作共享数据的支持
 * CAS 包含了三个操作数：
 * 内存值 V
 * 预估值 A
 * 更新值 B
 * 当且仅当 V == A 时，V = B 。否则不做任何操作。
 *
 * @author JOSWAY
 * @date 2021/2/19 8:10
 */
public class TestAtomicDemo {
    public static void main(String[] args) {
        AtomicDemo atomicDemo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(atomicDemo).start();
        }
    }
}

class AtomicDemo implements Runnable {

    //    private int serialNumber = 0;

    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + ":" + this.getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

}