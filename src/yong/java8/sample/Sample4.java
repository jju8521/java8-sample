package yong.java8.sample;

import com.sun.javafx.sg.prism.web.NGWebView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by yongju on 16. 9. 23.
 */
public class Sample4 {
    public static void main(String[] args) {
        Predicate<Integer> isPositive1 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer value) {
                if (value > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        System.out.println("isPositive1.test(1) : "+isPositive1.test(1));
        System.out.println("isPositive1.test(0) : "+isPositive1.test(0));
        System.out.println("isPositive1.test(-1) : "+isPositive1.test(-1));

        Predicate<Integer> isPositive2 = i -> i > 0;

        System.out.println("isPositive2.test(1) : "+isPositive2.test(1));
        System.out.println("isPositive2.test(0) : "+isPositive2.test(0));
        System.out.println("isPositive2.test(-1) : "+isPositive2.test(-1));

        List<Integer> numbers = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);
        System.out.println("numbers: "+numbers);

        List<Integer> positiveNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            if (isPositive2.test(number)) {
                positiveNumbers.add(number);
            }
        }

        System.out.println("positive numbers: "+positiveNumbers);

        Predicate<Integer> lessThan3 = i -> i < 3;
        List<Integer> numbersLessThan3 = new ArrayList<>();
        for (Integer number : numbers) {
            if (lessThan3.test(number)) {
                numbersLessThan3.add(number);
            }
        }

        System.out.println("lessThan3 numbers: "+numbersLessThan3);

        System.out.println("positive number - 2 : "+filter(numbers, isPositive2));
        System.out.println("lessThan3 number - 2 : "+filter(numbers, lessThan3));

        System.out.println("positive number - 3 : "+filter(numbers, i -> i > 0));
        System.out.println("lessThan3 number - 3 : "+filter(numbers, i -> i < 3));
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<T>();
        for (T input : list) {
            if (filter.test(input)) {
                result.add(input);
            }
        }

        return result;
    }
}
