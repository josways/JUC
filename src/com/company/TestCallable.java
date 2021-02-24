package com.company;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 测试Callable
 * 一、创建线程的方式三：实现 Callable 接口。
 *
 * 二、执行 Callable 方式，需要 FutureTask 实现类支持，用于接受运算结果。FutureTask 是 Future 接口的实现类。
 *
 * @author JOSWAY
 * @date 2021/2/22 8:24
 */
public class TestCallable {
    public static void main(String[] args) {
        CallableDemo callableDemo = new CallableDemo();

//        try {
//            Integer call = callableDemo.call();
//            System.out.println(call);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //执行 Callable 方式，需要 FutureTask 实现类支持，用于接受运算结果。
        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(callableDemo);

        new Thread(integerFutureTask).start();

        try {
            //FutureTask 可用于 闭锁
            Integer integer = integerFutureTask.get();
            System.out.println(integer);
            System.out.println("-------------------------------");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }

}

class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;

        for (int i = 0; i <= 10000; i++) {
            sum += i;

        }
        return sum;
    }
}

class RunnableDemo implements Runnable {

    @Override
    public void run() {

    }
}


