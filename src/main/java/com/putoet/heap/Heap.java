package com.putoet.heap;

public interface Heap<K extends Comparable<K>,T> {
    record Entry<K extends Comparable<K>,T>(K key, T element) {}

    T top();
    T peek();
    boolean insert(K key, T element);
    boolean remove(T element);
    boolean update(T element, K updatedKey);
    int size();
    boolean isEmpty();
}
