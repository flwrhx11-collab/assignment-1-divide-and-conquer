import java.util.Random;

public class QuickSorter {
    private static final Random RANDOM = new Random();

    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(array, low, high);

            if (pivotIndex - low < high - pivotIndex) {
                sort(array, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                sort(array, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivotIndex = low + RANDOM.nextInt(high - low + 1);
        swap(array, pivotIndex, high);
        int pivot = array[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}