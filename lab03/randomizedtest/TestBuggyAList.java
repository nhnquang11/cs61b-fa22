package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<Integer>();
        BuggyAList<Integer> broken = new BuggyAList<Integer>();

        correct.addLast(4);
        correct.addLast(5);
        correct.addLast(6);

        broken.addLast(4);
        broken.addLast(5);
        broken.addLast(6);

        assertEquals(correct.size(), broken.size());

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                assertEquals(correct.size(), broken.size());
            } else if (operationNumber == 2) {
                // getLast
                if (correct.size() == 0) {
                    continue;
                }
                int lastElem = correct.getLast();
                int bLastElem = broken.getLast();
                assertEquals(lastElem, bLastElem);
            } else if (operationNumber == 3) {
                // removeLast
                if (correct.size() == 0) {
                    continue;
                }
                int removedElem = correct.removeLast();
                int bRemovedElem = broken.removeLast();
                assertEquals(removedElem, bRemovedElem);
            }
        }
    }
}
