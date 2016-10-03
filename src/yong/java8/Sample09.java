package yong.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by yongju on 16. 10. 3.
 */
public class Sample09 {
    public static void main(String[] args) {
        final int abs1 = Math.abs(-1);
        final int abs2 = Math.abs(1);

        System.out.println("abs1 : " + abs1);
        System.out.println("abs2 : " + abs2);
        System.out.println("abs1 == abs2 is " + (abs1 == abs2));

        final int minInt = Math.abs(Integer.MIN_VALUE);

        System.out.println("MAX_VALUE : " + Integer.MAX_VALUE);
        System.out.println("MIN_VALUE : " + Integer.MIN_VALUE);
        System.out.println("minInt : " + minInt);

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(
                "map : " +
                        map(numbers, i -> i * 2)
        );

        System.out.println(
                "map2 : " +
                        map2(numbers, i -> i * 2)
        );
        System.out.println(
                "map2 : " +
                        map2(numbers, null)
        );

        System.out.println(
                "map3 : " +
                        map3(numbers, i -> i * 2)
        );
        System.out.println(
                "map3 : " +
                        map3(numbers, null)
        );

        System.out.println(
                "map4 : " +
                        map3(numbers, i -> i * 2)
        );
        System.out.println(
                "map4 : " +
                        map3(numbers, i -> i)
        );
        System.out.println(
                "map4 : " +
                        map3(numbers, Function.identity())
        );
    }

    private static <T, R> List<R> map(final List<T> list, final Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();

        for (T t : list) {
            result.add(mapper.apply(t));
        }

        return result;
    }

    private static <T, R> List<R> map2(final List<T> list, final Function<T, R> mapper) {
        final List<R> result;
        if (mapper != null) {
            result = new ArrayList<>();
        } else {
            result = new ArrayList<>((List<R>) list);
        }

        if (result.isEmpty()) {
            for (T t : list) {
//            if (mapper != null) {
                result.add(mapper.apply(t));
//            } else {
//                result.add((R) t);
//            }
            }
        }

        return result;
    }

    private static <T, R> List<R> map3(final List<T> list, final Function<T, R> mapper) {
        final Function<T, R> function;
        if (mapper != null) {
            function = mapper;
        } else {
            function = t -> (R) t;
        }

        final List<R> result = new ArrayList<>();

        for (final T t : list) {
            result.add(function.apply(t));
        }

        return result;
    }

    private static <T, R> List<R> map4(final List<T> list, final Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();

        for (final T t : list) {
            result.add(mapper.apply(t));
        }

        return result;
    }
}
