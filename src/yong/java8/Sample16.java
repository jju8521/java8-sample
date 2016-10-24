package yong.java8;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

/**
 * Created by yongju on 2016. 10. 23..
 */
public class Sample16 {
    public static BigDecimal $(String value) {
        return new BigDecimal(value);
    }

    public static void main(String[] args) {
        final Coin won01 = new Coin("won", $("20000"));
        final Coin won02 = new Coin("won", $("5000"));
        final Coin won03 = new Coin("won", $("340"));
        final Coin yen01 = new Coin("Yen", $("200"));
        final Coin yen02 = new Coin("Yen", $("3200"));
        final Coin dollar01 = new Coin("Dollar", $("75.00"));
        final Coin dollar02 = new Coin("Dollar", $("639"));
        final Coin dollar03 = new Coin("Dollar", $("100"));
        final Coin euro01 = new Coin("Euro", $("800"));
        final Coin euro02 = new Coin("Euro", $("1111"));
        final Coin euro03 = new Coin("Euro", $("999"));

        final List<Coin> coinList = new ArrayList<>();
        coinList.add(won01);
        coinList.add(yen01);
        coinList.add(dollar01);
        coinList.add(won02);
        coinList.add(euro02);
        coinList.add(yen02);
        coinList.add(dollar02);
        coinList.add(euro03);
        coinList.add(won03);
        coinList.add(dollar03);
        coinList.add(euro01);

        Map<String, List<Coin>> currencyToCoin = new HashMap<>();
        for (final Coin coin : coinList) {
            final List<Coin> coins;
            if (currencyToCoin.containsKey(coin.getCurrencyName())) {
                coins = currencyToCoin.get(coin.getCurrencyName());
            } else {
                coins = new ArrayList<>();
                currencyToCoin.put(coin.getCurrencyName(), coins);
            }

            coins.add(coin);
        }

        System.out.println(
                mapToString(currencyToCoin)
        );

        for (final Map.Entry<String, List<Coin>> coins : currencyToCoin.entrySet()) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final Coin coin : coins.getValue()) {
                final BigDecimal amount = coin.getAmount();
                sum = sum.add(amount);
            }

            System.out.println("[sum] : " + coins.getKey() + " : " + sum);
        }

        System.out.println("============================================");
        System.out.println(
                mapToString(
                        coinList.stream()
                                .collect(groupingBy(Coin::getCurrencyName))
                )
        );

        System.out.println(mapToStringWithSum(coinList.stream()
                        .collect(groupingBy(Coin::getCurrencyName)),
                (coin1, coin2) -> new Coin(coin1.getCurrencyName(), coin1.getAmount().add(coin2.getAmount()))));

        System.out.println("============================================");
        System.out.println(
                coinList.stream()
                        .collect(groupingBy(Coin::getCurrencyName))
                        .entrySet()
                        .stream()
                        .map(entry -> "[sum] : " + entry.getKey() + " : "
                                + entry.getValue().stream()
                                .map(Coin::getAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                        .collect(joining("\n"))
        );
    }

    private static <K, V> String mapToString(Map<K, V> map) {
        return map.entrySet().stream()
                .map(entry -> entry.toString())
                .collect(joining("\n"));
    }

    private static <K, E, V extends List<E>> String mapToStringWithSum(Map<K, V> map, BinaryOperator<E> adder) {
        return map.entrySet().stream()
                .map(entry -> "[sum] : key : " + entry.getKey() + "" + entry.getValue()
                        .stream()
                        .reduce(adder)
                )
                .collect(joining("\n"));
    }
}


@Data
class Coin {
    private final String currencyName;
    private final BigDecimal amount;
}