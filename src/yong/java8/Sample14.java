package yong.java8;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by yongju on 2016. 10. 16..
 */
public class Sample14 {

    private static final String[] PRICES = {"1.0", "100.99", "35.75", "21.30", "88.00"};
    private static final BigDecimal[] TARGET_PRICES = {new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("50")};
    private static final Random TARGET_PRICES_RANDOM = new Random(111);
    private static final Random RANDOM = new Random(123);
    private static final List<Product> PRODUCT_LIST;

    static {
        final int length = 8_000_000;
        final Product[] productList = new Product[length];

        for (int i = 0; i < length; i++) {
            productList[i] = (new Product((long) i, "Product_" + i, new BigDecimal(PRICES[RANDOM.nextInt(5)])));
        }

        PRODUCT_LIST = Collections.unmodifiableList(Arrays.asList(productList));
    }

    public static void slowDown() {
        try {
            TimeUnit.MILLISECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long iterativeSum(long n, boolean slowDown) {
        long result = 0;

        if (slowDown) {
            for (long i = 1; i <= n; i++) {
                slowDown();
                result += i;
            }
        } else {
            for (long i = 1; i <= n; i++) {
                result += i;
            }
        }

        return result;
    }

    public static long sequentialSum(long n, boolean slowDown) {
        if (slowDown) {
            return Stream.iterate(1L, i -> i + 1)
                    .limit(n)
                    .peek(i -> slowDown())
                    .reduce(Long::sum)
                    .get();
        } else {
            return Stream.iterate(1L, i -> i + 1)
                    .limit(n)
                    .reduce(Long::sum)
                    .get();
        }
    }

    public static long parallelSum(long n, boolean slowDown) {
        if (slowDown) {
            return Stream.iterate(1L, i -> i + 1)
                    .limit(n)
                    .parallel()
                    .peek(i -> slowDown())
                    .reduce(Long::sum)
                    .get();
        } else {
            return Stream.iterate(1L, i -> i + 1)
                    .limit(n)
                    .parallel()
                    .reduce(Long::sum)
                    .get();
        }
    }

    public static long rangedSum(long n, boolean slowDown) {
        if (slowDown) {
            return LongStream.rangeClosed(1, n)
                    .peek(i -> slowDown())
                    .reduce(Long::sum)
                    .getAsLong();
        } else {
            return LongStream.rangeClosed(1, n)
                    .reduce(Long::sum)
                    .getAsLong();
        }
    }

    public static long parallelRangedSum(long n, boolean slowDown) {
        if (slowDown) {
            return LongStream.rangeClosed(1, n)
                    .parallel()
                    .peek(i -> slowDown())
                    .reduce(Long::sum)
                    .getAsLong();
        } else {
            return LongStream.rangeClosed(1, n)
                    .parallel()
                    .reduce(Long::sum)
                    .getAsLong();
        }
    }

    public static void testSum() {
        final long n = 1_000_000;
        final long slowN = 1_000;

        System.out.println("Without Slowdown");

        final long start1 = System.currentTimeMillis();
        System.out.println("iterativeSum: " + iterativeSum(n, false));
        System.out.println(System.currentTimeMillis() - start1 + "ms");

        final long start2 = System.currentTimeMillis();
        System.out.println("sequentialSum: " + sequentialSum(n, false));
        System.out.println(System.currentTimeMillis() - start2 + "ms");

        final long start3 = System.currentTimeMillis();
        System.out.println("parallelSum: " + parallelSum(n, false));
        System.out.println(System.currentTimeMillis() - start3 + "ms");

        final long start4 = System.currentTimeMillis();
        System.out.println("rangedSum: " + rangedSum(n, false));
        System.out.println(System.currentTimeMillis() - start4 + "ms");

        final long start5 = System.currentTimeMillis();
        System.out.println("parallelRangedSum: " + parallelRangedSum(n, false));
        System.out.println(System.currentTimeMillis() - start5 + "ms");

        System.out.println("======================================");
        System.out.println("With Slowdown");

        final long start6 = System.currentTimeMillis();
        System.out.println("iterativeSum: " + iterativeSum(slowN, true));
        System.out.println(System.currentTimeMillis() - start6 + "ms");

        final long start7 = System.currentTimeMillis();
        System.out.println("sequentialSum: " + sequentialSum(slowN, true));
        System.out.println(System.currentTimeMillis() - start7 + "ms");

        final long start8 = System.currentTimeMillis();
        System.out.println("parallelSum: " + parallelSum(slowN, true));
        System.out.println(System.currentTimeMillis() - start8 + "ms");

        final long start9 = System.currentTimeMillis();
        System.out.println("rangedSum: " + rangedSum(slowN, true));
        System.out.println(System.currentTimeMillis() - start9 + "ms");

        final long start10 = System.currentTimeMillis();
        System.out.println("parallelRangedSum: " + parallelRangedSum(slowN, true));
        System.out.println(System.currentTimeMillis() - start10 + "ms");
    }

    private static BigDecimal imperativeSum(List<Product> productList, Predicate<Product> predicate) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final Product product : productList) {
            if (predicate.test(product)) {
                sum = sum.add(product.getPrice());
            }
        }

        return sum;
    }

    private static BigDecimal streamSum(final Stream<Product> stream, final Predicate<Product> predicate) {
        return stream.filter(predicate).map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void testSum2() {
        BigDecimal targetPrice = TARGET_PRICES[TARGET_PRICES_RANDOM.nextInt(3)];
        Predicate<Product> predicate = product -> product.getPrice().compareTo(targetPrice) >= 0;

        System.out.println("======================================");
        long start = System.currentTimeMillis();
        System.out.println("[parallelStreamSum]: " + streamSum(PRODUCT_LIST.parallelStream(), predicate));
        System.out.println((System.currentTimeMillis() - start) + " ms");
        System.out.println("======================================");
        start = System.currentTimeMillis();
        System.out.println("[streamSum]: " + streamSum(PRODUCT_LIST.stream(), predicate));
        System.out.println((System.currentTimeMillis() - start) + " ms");
        System.out.println("======================================");
        start = System.currentTimeMillis();
        System.out.println("[imperativeSum]: " + imperativeSum(PRODUCT_LIST, predicate));
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    public static void main(String[] args) {
        testSum();
        for (int i = 0; i < 5; i++) {
            testSum2();
        }
    }
}