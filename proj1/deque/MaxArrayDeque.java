package deque;

import java.util.Comparator;

public class MaxArrayDeque<T>  extends ArrayDeque<T> {
    private Comparator<T> comparator;

    /** Create a MaxArrayDeque with the given Comparator. */
    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    /** Return the max item in the deque using the default comparator */
    public T max() {
        return max(comparator);
    }

    /** Return the max item in the deque using the given comparator */
    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }
        T maxItem = this.get(0);
        for (T item: this) {
            if (c.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }
}
