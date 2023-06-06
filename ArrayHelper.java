
public class ArrayHelper {
    // cast from int[] to double[] and vice-versa
    private static double[] cast(int[] arr) {
        double[] newArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = (double)arr[i];
        }
        return newArr;
    }
    private static int[] cast(double[] arr) {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = (int)arr[i];
        }
        return newArr;
    }

    // resize string array up to a length
    public static String[] trim(String[] arr, int length) {
        String[] newArr = new String[length];
        for (int i = 0; i < length; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    // resize double array up to a length
    public static double[] trim(double[] arr, int length) {
        double[] newArr = new double[length];
        for (int i = 0; i < length; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }
    public static int[] trim(int[] arr, int length) {
        return cast(trim(cast(arr), length));
    }

    // clone functions to prevent mutation
    public static double[] clone(double[] arr) {
        return trim(arr, arr.length);
    }
    public static int[] clone(int[] arr) {
        return trim(arr, arr.length);
    }

    // all indices of a value in an int array
    public static int[] indices(int[] arr, int val) {
        int[] indices = new int[arr.length];
        int length = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                indices[length] = i;
                length++;
            }
        }
        return trim(indices, length);
    }
}
