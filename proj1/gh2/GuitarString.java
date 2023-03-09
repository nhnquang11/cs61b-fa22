package gh2;

import deque.ArrayDeque;
import deque.Deque;
import deque.LinkedListDeque;
import edu.princeton.cs.algs4.StdRandom;

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        /** Create a buffer with capacity = SR / frequency. */
        long capacity = Math.round(SR / frequency);
        buffer = new LinkedListDeque<>();

        /** Fill buffer array with zeros. */
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        /** Dequeue everything in buffer, and replace with random noise
         *  between -0.5 and 0.5.*/
        int bufferSize = buffer.size();
        for (int i = 0; i < bufferSize; i++) {
            buffer.removeFirst();
            double noise = Math.random() - 0.5;
            buffer.addLast(noise);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        /** Dequeue the front sample and enqueue a new sample that is
         *  the average of the two multiplied by the DECAY factor. */
        double deValue = buffer.removeFirst();
        double nextValue = buffer.get(0);
        double enValue = DECAY * 0.5 * (deValue + nextValue);
        buffer.addLast(enValue);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
