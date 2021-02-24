package com.company;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 测试调度线程池
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
public class TestScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 10; i++) {
            ScheduledFuture<Integer> future = scheduledExecutorService.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName() + " : " + num);
                    return num;
                }
            }, 1, TimeUnit.SECONDS);

            System.out.println(future.get());
        }

        scheduledExecutorService.shutdown();
    }

}
