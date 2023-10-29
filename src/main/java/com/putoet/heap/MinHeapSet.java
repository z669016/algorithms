package com.putoet.heap;

import java.util.ArrayList;
import java.util.List;

public class MinHeapSet<K extends Comparable<K>, T> implements Heap<K, T> {
    private final List<Heap.Entry<K, T>> elements = new ArrayList<>();

    @Override
    public T top() {
        if (elements.isEmpty())
            throw new IllegalStateException("Heap is empty");

        final var last = elements.removeLast();
        if (elements.isEmpty())
            return last.element();

        final var top = elements.get(0);
        elements.set(0, last);
        pushDown();

        return top.element();
    }

    @Override
    public T peek() {
        if (elements.isEmpty())
            throw new IllegalStateException("Heap is empty");

        return elements.get(0).element();
    }

    @Override
    public boolean remove(T element) {
        if (elements.isEmpty())
            return false;

        final var idx = findIdx(element);
        if (idx == -1)
            return false;

        if (idx == elements.size() - 1) {
            elements.remove(idx);
            return true;
        }

        final var last = elements.removeLast();
        final var oldKey = elements.get(idx).key();
        final var updatedKey = last.key();
        elements.set(idx, last);

        if (updatedKey.compareTo(oldKey) < 0)
            bubbleUp(idx);
        else if (updatedKey.compareTo(oldKey) > 0)
            pushDown(idx);

        return true;
    }

    @Override
    public boolean update(T element, K updatedKey) {
        if (elements.isEmpty())
            return false;

        final var idx = findIdx(element);
        final var oldKey = elements.get(idx).key();
        elements.set(idx, new Entry<>(updatedKey, element));

        if (updatedKey.compareTo(oldKey) < 0)
            bubbleUp(idx);
        else if (updatedKey.compareTo(oldKey) > 0)
            pushDown(idx);

        return true;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean insert(K key, T element) {
        final var added = elements.add(new Entry<>(key, element));
        bubbleUp();

        return added;
    }

    private void bubbleUp() {
        bubbleUp(elements.size() - 1);
    }

    private void bubbleUp(int idx) {
        final var current = elements.get(idx);
        while (idx > 0) {
            final int parentIdx = getParentIdx(idx);
            if (elements.get(parentIdx).key().compareTo(current.key()) > 0) {
                elements.set(idx, elements.get(parentIdx));
                idx = parentIdx;
            } else {
                break;
            }
        }
        elements.set(idx, current);
    }

    private void pushDown() {
        pushDown(0);
    }

    private void pushDown(int idx) {
        if (elements.size() < 2)
            return;

        final var current = elements.get(idx);
        while (idx < firstLeafIndex()) {
            final int childIdx = lowestPriorityChild(elements, idx);
            if (elements.get(childIdx).key().compareTo(current.key()) < 0) {
                elements.set(idx, elements.get(childIdx));
                idx = childIdx;
            } else {
                break;
            }
        }
        elements.set(idx, current);
    }

    private int findIdx(T element) {
        for (int idx = 0; idx < elements.size(); idx++)
            if (elements.get(idx).element().equals(element))
                return idx;

        return -1;
    }

    private int lowestPriorityChild(List<Heap.Entry<K, T>> elements, int idx) {
        var lowestPriorityChild = firstChildIndex(idx);
        for (int i = lowestPriorityChild; i <= lastChildIndex(idx); i++) {
            if (i < elements.size() && elements.get(i).key().compareTo(elements.get(lowestPriorityChild).key()) < 0)
                lowestPriorityChild = i;
        }
        return lowestPriorityChild;
    }

    private int lastChildIndex(int idx) {
        return 2 * idx + 2;
    }

    private int firstChildIndex(int idx) {
        return 2 * idx + 1;
    }

    private int firstLeafIndex() {
        return (elements.size() - 2) / 2 + 1;
    }

    private int getParentIdx(int idx) {
        return (idx - 1) / 2;
    }
}
