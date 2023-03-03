public class ArrayMax {
    /** Returns the maximum value from arr. */
    public static int max(int[] arr) {
        int maxValue = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (maxValue < arr[i]) {
                maxValue = arr[i];
            }
        }
        return maxValue;
    }
    public static void main(String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.println("Max value = " + max(numbers));
    }
}
