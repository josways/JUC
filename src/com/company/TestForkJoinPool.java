package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 测试分支合并框架
 * <p>
 * Fork/Join框架：就是在必要的情况下，将一个大任务，
 * 进行拆分（fork）成若干个小任务（拆到不可再拆时），
 * 再将一个个的小任务运算的结果继续汇总（json）。
 *
 * @author JOSWAY
 * @date 2021/2/24 10:25
 */
public class TestForkJoinPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Instant start = Instant.now();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ForkJoinSumCalculate forkJoinSumCalculate = new ForkJoinSumCalculate(0L, 1000000000L);

        ForkJoinTask<Long> submit = forkJoinPool.submit(forkJoinSumCalculate);

        Long sum = submit.get();

        Instant end = Instant.now();

        long l = Duration.between(start, end).toMillis();

        System.out.println("耗时：" + l + ",结果：" + sum);

        test1();

        test2();
    }

    public static void test1() {
        Instant start = Instant.now();

        long sum = 0L;

        for (int i = 0; i <= 1000000000L; i++) {
            sum += i;
        }

        Instant end = Instant.now();

        long l = Duration.between(start, end).toMillis();

        System.out.println("耗时：" + l + ",结果：" + sum);
    }

    //java8
    public static void test2() {

        Instant start = Instant.now();

        long sum = LongStream.rangeClosed(0L, 1000000000L).parallel().sum();

        Instant end = Instant.now();

        long l = Duration.between(start, end).toMillis();

        System.out.println("耗时：" + l + ",结果：" + sum);

    }

}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THRESHOLD = 10000L;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length <= THRESHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;

            ForkJoinSumCalculate leftForkJoinSumCalculate = new ForkJoinSumCalculate(start, middle);
            //进行拆分，同事压入线程队列。
            leftForkJoinSumCalculate.fork();
            ForkJoinSumCalculate rightForkJoinSumCalculate = new ForkJoinSumCalculate(middle + 1, end);
            //进行拆分，同事压入线程队列。
            rightForkJoinSumCalculate.fork();

            return leftForkJoinSumCalculate.join() + rightForkJoinSumCalculate.join();

        }

    }
}
