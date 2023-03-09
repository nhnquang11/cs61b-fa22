package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    private class IntegerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a > b ? 1: a == b? 0: -1;
        }
    }

    private class Dog {
        private int size;
        private String name;
        public Dog(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Largest dog info: \nName: " + this.name + "\nSize: " + this.size;
        }
    }

    private class DogSizeComparator implements Comparator<Dog> {
        public int compare(Dog d1, Dog d2) {
            int size1 = d1.getSize();
            int size2 = d2.getSize();
            return size1 > size2 ? 1: size1 == size2? 0: -1;
        }
    }
    @Test
    /** Add a list of integers to the deque, check if the max() is correct.*/
    public void getMaxInteger() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<Integer>(new IntegerComparator());

        mad.addFirst(3);
        mad.addFirst(9);
        mad.addFirst(25);
        mad.addFirst(2);
        mad.addFirst(33);
        mad.addLast(24);
        mad.addLast(11);
        mad.addLast(100);
        mad.addLast(4);
        mad.addLast(8);

        System.out.println(mad.max());
    }

    @Test
    /** Add a list of dogs to the deque, print out the dog with the largest size.*/
    public void getLargestDog() {
        MaxArrayDeque<Dog> mad = new MaxArrayDeque<Dog>(new DogSizeComparator());

        Dog d1 = new Dog("Densie", 8);
        Dog d2 = new Dog("Pakkun", 5);
        Dog d3 = new Dog("GoldRet", 20);
        Dog d4 = new Dog("Gerny", 30);

        mad.addLast(d3);
        mad.addFirst(d1);
        mad.addFirst(d4);
        mad.addLast(d2);

        System.out.println(mad.max());
    }
}
