package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class Percolation {
    /** Number of open sites in the system. */
    private int numOpenSites;

    /** Keep track sets of connected sites in the system. */
    private WeightedQuickUnionUF siteUF;

    /** Number of sites per edge. */
    private int n;

    /** The indices of two virtual sites created at the top and the bottom of the system. */
    private int topIndex;
    private int bottomIndex;

    /** To check whether the site at the given coordinate is ope:
     *  - isSiteOpen[r][c] = 0 -> Site Block
     *  - isSiteOpen[r][c] = 1 -> Site Open
     *  */
    private int[][] isSiteOpen;

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Number of sites per edge must be a positive integer.");
        }

        n = N;
        numOpenSites = 0;
        int numSites = N * N;
        topIndex = numSites;
        bottomIndex = numSites + 1;
        siteUF = new WeightedQuickUnionUF(numSites + 2); // Including two virtual sites

        /* Set the status of all the sites in the system as blocked. */
        isSiteOpen = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                isSiteOpen[r][c] = 0;
            }
        }
    }

    /** Convert the 2D coordinate into 1D index. */
    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    /** Return whether the coordinate (row, col) is valid. */
    private boolean valid(int row, int col) {
        return (row >= 0) && (row < n) && (col >= 0) && (col < n);
    }

    /** Open the site (row, col) if it is not open yet. */
    public void open(int row, int col) {
        if (!valid(row, col)) {
            throw new IndexOutOfBoundsException("The coordinate values are expected to be between 0 and " + (n-1) + " but (" + row + ", " + col +") was given." );
        }

        /* If site is already open, no need to check anymore. */
        if (isSiteOpen[row][col] == 1) {
            return;
        }

        isSiteOpen[row][col] = 1;
        numOpenSites++;
        int index1D = xyTo1D(row, col);
        /* Site at the top of the system -> union with the top virtual site. */
        if (row == 0) {
            siteUF.union(topIndex, index1D);
        }

        /* Union with neighbors from North and South if open. */
        for (int r = row - 1; r <= row + 1; r++) {
            if (r != row && valid(r, col) && isSiteOpen[r][col] == 1) {
                int i = xyTo1D(r, col);
                siteUF.union(index1D, i);
            }
        }

        /* Union with neighbors from West and East if open. */
        for (int c = col - 1; c <= col + 1; c++) {
            if (c != col && valid(row, c) && isSiteOpen[row][c] == 1) {
                int i = xyTo1D(row, c);
                siteUF.union(index1D, i);
            }
        }

        /* Site at the bottom of the system and full -> union with the top virtual site */
        if (row == n-1 && isFull(row, col)) {
            siteUF.union(bottomIndex, index1D);
        }
    }

    /** Return whether the site at (row, col) is open. */
    public boolean isOpen(int row, int col) {
        if (!valid(row, col)) {
            throw new IndexOutOfBoundsException("The coordinate values are expected to be between 0 and " + (n-1) + " but (" + row + ", " + col +") was given." );
        }
        return isSiteOpen[row][col] == 1;
    }

    /** Return whether the site at (row, col) is full. */
    public boolean isFull(int row, int col) {
        if (!valid(row, col)) {
            throw new IndexOutOfBoundsException("The coordinate values are expected to be between 0 and " + (n-1) + " but (" + row + ", " + col +") was given." );
        }
        int index1D = xyTo1D(row, col);
        return siteUF.connected(topIndex, index1D);
    }

    /** Return the number of open sites in the system. */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /** Return whether the system percolates. */
    public boolean percolates() {
        return siteUF.connected(topIndex, bottomIndex);
    }
}
