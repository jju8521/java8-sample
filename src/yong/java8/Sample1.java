package yong.java8;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Sample1 {
    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // imperative
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1. result: ");
        for (Integer number : numbers) {
            stringBuilder.append(number).append(" : ");
        }
        System.out.println(stringBuilder.toString());

        final StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("2. result: ");
        final int size = numbers.size();
        for (int i = 0; i < size; i++) {
            stringBuilder2.append(numbers.get(i));
            if (i != size - 1) {
                stringBuilder2.append(" : ");
            }
        }
        System.out.println(stringBuilder2.toString());

        final StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("3. result: ");
        final String separator = " : ";
        for (Integer number : numbers) {
            stringBuilder3.append(number).append(separator);
        }
        final int stringLength = stringBuilder3.length();
        if (stringLength > 0) {
            stringBuilder3.delete(stringLength - separator.length(), stringLength);
        }
        System.out.println(stringBuilder3.toString());

        /*
         * java 8
         * functional programming
         */

        System.out.println("===================================================");
        final String result = numbers.stream()
                .map(String::valueOf)
                .collect(joining(" : "));

        System.out.println("4. result: " + result);
    }
}
