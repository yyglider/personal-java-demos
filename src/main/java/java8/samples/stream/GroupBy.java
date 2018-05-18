package java8.samples.stream;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yaoyuan on 2018/3/9.
 */
public class GroupBy {


    static class Item {
        String name;
        int number;
        BigDecimal price;

        public Item(String name, int number, BigDecimal price) {
            this.name = name;
            this.number = number;
            this.price = price;
        }

        public String getName3() {
            return name + "+" +number;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "price:" + price;
        }
    }

    public static void main(String[] args) {

        //3 apple, 2 banana, others 1
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.91")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orang", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("9.92")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.93"))
        );

        //group by price
        Map<String, List<Item>> groupByPriceMap =
                items.stream().collect(Collectors.groupingBy(Item::getName3
                        ));

        System.out.println(groupByPriceMap);

        // group by price, uses 'mapping' to convert List<item> to Set<string>
//        Map<BigDecimal, Set<String>> result =
//                items.stream().collect(
//                        Collectors.groupingBy(Item::getPrice,
//                                Collectors.mapping(Item::getName, Collectors.toSet())
//                        )
//                );
//
//        System.out.println(result);

    }
}
