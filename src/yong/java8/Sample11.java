package yong.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by yongju on 16. 10. 8.
 */
public class Sample11 {
    public static void main(String[] args) {
//        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
//        numbers.stream()

//        final Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        // List 중복 허용. 순서 보장.
        final List<String> collect1 = Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(toList());
        System.out.println("[collect1] : " + collect1);

        // set 중복 안됨. 순서 보장 X
        final Set<String> set1 = Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(toSet());
        System.out.println("[set1] : " + set1);

        final String joining1 = Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(joining());
        System.out.println("[joining1] : " + joining1);

        final String joining2 = Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(joining(", "));
        System.out.println("[joining2] : " + joining2);

        final String joining3 = Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(joining(", ", "[", "]"));
        System.out.println("[joining3] : " + joining3);


        final String joining4 = Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .distinct()
                .collect(joining(", ", "[", "]"));
        System.out.println("[joining4] : " + joining4);

        final List<String> collect2 = Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .distinct()
                .collect(toList());
        System.out.println("[collect2] : " + collect2);

        System.out.println(
                Stream.of(1, 2, 3, 4, 5)
                        .findFirst()
        );

        final Integer integer3 = 3; // Integer.valueOf(3)
        System.out.println(
                Stream.of(1, 2, 3, 4, 5)
//                        .filter(integer -> integer == 3) // integer : obejct, 3 : primitive type
                        .filter(integer -> integer == integer3)
                        .findFirst()
        );

        final Integer integer127 = 127;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 127)
                        .filter(integer -> integer == integer127) // 같은 object 가 됨.
                        .findFirst()
        );

        final Integer integer128 = 128;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(integer -> integer == integer128) // 서로 다른 object 가 됨.
                        .findFirst()
        );

        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(integer -> integer.equals(integer128))
                        .findFirst()
        );

        System.out.println("[integer > integer3] count : " +
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(integer -> integer > integer3)
                        .count()
        );

        // external iterator
        System.out.println("[External Iterator]");
        final List<Integer> numbeers = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i : numbeers) {
            System.out.print(i);
        }

        System.out.println("\n[Internal Iterator]");
        Stream.of(1, 2, 3, 4, 5, 128)
                // internal iterator
                .forEach(i -> System.out.print(i));
    }
}

