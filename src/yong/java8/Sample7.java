package yong.java8;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yongju on 16. 9. 25.
 */
public class Sample7 {
    public static void main(String[] args) {
        BigDecimalToCurrency1 bigDecimalToCurrency = bd -> "$"+bd.toString();

        System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal("120.00")));

        final BigDecimalToCurrency2 bigDecimalToCurrency2 = new BigDecimalToCurrency2() {
            @Override
            public <T> String toCurrency(T value) {
                return "$"+value.toString();
            }
        };
        System.out.println(bigDecimalToCurrency2.toCurrency(new BigDecimal("130.00")));

        // Error (Can not using lamdba expression, because using generic method)
//        final BigDecimalToCurrency2 bigDecimalToCurrency3 = value -> "$"+value.toString();
//        System.out.println(bigDecimalToCurrency3.toCurrency(new BigDecimal("140.00")));

        final List<Product> products = Arrays.asList(
                new Product(1L, "A", new BigDecimal("10.00")),
                new Product(2L, "B", new BigDecimal("55.50")),
                new Product(3L, "C", new BigDecimal("17.45")),
                new Product(4L, "D", new BigDecimal("23.00")),
                new Product(5L, "E", new BigDecimal("110.99"))
        );
    }

    @AllArgsConstructor
    @Data
    static class Product {
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @FunctionalInterface
    interface BigDecimalToCurrency1 {
        String toCurrency(BigDecimal value);
    }

    // Invalid Functional Interface
    // lambda expression (X) -> generic method
    @FunctionalInterface
    interface BigDecimalToCurrency2 {
        <T>  String toCurrency(T value);
    }
}
