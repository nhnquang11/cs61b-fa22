package deque;

public interface Deque<T> {
    /** Add an item of type T to the front of the deque.
     *  Assumption: item is never null. */
    public void addFirst(T item);

    /** Add an item of type T to the back of the deque.
     *  Assumption: item is never null. */
    public void addLast(T item);

    /** Return the number of items in the deque. */
    public int size();

    /** Print the items in the deque from first to last, separated by a space. */
    public void printDeque();

    /** Remove and return the item at the front of the queue.
     *  If no such item exists, return null.
     */
    public T removeFirst();

    /** Remove and return the item at the back of the deque.
     *  If no such item exists, return null.
     */
    public T removeLast();

    /** Get the item at the given index. If no such item exists, return null. */
    public T get(int index);

    /** Return true if deque is empty, false otherwise. */
    default public boolean isEmpty() {
        return size() == 0;
    }
}
