package yong.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by yongju on 16. 9. 25.
 */
public class Sample07 {
    public static void main(String[] args) {
        BigDecimalToCurrency1 bigDecimalToCurrency = bd -> "$" + bd.toString();

        System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal("120.00")));

        final BigDecimalToCurrency2 bigDecimalToCurrency2 = new BigDecimalToCurrency2() {
            @Override
            public <T> String toCurrency(T value) {
                return "$" + value.toString();
            }
        };
        System.out.println(bigDecimalToCurrency2.toCurrency(new BigDecimal("130.00")));

        // Error (Can not using lamdba expression, because using generic method)
//        final BigDecimalToCurrency2 bigDecimalToCurrency3 = value -> "$"+value.toString();
//        System.out.println(bigDecimalToCurrency3.toCurrency(new BigDecimal("140.00")));

        final Product product1 = new Product(1L, "A", new BigDecimal("10.00"));
        final Product product2 = new Product(2L, "B", new BigDecimal("55.50"));
        final Product product3 = new Product(3L, "C", new BigDecimal("17.45"));
        final Product product4 = new Product(4L, "D", new BigDecimal("20.00"));
        final Product product5 = new Product(5L, "E", new BigDecimal("110.99"));
        final List<Product> products = Arrays.asList(
                product1,
                product2,
                product3,
                product4,
                product5
        );

        System.out.println("[Products : " + products);

        BigDecimal twenty = new BigDecimal("20");
        List<Product> result = new ArrayList<>();
        for (final Product product : products) {
            if (product.getPrice().compareTo(twenty) >= 0) {
                result.add(product);
            }
        }

        System.out.println("[price >= 20] : " + result);

        List<Product> result2 = filter(products, product -> product.getPrice().compareTo(twenty) >= 0);
        System.out.println("[price >= 20] : " + result2);

        System.out.println("[price <= 10] : " +
                filter(products, product -> product.getPrice().compareTo(new BigDecimal("10")) < 1));

        final List<Product> expensiveProducts = filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) > 0);

        final List<DiscountedProduct> discountedProducts = new ArrayList<>();
        for (final Product product : expensiveProducts) {
            discountedProducts.add(
                    new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        }

        System.out.println("[expensiveProducts] : " + expensiveProducts);
        System.out.println("[discountedProducts] : " + discountedProducts);

        final List<DiscountedProduct> discountedProducts2 = map(expensiveProducts,
                product -> new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        System.out.println("[discountedProducts2] : " + discountedProducts2);


        // error
        // discountedProductPredicate 가 DiscountedProduct 타입이므로 Product 클래스에 적용할 수 없음.
        // 1. discountedProductPredicate 를 Product class로 변경.
        // 2. filter method 의 Predicate<T> predicate 를 Predicate<? super T> predicate 변경.
//        Predicate<DiscountedProduct> discountedProductPredicate = prouct -> prouct.getPrice().compareTo(new BigDecimal("30")) <= 0;
        Predicate<Product> discountedProductPredicate = prouct -> prouct.getPrice().compareTo(new BigDecimal("30")) <= 0;
        System.out.println("[discountedProducts2 <= 30] : " +
                filter(discountedProducts2, discountedProductPredicate));
        System.out.println("[products <= 30] : " +
                filter(products, discountedProductPredicate));

        // total 가격의 합 구하기.
        final List<BigDecimal> prices = map(products, product -> product.getPrice());
        BigDecimal total = BigDecimal.ZERO;
        for (final BigDecimal price : prices) {
            total = total.add(price);
        }

        System.out.println("[total] : " + total);

        System.out.println("[total] : " + total(products, product -> product.getPrice()));
        System.out.println("[Discounted Total] : " + total(discountedProducts2, product -> product.getPrice()));


        Order order = new Order(1L, "on-1234", Arrays.asList(
                new OrderedItem(1L, product1, 2),
                new OrderedItem(1L, product3, 1),
                new OrderedItem(1L, product4, 10)
        ));

        System.out.println("[order total] : " + order.totalPrice());
    }

    private static <T> BigDecimal total(List<T> list, Function<T, BigDecimal> mapper) {
        BigDecimal total = BigDecimal.ZERO;
        for (final T t : list) {
            total = total.add(mapper.apply(t));
        }

        return total;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
            result.add(function.apply(t));
        }

        return result;
    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        List<T> result = new ArrayList<>();
        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        return result;
    }

    @FunctionalInterface
    interface BigDecimalToCurrency1 {
        String toCurrency(BigDecimal value);
    }

    // Invalid Functional Interface
    // lambda expression (X) -> generic method
    @FunctionalInterface
    interface BigDecimalToCurrency2 {
        <T> String toCurrency(T value);
    }

    @AllArgsConstructor
    @Data
    static class Product {
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @ToString(callSuper = true)
    static class DiscountedProduct extends Product {
        public DiscountedProduct(Long id, String name, BigDecimal price) {
            super(id, name, price);
        }
    }

    @AllArgsConstructor
    @Data
    static class Order {
        private Long id;
        private String orderNumber;
        private List<OrderedItem> items;

        public BigDecimal totalPrice() {
            return total(items, item -> item.getItemTotal());
        }
    }

    @AllArgsConstructor
    @Data
    static class OrderedItem {
        private Long id;
        private Product product;
        private int quuantity;

        public BigDecimal getItemTotal() {
            return product.getPrice().multiply(new BigDecimal(quuantity));
        }
    }
}