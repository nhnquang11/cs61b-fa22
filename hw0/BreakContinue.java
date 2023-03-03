public class BreakContinue {
    /** Replaces each element a[i] with the sum of a[i] through a[i+n],
     *  but only if a[i] is positive valued. If there are not enough values
     *  because we reach the end of the array, we sum only as many values as
     *  we have.
     *  */
    public static void windowPosSum(int[] a, int n) {
        int[] posSumArr = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            posSumArr[i] = getPosSum(a, i, n);
        }
        /** Replace each element in the array with their pos sum value */
        for (int i = 0; i < a.length; i++) {
            a[i] = posSumArr[i];
        }
    }

    /** Calculate pos sum value of N elements for element in array A at index I. */
    public static int getPosSum(int[] a, int i, int n) {
        if (a[i] < 0) {
            return a[i];
        }
        int total = 0;
        for (int j = 0; j <= n; j++) {
            /** Array out of bound. */
            if (i + j == a.length) {
                break;
            }
            total += a[i + j];
        }
        return total;
    }
    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSum(a, n);
        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }
}
