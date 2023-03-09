package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private static final int startingCapacity = 8;
    private static final int resizeFactor = 2;
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Create an empty array deque */
    public ArrayDeque() {
        items = (T[]) new Object[startingCapacity];
        size = 0;
        nextFirst = items.length - 1;
        nextLast = 0;
    }

    @Override
    /** Return the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Resize the underlying array to the target capacity. */
    private void resize(int capacity) {
        /** Capacity must be greater than size due to an extra space for sentinel nextFirst and nextLast. */
        if (capacity <= size) {
            return;
        }
        T[] a = (T[]) new Object[capacity];
        if (nextFirst < nextLast) {
            System.arraycopy(items, nextFirst + 1, a, 1, size());
        } else {
            /** Copy elements at the back of the list start form nextFirst. */
            if (nextFirst != items.length - 1) {
                System.arraycopy(items, nextFirst + 1, a, 1, items.length - nextFirst - 1);
            }
            /** Copy the rest elements at the front of the deque. */
            System.arraycopy(items, 0, a, items.length - nextFirst, nextLast);
        }
        /** Update new array deque info */
        items = a;
        nextFirst = 0;
        nextLast = size() + 1;
    }

    @Override
    /** Add an item of type T to the front of the deque.
     *  Assumption: item is never null. */
    public void addFirst(T item) {
        items[nextFirst] = item;
        size++;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        if (nextFirst == nextLast) {
            resize(items.length * resizeFactor);
        }
    }

    @Override
    /** Add an item of type T to the back of the deque.
     *  Assumption: item is never null. */
    public void addLast(T item) {
        items[nextLast] = item;
        size++;
        nextLast = (nextLast + 1) % items.length;
        if (nextLast == nextFirst) {
            resize(items.length * resizeFactor);
        }
    }

    @Override
    /** Print the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        if (nextFirst < nextLast) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        } else {
            for (int i = nextFirst + 1; i < items.length; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println();
    }

    @Override
    /** Remove and return the item at the front of the queue.
     *  If no such item exists, return null.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int removedIndex = (nextFirst + 1) % items.length;
        T removedItem = items[removedIndex];
        items[removedIndex] = null;
        nextFirst = removedIndex;
        size--;
        return removedItem;
    }

    @Override
    /** Remove and return the item at the back of the deque.
     *  If no such item exists, return null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int removedIndex = (nextLast + items.length - 1) % items.length;
        T removedItem = items[removedIndex];
        items[removedIndex] = null;
        nextLast = removedIndex;
        size--;
        return removedItem;
    }

    @Override
    /** Get the item at the given index. If no such item exists, return null. */
    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        int underlyingIndex = (nextFirst + 1 + index) % items.length;
        return items[underlyingIndex];
    }

    /** Return whether the parameter o is equal to the deque. */
    public boolean equals(Object o) {
        /** Check whether o is a linked list deque or not. */
        if (!(o instanceof ArrayDeque<?>)) {
            return false;
        }

        /** Cast type of o to LinkedListDeque for easier comparison. */
        ArrayDeque<?> toCompare = (ArrayDeque<?>) o;
        if (this.size() != toCompare.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(toCompare.get(i))) {
                return false;
            }
        }
        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = (nextFirst + 1) % items.length;
        }

        @Override
        public boolean hasNext() {
            return wizPos != nextLast;
        }

        @Override
        public T next() {
            T returnItem = items[wizPos];
            wizPos = (wizPos + 1) % items.length;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
}
