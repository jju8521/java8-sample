package yong.java8;

import java.util.function.Function;

/**
 * Created by yongju on 16. 9. 20.
 */
public class Sample2 {
    public static void main(String[] args) {
        /*
         * Functions, The Transformer
         */
        // using anonymous class
        final Function<String, Integer> toInt = new Function<String, Integer>() {
            @Override
            public Integer apply(final String value) {
                return Integer.valueOf(value);
            }
        };
        System.out.println("1. toInt: "+toInt.apply("101"));

        // using lambda expression
        final Function<String, Integer> toInt2 = value -> Integer.valueOf(value);
        System.out.println("2. toInt: "+toInt2.apply("102"));

        final Function<Integer, Integer> identity1 = Function.identity();
        System.out.println("3. identity: "+identity1.apply(103));

        final Function<Integer, Integer> identity2 = t -> t;
        System.out.println("4. identity: "+identity2.apply(104));

    }
}
