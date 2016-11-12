package yong.java8;

import java.util.function.Consumer;

/**
 * Created by yongju on 16. 9. 22.
 */
public class Sample03 {
    public static void main(String[] args) {
        final Consumer<String> print = new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println(value);
            }
        };

        print.accept("[print1] hello");

        final Consumer<String> print2 = value -> System.out.println(value);
        print2.accept("[print2] hello");

//        final Function<String, Void> print3 = value -> System.out.println(value);

        final Consumer<String> greetings = value -> System.out.println("[greetings] hello " + value);
        greetings.accept("consumer");
    }
}
