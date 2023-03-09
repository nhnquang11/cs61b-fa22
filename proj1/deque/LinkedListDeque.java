package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        /** Set the next pointer of the current node to next. */
        public void setNext(Node<T> next) {
            this.next = next;
        }

        /** Set the prev pointer of the current node to prev */
        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        /** Return the next pointer of the current node. */
        public Node<T> getNext() {
            return next;
        }

        /** Return the prev pointer of the current node. */
        public Node<T> getPrev() {
            return prev;
        }

        public T getItem() {
            return item;
        }

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size; // Number of elements in the list
    private Node<T> sentFront; // Front sentinel to keep track the front of the list
    private Node<T> sentBack; // Back sentinel to keep track the end of the list

    /** Create an empty linked list deque.  */
    public LinkedListDeque() {
        size = 0;
        sentFront = new Node<T>(null, null, null);
        sentBack = new Node<T>(null, null, null);
        sentFront.setNext(sentBack);
        sentBack.setPrev(sentFront);
    }
    @Override
    /** Add an item of type T to the front of the deque.
     *  Assumption: item is never null. */
    public void addFirst(T item) {
        Node<T> newNode = new Node(sentFront, item, sentFront.getNext());
        sentFront.getNext().setPrev(newNode);
        sentFront.setNext(newNode);
        size++;
    }

    @Override
    /** Add an item of type T to the back of the deque.
     *  Assumption: item is never null. */
    public void addLast(T item) {
        Node<T> newNode = new Node(sentBack.getPrev(), item, sentBack);
        sentBack.getPrev().setNext(newNode);
        sentBack.setPrev(newNode);
        size++;
    }

    @Override
    /** Return the number of items in the deque. */
    public int size() {
        return size;
    }

    @Override
    /** Print the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        for (Node<T> p = sentFront.getNext(); p != sentBack; p = p.getNext()) {
            System.out.print(p.getItem() + " ");
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
        Node<T> removedNode = sentFront.getNext();
        sentFront.setNext(removedNode.getNext());
        removedNode.getNext().setPrev(sentFront);
        removedNode.setNext(null);
        removedNode.setPrev(null);
        size--;
        return removedNode.getItem();
    }

    @Override
    /** Remove and return the item at the back of the deque.
     *  If no such item exists, return null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> removedNode = sentBack.getPrev();
        sentBack.setPrev(removedNode.getPrev());
        removedNode.getPrev().setNext(sentBack);
        removedNode.setNext(null);
        removedNode.setPrev(null);
        size--;
        return removedNode.getItem();
    }

    @Override
    /** Get the item at the given index. If no such item exists, return null. */
    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        Node<T> p = sentFront;
        for (int i = 0; i <= index; i++) {
            p = p.getNext();
        }
        return p.getItem();
    }

    @Override
    /** Return whether the parameter o is equal to the deque. */
    public boolean equals(Object o) {
        /** Check whether o is a linked list deque or not. */
        if (!(o instanceof LinkedListDeque<?>)) {
            return false;
        }

        /** Cast type of o to LinkedListDeque for easier comparison. */
        LinkedListDeque<?> toCompare = (LinkedListDeque<?>) o;
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

    /** Return the item at the given index of the deque with p as the front sentinel. */
    private T getRecursiveHelper(int index, Node<T> p) {
        if (index == -1) {
            return p.getItem();
        }
        return getRecursiveHelper(index - 1, p.getNext());
    }

    /** Get the item at the given index using recursive approach.
     *  If no such item exists, return null. */
    public T getRecursive(int index) {
        if (index >= size()) {
            return null;
        }
        return getRecursiveHelper(index, sentFront);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node<T> wizNode;

        public LinkedListDequeIterator() {
            wizNode = sentFront.getNext();
        }

        @Override
        public boolean hasNext() {
            return wizNode != sentBack;
        }

        @Override
        public T next() {
            T returnItem = wizNode.getItem();
            wizNode = wizNode.getNext();
            return returnItem;
        }
    }
}
