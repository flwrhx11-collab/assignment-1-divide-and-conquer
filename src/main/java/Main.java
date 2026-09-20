import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        runCorrectnessTests();
        System.out.println("Tests passed. Running experiments...");
        Experiment.runExperiments();
        System.out.println("Experiments finished. Check results/results.csv");
    }

    private static void runCorrectnessTests() {
        int[] testArr = {5, 2, 9, 1, 5, 6, 3, 8};

        int[] mergeArr = testArr.clone();
        MergeSorter.sort(mergeArr);

        int[] quickArr = testArr.clone();
        QuickSorter.sort(quickArr);

        int[] expected = testArr.clone();
        Arrays.sort(expected);

        if (!Arrays.equals(mergeArr, expected)) throw new RuntimeException("MergeSort failed");
        if (!Arrays.equals(quickArr, expected)) throw new RuntimeException("QuickSort failed");

        int selectResult = DeterministicSelector.select(testArr.clone(), 3);
        if (selectResult != expected[3]) throw new RuntimeException("Select failed");

        Point[] points = {new Point(0, 0), new Point(0, 1), new Point(10, 10)};
        double dist = ClosestPairSolver.findClosestPair(points);
        if (dist != 1.0) throw new RuntimeException("ClosestPair failed");
    }
}