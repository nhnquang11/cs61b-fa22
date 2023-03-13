package bstmap;

import edu.princeton.cs.algs4.BST;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;              // root node of the bst map
    private int size;               // number of nodes in the bst map
    private class Node {
        private K key;              // sorted by key
        private V val;              // associated data
        private Node left, right;   // left and right subtrees

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
        }

        /** Return the key of the current node. */
        public K getKey() {
            return key;
        }

        /** Return the data associated with the key in the current node. */
        public V getVal() {
            return val;
        }

        /** Return the left child of the current node. */
        public Node getLeft() {
            return left;
        }

        /** Return the right child of the current node. */
        public Node getRight() {
            return right;
        }

        /** Set the associated data of the current node to val. */
        public void setVal(V val) {
            this.val = val;
        }

        /** Set the right child of the current node to x. */
        public void setRight(Node x) {
            this.right = x;
        }

        /** Set the left child of the current node to x. */
        public void setLeft(Node x) {
            this.left = x;
        }
    }

    /** Empty map constructor. */
    public BSTMap() {
        root = null;
        size = 0;
    }


    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    /** Return true whether the tree with node x as a root contains key or not. */
    private boolean containsKey(Node x, K key) {
        if (x == null) { return false; }
        if (x.getKey().compareTo(key) == 0) {
            return true;
        } else if (x.getKey().compareTo(key) < 0) {
            return containsKey(x.getRight(), key);
        } else {
            return  containsKey(x.getLeft(), key);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (x == null) { return null; }

        int cmp = x.getKey().compareTo(key);
        if (cmp == 0) {
            return x.getVal();
        } else if (cmp < 0) {
            return get(x.getRight(), key);
        } else {
            return get(x.getLeft(), key);
        }
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    };

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V val) {
        root = put(root, key, val);
    };

    private Node put(Node x, K key, V val) {
        if (x == null) {
            size++;
            return new Node(key, val);
        }

        int cmp = x.getKey().compareTo(key);
        if (cmp == 0) {
            x.setVal(val);
        } else if (cmp < 0) {
            x.setRight(put(x.getRight(), key, val));
        } else {
            x.setLeft(put(x.getLeft(), key, val));
        }
        return x;
    }

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> ks = new HashSet<K>();
        keySet(ks, root);
        return ks;
    };

    private void keySet(Set<K> ks, Node x) {
        if (x == null) { return; }
        ks.add(x.getKey());
        keySet(ks, x.getLeft());
        keySet(ks, x.getRight());
    };

    /** Removes the mapping for the specified key from this map if present. */
    public V remove(K key) {
        V returnVal = get(key);
        root = remove(root, key);
        return returnVal;
    };

    private Node remove(Node x, K key) {
        if (x == null) { return null; }

        int cmp = x.getKey().compareTo(key);
        if (cmp < 0) {
            x.setRight(remove(x.getRight(), key));
        } else if (cmp > 0) {
            x.setLeft(remove(x.getLeft(), key));
        } else {
            size--;
            if (x.getRight() == null) { return x.getLeft(); }
            else if (x.getLeft() == null) { return x.getRight(); }

            Node t = x;
            x = min(t.right);
            x.setRight(deleteMin(t.getRight()));
            x.setLeft(t.getLeft());
        }
        return x;
    }

    /** Delete the node with the smallest key in the tree. */
    private Node deleteMin(Node x) {
        if (x.getLeft() == null) { return x.getRight(); }
        x.setLeft(deleteMin(x.getLeft()));
        return x;
    }

    /** Return the node with the smallest key in the tree. */
    private Node min(Node x) {
        if (x == null) {
            throw new IllegalArgumentException("The node passed into the function must not be null.");
        }
        if (x.getLeft() == null) { return x; }
        return min(x.getLeft());
    }

    /** Removes the entry for the specified key only if it is currently mapped to
     *  the specified value. */
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    };

    /** Not implement yet. */
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }
    private class BSTMapIterator implements Iterator<K> {

        public BSTMapIterator() {

        }

        public boolean hasNext() {
            return false;
        }

        public K next() {
            return null;
        }
    }
}
