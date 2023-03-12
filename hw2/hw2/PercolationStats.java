package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.IllegalArgumentException;

public class PercolationStats {
    /** Keep track the thresholds of each experiment. */
    private double[] thresholds;

    /** Sample mean of percolation threshold. */
    private double sampleMean;
    /** Standard deviation of percolation threshold. */
    private double sampleStd;

    /** Low endpoint of 95% confidence interval. */
    private double sampleConfidenceLow;
    /** High endpoint of 95% confidence interval. */
    private double sampleConfidenceHigh;

    /** Perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be positive integers, but " + N + " and " + T + " were given.");
        }
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            /* Open a random site until the system percolates. */
            while (!p.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                p.open(row, col);
            }
            thresholds[i] = (double) p.numberOfOpenSites() / (N * N);
        }

        /* Calculate sample mean. */
        sampleMean = StdStats.mean(thresholds);

        /* Calculate standard deviation. */
        sampleStd = StdStats.stddev(thresholds);

        /* Calculate low endpoint of 95% confidence interval. */
        sampleConfidenceLow = sampleMean - 1.96 * sampleStd / Math.sqrt(T);

        /* Calculate high endpoint of 95% confidence interval. */
        sampleConfidenceHigh = sampleMean + 1.96 * sampleStd / Math.sqrt(T);
    }

    /** Return the sample mean of percolation threshold. */
    public double mean() {
        return sampleMean;
    }

    /** Return the sample standard deviation of percolation threshold. */
    public double stddev() {
        return sampleStd;
    }

    /** Return the low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return sampleConfidenceLow;
    }

    /** Return the high endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return sampleConfidenceHigh;
    }
}
