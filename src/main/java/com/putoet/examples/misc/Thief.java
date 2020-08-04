package com.putoet.examples.misc;

import com.putoet.misc.Knapsack;

import java.util.List;

public class Thief {
    public static void main(String[] args) {
        final List<Knapsack.Item> items = List.of(
                Knapsack.itemOf("television", 50, 500),
                Knapsack.itemOf("candlesticks", 2, 300),
                Knapsack.itemOf("stereo", 35, 400),
                Knapsack.itemOf("laptop", 3, 1000),
                Knapsack.itemOf("food", 15, 50),
                Knapsack.itemOf("clothing", 20, 800),
                Knapsack.itemOf("jewelry", 1, 4000),
                Knapsack.itemOf("books", 100, 300),
                Knapsack.itemOf("printer", 18, 30),
                Knapsack.itemOf("refrigerator", 200, 700),
                Knapsack.itemOf("painting", 10, 1000)
        );

        System.out.println("The best items for the thief to steal are:");
        System.out.printf("%-15.15s %-15.15s %-15.15s\n", "Name", "Weight", "Value");
        for (Knapsack.Item item : Knapsack.fill(items, 75)) {
            System.out.printf("%-15.15s %-15d %-15.2f\n", item.name(), item.weight(), item.value());
        }
    }
}
