package yong.java8;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by yongju on 16. 9. 25.
 */
public class Sample05 {
    public static void main(String[] args) {
        final Supplier<String> helloSupplier = () -> "Hello ";
        System.out.println(helloSupplier.get() + "world");

        printIfValidIndex1(0, "yong");
        printIfValidIndex1(1, "yong");
        printIfValidIndex1(-1, "yong");

        long start = System.currentTimeMillis();
        printIfValidIndex1(0, getVeryExpensiveValue());
        printIfValidIndex1(-1, getVeryExpensiveValue());
        printIfValidIndex1(-2, getVeryExpensiveValue());
        System.out.println("It tooks " + ((System.currentTimeMillis() - start) / 1000) + " seconds");

        // lazy evalutation
        start = System.currentTimeMillis();
        printIfValidIndex2(0, () -> getVeryExpensiveValue());
        printIfValidIndex2(-1, () -> getVeryExpensiveValue());
        printIfValidIndex2(-2, new Supplier<String>() {
            @Override
            public String get() {
                return getVeryExpensiveValue();
            }
        });
        System.out.println("It tooks " + ((System.currentTimeMillis() - start) / 1000) + " seconds");


    }

    private static String getVeryExpensiveValue() {
        // Let's just say it has very expensive calcuation.
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "yong";
    }

    private static void printIfValidIndex1(int number, String value) {
        if (number >= 0) {
            System.out.println("[printIfValinIndex] This value is " + value);
        } else {
            System.out.println("[printIfValinIndex] Invalid");
        }
    }

    private static void printIfValidIndex2(int number, Supplier<String> valueSupplier) {
        if (number >= 0) {
            System.out.println("[printIfValinIndex] This value is " + valueSupplier.get());
        } else {
            System.out.println("[printIfValinIndex] Invalid");
        }
    }


}
