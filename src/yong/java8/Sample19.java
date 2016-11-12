package yong.java8;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by yongju on 2016. 11. 12..
 */
public class Sample19 {
    public static void main(String[] args) {
        /*
         * First Class Function
         * function을 data처럼 사용 가능.
         */

        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("integers.forEach(i -> System.out.println(i))");
        integers.forEach(i -> System.out.print(i));
        System.out.println("\nintegers.forEach(System.out::println)");
        integers.forEach(System.out::print);
        System.out.println();

        final List<BigDecimal> bigDecimals = Arrays.asList(
                new BigDecimal("10.0"),
                new BigDecimal("23"),
                new BigDecimal("5")
        );

        System.out.println("\nbigDecimals : " + bigDecimals);
        System.out.println(
                "bigDecimals.stream()\n" +
                        "   .sorted()\n" +
                        "   .collect(toList())\n" +
                        bigDecimals.stream()
                                .sorted()
                                .collect(toList())
        );

        System.out.println(
                "\nbigDecimals.stream()\n" +
                        "   .sorted((bd1, bd2) -> bd1.compareTo(bd2))\n" +
                        "   .collect(toList())\n" +
                        bigDecimals.stream()
                                .sorted((bd1, bd2) -> bd1.compareTo(bd2))
                                .collect(toList())
        );

        System.out.println(
                "\nbigDecimals.stream()\n" +
                        "   .sorted((bd1, bd2) -> bd2.compareTo(bd1))\n" +
                        "   .collect(toList())\n" +
                        bigDecimals.stream()
                                .sorted((bd1, bd2) -> bd2.compareTo(bd1))
                                .collect(toList())
        );

        System.out.println(
                "\nbigDecimals.stream()\n" +
                        "   .sorted(BigDecimalUtil::compare)\n" +
                        "   .collect(toList())\n" +
                        bigDecimals.stream()
                                .sorted(BigDecimalUtil::compare)
                                .collect(toList())
        );

        System.out.println(
                "\nbigDecimals.stream()\n" +
                        "     .sorted(BigDecimal::compareTo)\n" +
                        "     .collect(toList())\n" +
                        bigDecimals.stream()
                                .sorted(BigDecimal::compareTo)
                                .collect(toList())
        );

        final List<String> strings = Arrays.asList("a", "b", "c", "d");
        System.out.println("\nstrings : "+strings);
        System.out.println(
                "strings.stream()\n" +
                        "     .anyMatch(x -> x.equals(\"c\"))\n" +
                        strings.stream()
                                .anyMatch(x -> x.equals("c"))
        );

        final String targetString = "c";
        System.out.println(
                "\nstrings.stream()\n" +
                        "     .anyMatch(targetString::equals)\n"+
                strings.stream()
//                        .anyMatch(String::equals)
                        .anyMatch(targetString::equals)
        );


    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}
