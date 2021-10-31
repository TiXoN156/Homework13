package com.company;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ArrayList<Order> orders = getOrders();

//Задача 1
        List<Product> list1 = orders.stream()
                .flatMap(e -> e.getProducts().stream())
                .filter(e -> e.getCategory().equalsIgnoreCase("books"))
                .filter(e -> e.getPrice() > 100)
                .collect(Collectors.toList());
        System.out.println(list1);

//Задача 2
        List<Product> list2 = orders.stream()
                .flatMap(e -> e.getProducts().stream())
                .filter(e -> e.getCategory().equalsIgnoreCase("baby"))
                .collect(Collectors.toList());
        System.out.println(list2);

//Задача 3
        List<Product> list3 = orders.stream()
                .flatMap(e -> e.getProducts().stream())
                .filter(e -> e.getCategory().equalsIgnoreCase("Toys"))
                .collect(Collectors.toList());
        System.out.println(list3);
        // у меня не получилось в стриме взять getPrice и сделать setPrice с коэффициентом 0,9 (скидку 10%)

//Задача 4
        List<Product> list4 = orders.stream()
                .filter(o -> o.getCustomer().getTier() == 2)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 4, 1)) <= 0)
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(list4);

//Задача 5
        Optional<Product> list5 = orders.stream()
                .flatMap(e -> e.getProducts().stream())
                .filter(e -> e.getCategory().equalsIgnoreCase("Books"))
                .min(Comparator.comparing(Product::getPrice));
        System.out.println(list5);

//Задача 6
        List<Order> list6 = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(list6);

//Задача 7
        List<Product> list7 = orders.stream()
                .filter(e -> e.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(e -> System.out.println(e.toString()))
                .flatMap(e -> e.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(list7);

//задача 8
        Double sumOrdersPrice = orders.stream()
                .filter(e -> e.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0)
                .filter(e -> e.getOrderDate().compareTo(LocalDate.of(2021, 3, 1)) < 0)
                .flatMap(e -> e.getProducts().stream())
                .mapToDouble(e -> e.getPrice())
                .sum();
        System.out.println(sumOrdersPrice);

//Задача 9
        Double averageOrdersPrice = orders.stream()
                .filter(e -> e.getOrderDate().isEqual(LocalDate.of(2021, 2, 1))) //дату взял не по
                .flatMap(e -> e.getProducts().stream())                            // условию, а подходящую для проверки
                .mapToDouble(e -> e.getPrice())
                .average().getAsDouble();
        System.out.println(averageOrdersPrice);

//Задача 10
        DoubleSummaryStatistics statistics = orders.stream()
                .flatMap(e -> e.getProducts().stream())
                .filter(e -> e.getCategory().equalsIgnoreCase("Books"))
                .mapToDouble(e -> e.getPrice())
                .summaryStatistics();

        System.out.println(String.format("count = %s, average = %s, max = %s, min = %s, sum = %s",
                statistics.getCount(), statistics.getAverage(), statistics.getMax(), statistics.getMin(),
                statistics.getSum()));

//Задача 11
        Map<Long, Integer> map11 = orders.stream()
                .collect(
                        Collectors.toMap(
                                order -> order.getId(),
                                order -> order.getProducts().size()
                        )
                );
        System.out.println(map11);

//Задача 12
        Map<Customer, List<Order>> map12 = orders.stream()
                .collect(
                        Collectors.groupingBy(Order::getCustomer)
                );
        System.out.println(map12);

//Задача 13
        Map<Order, Double> map13 = orders.stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                order -> order.getProducts().stream()
                                        .mapToDouble(e -> e.getPrice()).sum()
                        )
                );
        System.out.println(map13);

//Задача 14
        Map<String, List<String>> map14 = orders.stream()
                .flatMap(e -> e.getProducts().stream())
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.mapping(product -> product.getName(), Collectors.toList()))
                );
        System.out.println(map14);

//Задача 15
        Map<String, Optional<Product>> map15 = orders.stream()
                .flatMap(e -> e.getProducts().stream())
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)))
                );
        System.out.println(map15);
    }

    private static ArrayList<Order> getOrders() {
        Order order = new Order();
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        Product product1 = new Product(1L, "имяОдин", "Books", 1.5);
        Product product2 = new Product(2L, "имяДва", "Books", 101.5);
        Product product3 = new Product(3L, "имяТри", "Baby", 3.5);
        Product product4 = new Product(3L, "имяЧетыре", "Baby", 55.9);
        Product product5 = new Product(3L, "имяПять", "Toys", 24.5);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);

        Customer customer = new Customer(1L, "customerName", 2);
        order.setId(1L);
//        order.setOrderDate(LocalDate.now().minusDays(1));
        order.setOrderDate(LocalDate.of(2021, 02, 01));
        order.setDeliveryDate(LocalDate.now().plusDays(7));
        order.setStatus("someStatus");
        order.setCustomer(customer);
        order.setProducts(products);

        return orders;
    }
}
