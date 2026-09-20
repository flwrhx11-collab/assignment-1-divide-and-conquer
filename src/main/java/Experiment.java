import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {
    private static final int[] SIZES = {1000, 10000, 100000};
    private static final String[] TYPES = {"Random", "Sorted", "Reverse-sorted", "Duplicate-heavy"};

    public static void runExperiments() {
        try (FileWriter writer = new FileWriter("results/results.csv")) {
            writer.write("Algorithm,InputSize,InputType,Time_ns\n");

            for (int size : SIZES) {
                for (String type : TYPES) {
                    int[] array = generateArray(size, type);

                    long time = measureMergeSort(array.clone());
                    writer.write(String.format("MergeSort,%d,%s,%d\n", size, type, time));

                    time = measureQuickSort(array.clone());
                    writer.write(String.format("QuickSort,%d,%s,%d\n", size, type, time));

                    time = measureSelect(array.clone());
                    writer.write(String.format("DeterministicSelect,%d,%s,%d\n", size, type, time));
                }
            }

            for (int size : SIZES) {
                Point[] points = generatePoints(size);
                long startTime = System.nanoTime();
                ClosestPairSolver.findClosestPair(points);
                long endTime = System.nanoTime();
                writer.write(String.format("ClosestPair,%d,Random,%d\n", size, "Random", endTime - startTime));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (type.equals("Random")) arr[i] = random.nextInt();
            else if (type.equals("Sorted")) arr[i] = i;
            else if (type.equals("Reverse-sorted")) arr[i] = size - i;
            else if (type.equals("Duplicate-heavy")) arr[i] = random.nextInt(10);
        }
        return arr;
    }

    private static Point[] generatePoints(int size) {
        Point[] points = new Point[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }

    private static long measureMergeSort(int[] array) {
        long start = System.nanoTime();
        MergeSorter.sort(array);
        return System.nanoTime() - start;
    }

    private static long measureQuickSort(int[] array) {
        long start = System.nanoTime();
        QuickSorter.sort(array);
        return System.nanoTime() - start;
    }

    private static long measureSelect(int[] array) {
        long start = System.nanoTime();
        DeterministicSelector.select(array, array.length / 2);
        return System.nanoTime() - start;
    }
}