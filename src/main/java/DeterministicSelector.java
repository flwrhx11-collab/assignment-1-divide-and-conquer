public class DeterministicSelector {

    public static int select(int[] array, int k) {
        if (array == null || array.length == 0 || k < 0 || k >= array.length) {
            throw new IllegalArgumentException();
        }
        return select(array, 0, array.length - 1, k);
    }

    private static int select(int[] array, int low, int high, int k) {
        while (low < high) {
            int pivotValue = getPivotValue(array, low, high);
            int pivotIndex = partition(array, low, high, pivotValue);

            if (pivotIndex == k) {
                return array[pivotIndex];
            } else if (k < pivotIndex) {
                high = pivotIndex - 1;
            } else {
                low = pivotIndex + 1;
            }
        }
        return array[low];
    }

    private static int getPivotValue(int[] array, int low, int high) {
        if (high - low < 5) {
            insertionSort(array, low, high);
            return array[low + (high - low) / 2];
        }

        for (int i = low; i <= high; i += 5) {
            int subRight = i + 4;
            if (subRight > high) {
                subRight = high;
            }
            int medianIndex = getMedianIndex(array, i, subRight);
            swap(array, medianIndex, low + (i - low) / 5);
        }

        int mid = (high - low) / 10 + 1;
        return select(array, low, low + (high - low) / 5, low + mid - 1);
    }

    private static int getMedianIndex(int[] array, int low, int high) {
        insertionSort(array, low, high);
        return low + (high - low) / 2;
    }

    private static int partition(int[] array, int low, int high, int pivotValue) {
        int i = low;
        for (int j = low; j <= high; j++) {
            if (array[j] == pivotValue) {
                swap(array, j, high);
                break;
            }
        }

        int pivot = array[high];
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, high);
        return i;
    }

    private static void insertionSort(int[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= low && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}