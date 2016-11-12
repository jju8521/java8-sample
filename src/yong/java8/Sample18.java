package yong.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Created by yongju on 2016. 11. 12..
 */
public class Sample18 {
    public static void main(String[] args) {
        /*
         * high order function
         * 1. function (function)
         * 2. fucntion = function()
         *
         * Fuction<Function<Integer, String>, String>
         * f = x -> x.apply()
         * f.apply(i -> "#"+i)
         */

        final Function<Integer, String> _f = i -> i + "";

        // f : high order function
        final Function<Function<Integer, String>, String> f =
                g -> g.apply(10);

        System.out.println("final Function<Function<Integer, String>, String> f = g -> g.apply(10);");
        System.out.println("f.apply(i -> \"#\" + i) : " + f.apply(i -> "#" + i));
//        f.apply(i -> i * 10)

        final Function<Integer, Function<Integer, Integer>> f2 =
                i -> (i2 -> i + i2);
        System.out.println("\nfinal Function<Integer, Function<Integer, Integer>> f2 = i -> (i2 -> i + i2);");
        System.out.println("f2.apply(1) : " + f2.apply(1));
        System.out.println("f2.apply(1).apply(9) : " + f2.apply(1).apply(9));


        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(
                "\nlist : " + list
        );
        System.out.println(
                "map(list, i -> \"# \"+i) : " +
                        map(list, i -> "# " + i)
        );
        System.out.println(
                "list.stream().map(i -> \"# \"+i).collect(toList())" +
                        list.stream().map(i -> "# " + i).collect(toList())
        );
        System.out.println(
                "list.stream().filter(i -> i > 3).map(i -> \"# \" + i).collect(toList())" +
                        list.stream().filter(i -> i > 3).map(i -> "# " + i).collect(toList())
        );

//        Function.identity()

        Function<String, Function<Boolean, Function<Integer, Integer>>> f3 =
//                i1 -> i2 -> i3 -> Integer.valueOf(i1 + i2 + i3);
                i1 -> i2 -> i3 -> i2 ? Integer.valueOf(i1 + i3) : Integer.valueOf(i1 + (-1 * i3));

        final Function<Boolean, Function<Integer, Integer>> apply1 = f3.apply("123");
        final Function<Integer, Integer> apply2 = apply1.apply(false);
        final Integer resultF3 = apply2.apply(50000);

        System.out.println("\nresultF3 : "+resultF3);
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<R>();
        for (final T t : list) {
            result.add(mapper.apply(t));
        }

        return result;
    }
}
