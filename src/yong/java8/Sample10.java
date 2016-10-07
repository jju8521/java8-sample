package yong.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by yongju on 16. 10. 3.
 */
public class Sample10 {
    public static void main(String[] args) {
        System.out.print("[range] : ");
        IntStream.range(0, 10).forEach(i -> System.out.print(i + " "));
        System.out.println("");
        System.out.print("[rangeClosed] : ");
        IntStream.rangeClosed(0, 10).forEach(i -> System.out.print(i + " "));
        System.out.println("");

//        무한대는 아님 (i 가 int 형이라서)
//        IntStream.iterate(1, i -> i + 1)
//                .forEach(i -> System.out.print(i + " "));

//        무한대
//        Stream.iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE))
//                .forEach(i -> System.out.print(i + " "));

        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(i -> System.out.print(i + " "));
        System.out.println("");

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final List<Integer> result1 = new ArrayList<>();
        for (final Integer number : numbers) {
            if (number > 3) {
                result1.add(number);
            }
        }
        System.out.println("[Imperative] result1 : " + result1);

        final List<Integer> result2 = new ArrayList<>();
        for (final Integer number : numbers) {
            if (number > 3 && number < 9) {
                result2.add(number);
            }
        }
        System.out.println("[Imperative] result2 : " + result2);

        final List<Integer> result3 = new ArrayList<>();
        for (final Integer number : numbers) {
            if (number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                result3.add(newNumber);
            }
        }
        System.out.println("[Imperative] result3 : " + result3);

        Integer result4 = null;
        for (final Integer number : numbers) {
            if (number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                if (newNumber > 10) {
                    result4 = newNumber;
                    break;
                }
            }
        }
        System.out.println("[Imperative] result4 : " + result4);

        System.out.println("[Funcational] result5 : " +
                numbers.stream().filter(number -> number > 3)
                        .filter(number -> number < 9)
                        .map(number -> number * 2)
                        .filter(number -> number > 10)
                        .findFirst()
        );

        Integer result6 = null;
        for (final Integer number : numbers) {
            if (number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                if (newNumber > 20) {
                    result6 = newNumber;
                    break;
                }
            }
        }
        System.out.println("[Imperative] result6 : " + result6);

        System.out.println("[Funcational] result7 : " +
                numbers.stream().filter(number -> number > 3)
                        .filter(number -> number < 9)
                        .map(number -> number * 2)
                        .filter(number -> number > 20)
                        .findFirst()
        );

        Integer result8 = null;

        for (final Integer number : numbers) {
            if (number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                if (newNumber > 10) {
                    result8 = newNumber;
                    break;
                }
            }
        }
        System.out.println("[Imperative] result8 : " + result8);

        final AtomicInteger count = new AtomicInteger(1);
        System.out.println("[Funcational] result9 : " +
                numbers.stream()
                        .filter(number -> {
                            System.out.println("["+count.getAndAdd(1)+"][number > 3] number : " + number);
                            return number > 3;
                        })
                        .filter(number -> {
                            System.out.println("["+count.getAndAdd(1)+"][number < 9] number : " + number);
                            return number < 9;
                        })
                        .map(number -> {
                            System.out.println("["+count.getAndAdd(1)+"][number * 2] number : " + number);
                            return number * 2;
                        })
                        .filter(number -> {
                            System.out.println("["+count.getAndAdd(1)+"][number > 10] number : " + number);
                            return number > 10;
                        })
                        .findFirst()
        );

//        final List<Integer> greaterThan3 = filter(numbers, i -> i > 3);
//        final List<Integer> lessThan9 = filter(greaterThan3, i -> i < 9);
//        final List<Integer> doubled = map(lessThan9, i -> i * 2);
//        final List<Integer> greaterThan10 = filter(doubled, i -> i > 10);
//        System.out.println("greaterThan10 : " + greaterThan10.get(0));

        // stream 을 사용할 떄 보다 연산량이 많음.
        count.set(1);
        final List<Integer> greaterThan3 = filter(numbers, i -> {
            System.out.println("["+count.getAndAdd(1)+"][i > 3] i : " + i);
            return i > 3;
        });
        final List<Integer> lessThan9 = filter(greaterThan3, i -> {
            System.out.println("["+count.getAndAdd(1)+"][i < 9] i :" + i);
            return i < 9;
        });
        final List<Integer> doubled = map(lessThan9, i -> {
            System.out.println("["+count.getAndAdd(1)+"][i * 2] i : " + i);
            return i * 2;
        });
        final List<Integer> greaterThan10 = filter(doubled, i -> {
            System.out.println("["+count.getAndAdd(1)+"][i > 10] i : " + i);
            return i > 10;
        });
        System.out.println("greaterThan10 : " + greaterThan10.get(0));
    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        final List<T> result = new ArrayList<>();
        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
            result.add(mapper.apply(t));
        }

        return result;
    }
}
