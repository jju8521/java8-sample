package yong.java8;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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
        System.out.println("\nstrings : " + strings);
        System.out.println(
                "strings.stream()\n" +
                        "     .anyMatch(x -> x.equals(\"c\"))\n" +
                        strings.stream()
                                .anyMatch(x -> x.equals("c"))
        );

        final String targetString = "c";
        System.out.println(
                "\nstrings.stream()\n" +
                        "     .anyMatch(targetString::equals)\n" +
                        strings.stream()
//                        .anyMatch(String::equals)
                                .anyMatch(targetString::equals)
        );

        System.out.println();
        methodReference();
    }

    private static void methodReference() {
        // First Class Function

        /**
         * Function can be passes as a parameter to another function
         */
        // Using Lambda Expression
        System.out.println(
                "testFirstClassFunction(3, i -> String.valueOf(i * 2)) : \n" +
                        testFirstClassFunction(3, i -> String.valueOf(i * 2))
        );

        // Using Method Reference
        System.out.println(
                "testFirstClassFunction(5, Sample19::doubleThenToString) : \n" +
                        testFirstClassFunction(5, Sample19::doubleThenToString)
        );

        /**
         *  A Function can be returned as the result of another function.
         */
        //Using Lambda Expression
        final Function<Integer, String> fl = getDoubleThenToStringUsingLambdaExpression();
        System.out.println("\nfl : " + fl);
        System.out.println("fl.apply(16) : " + fl.apply(16));

        //Using Method Reference
        final Function<Integer, String> fmr = getDoubleThenToStringUsingMethodReference();
        System.out.println("\nfmr : " + fmr);
        System.out.println("fmr.apply(15) : " + fmr.apply(15));

        /**
         * A Function can be stored in the data structure
         */
        // Using Lamba Expression, Using Method Reference
        final List<Function<Integer, String>> fsl =
                Arrays.asList(
                        i -> String.valueOf(i * 2),
                        Sample19::doubleThenToString,
                        Sample19::addHashPrefix);
        int targetInt = 235;
        System.out.println("\ntargetInt : " + targetInt);
        for (final Function<Integer, String> f : fsl) {
            final String result = f.apply(targetInt);
            System.out.println("result : " + result);
        }

        // Using Lambda Expression
        final Function<Integer, String> fl2 = i -> String.valueOf(i * 2);
        System.out.println("\nfl2 : " + fl2);
        System.out.println("fl2.apply(394) : " + fl2.apply(394));

        // Using Method Reference
        final Function<Integer, String> fml2 = Sample19::doubleThenToString;
        System.out.println("\nfml2 : " + fml2);
        System.out.println("fml2.apply(832) : " + fml2.apply(832));
    }

    private static String testFirstClassFunction(int n, Function<Integer, String> f) {
//        System.out.println("[testFirstClassFunction] f : "+f);
        return "The result is " + f.apply(n);
    }

    private static String doubleThenToString(int i) {
        return String.valueOf(i * 2);
    }

    private static Function<Integer, String> getDoubleThenToStringUsingLambdaExpression() {
        return i -> String.valueOf(i * 2);
    }

    private static Function<Integer, String> getDoubleThenToStringUsingMethodReference() {
        return Sample19::doubleThenToString;
    }

    private static String addHashPrefix(int number) {
        return "#"+number;
    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}
