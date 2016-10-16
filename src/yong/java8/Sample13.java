package yong.java8;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by yongju on 2016. 10. 16..
 */
public class Sample13 {
    public static void main(String[] args) {
        // 사용할 core 수 설정.
        // singlee core : "0", dual core : "1"...
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1");
        parallel("Parallel Stream (core setting 8)", 8);

        System.out.println("=======================================");
        // with side effeect (객체의 상태 변경)
        int[] sum = {0};
        IntStream.range(0, 100)
                .forEach(i -> sum[0] += i);
        System.out.println("sum2 : " + sum[0]);

        // 실행할 때마다 값이 변경 됨. (race condition)
        // with side effeect (객체의 상태 변경)
        int[] sum2 = {0};
        IntStream.range(0, 100)
                .parallel()
                .forEach(i -> sum2[0] += i);
        System.out.println("parallel sum2 : " + sum2[0]);

        // stream sum no side effeect
        System.out.println("stream sum : " +
                 IntStream.range(0, 100)
                        .sum()
        );

        // parallel stream sum no side effeect
        System.out.println("parallel stream sum : " +
                IntStream.range(0, 100)
                        .parallel()
                        .sum()
        );

        System.out.println("=======================================");
        System.out.println("Stream");
        final long start = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
                .stream()
                .map(integer -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return integer;
                })
                .forEach(i -> System.out.print(i));
        System.out.println("\n"+(System.currentTimeMillis() - start)+" ms");

        // core 가 8개인 pc 에서는 1초만에 실행됨.
        parallel("Parallel Stream (8 elements)", 8);
        // core 가 8개인 pc 에서 2초가 걸림 (element 가 9개라서)
        parallel("Parallel Stream (9 elements)", 9);
    }

    private static void parallel(String msg, long limit) {
        System.out.println("=======================================");
        System.out.println(msg);
        // 사용할 core 갯수 셋팅.

        final long start = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
//                .parallelStream()
        IntStream.iterate(1, i -> i+1)
                .limit(limit)
                .parallel()
                .map(integer -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return integer;
                })
                .forEach(i -> System.out.print(i));
        System.out.println("\n"+(System.currentTimeMillis() - start)+" ms");
    }
}
