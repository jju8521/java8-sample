package yong.java8;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by yongju on 2016. 10. 15.
 */
public class Sample12 {
    public static void main(String[] args) {
        final Product product1 = new Product(1L, "A", new BigDecimal("100.50"));
        final Product product2 = new Product(2L, "B", new BigDecimal("23.00"));
        final Product product3 = new Product(3L, "C", new BigDecimal("31.45"));
        final Product product4 = new Product(4L, "D", new BigDecimal("80.20"));
        final Product product5 = new Product(5L, "E", new BigDecimal("7.5"));
        final List<Product> products = Arrays.asList(
                product1,
                product2,
                product3,
                product4,
                product5
        );

        System.out.println("[price >= 30]\n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .collect(toList())
        );

        System.out.println("====================================");
        System.out.println("[price >= 30]\n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .map(product -> product.toString())
                        .collect(joining("\n"))
        );

        System.out.println("====================================");
        System.out.println("IntStream.sum : " +
                IntStream.of(1, 2, 3, 4, 5).sum()
        );

        System.out.println("====================================");
        System.out.println("[total price] : " +
//            products.stream()
//                    .reduce(BigDecimal.ZERO, (p1, p2) -> p1.getPrice().add(p2.getPrice()))
                        products.stream()
                                .map(product -> product.getPrice())
                                .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
//                    .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        System.out.println("====================================");
        System.out.println("[total price (price >= 30)] : " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .map(product -> product.getPrice())
                        .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
        );

        System.out.println("====================================");
        System.out.println("[# of products (price >= 30)] : " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .count()
        );

        final OrderedItem item1 = new OrderedItem(1L, product1, 1);
        final OrderedItem item2 = new OrderedItem(1L, product3, 3);
        final OrderedItem item3 = new OrderedItem(1L, product5, 10);

        final List<OrderedItem> orderedItemList = Arrays.asList(
                item1,
                item2,
                item3
        );
        final Order order = new Order(1L, orderedItemList);

        System.out.println("====================================");
        System.out.println("[total price ordered item] : " + order.totalPrice());

    }
}

@AllArgsConstructor
@Data
class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

@AllArgsConstructor
@Data
class OrderedItem {
    private Long id;
    private Product product;
    private int quantity;

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}

@AllArgsConstructor
@Data
class Order {
    private Long id;
    private List<OrderedItem> items;

    // compare sample07
    public BigDecimal totalPrice() {
        return items.stream()
                .map(item -> item.getTotalPrice())
                .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2));
    }
}
