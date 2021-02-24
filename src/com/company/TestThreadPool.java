package com.company;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 测试线程池
 * <p>
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建和销毁的额外开销，提高了响应的速度。
 * <p>
 * 二、线程池的体系结构：
 * java.util.concurrent.Executor: 负责线程的使用与调度的根接口
 * |-ExecutorService: 线程池的主要接口*
 * |--ThreadPoolExecutor: 线程池的实现类
 * |---ScheduledExecutorService: 子接口：负责线程的调度
 * |----ScheduledThreadPoolExecutor: 继承了 ThreadPoolExecutor ，实现了 ScheduledExecutorService
 * <p>
 * 三、工具类：Executors：
 * ExecutorService newFixedThreadPool(): 创建固定大小的线程池
 * ExecutorService newCachedThreadPool(): 缓存线程池，线程池数量不固定，可以更具需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor(): 创建单个线程的线程池。
 * <p>
 * ScheduledExecutorService newScheduledThreadPool(): 创建固定大小的线程池，可以延迟活定时执行任务。
 *
 * @author JOSWAY
 * @date 2021/2/24 9:17
 */
public class TestThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        /*ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();

        //2.为线程池的线程分配任务
        for (int i = 0; i < 10; i++) {
            executorService.submit(threadPoolDemo);
        }


        //3.关闭线程池
        executorService.shutdown();*/


        /*Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = 0; i <= 100; i++) {
                    sum += i;
                }
                return sum;
            }
        });

        System.out.println(future.get());

        executorService.shutdown();*/

        ArrayList<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int i = 0; i <= 100; i++) {
                        sum += i;
                    }
                    return sum;
                }
            });
            futures.add(future);
        }

        for (Future<Integer> future : futures) {
            System.out.println(future.get());
        }

        executorService.shutdown();


    }

}

class ThreadPoolDemo implements Runnable {

    private int i = 0;

    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }
}
