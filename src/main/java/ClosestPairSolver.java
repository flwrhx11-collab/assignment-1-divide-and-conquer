import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    public static double bruteForce(Point[] points, int low, int high) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                double dist = points[i].distanceTo(points[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }
        return minDistance;
    }

    public static double findClosestPair(Point[] points) {
        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[pointsCopy.length];
        return closestPair(pointsCopy, aux, 0, pointsCopy.length - 1);
    }

    private static double closestPair(Point[] points, Point[] aux, int low, int high) {
        if (high - low <= 3) {
            Arrays.sort(points, low, high + 1, Comparator.comparingDouble(p -> p.y));
            return bruteForce(points, low, high);
        }

        int mid = low + (high - low) / 2;
        double midX = points[mid].x;

        double dl = closestPair(points, aux, low, mid);
        double dr = closestPair(points, aux, mid + 1, high);
        double d = Math.min(dl, dr);

        merge(points, aux, low, mid, high);

        int stripCount = 0;
        for (int i = low; i <= high; i++) {
            if (Math.abs(points[i].x - midX) < d) {
                aux[stripCount++] = points[i];
            }
        }

        for (int i = 0; i < stripCount; i++) {
            for (int j = i + 1; j < stripCount && (aux[j].y - aux[i].y) < d; j++) {
                double dist = aux[i].distanceTo(aux[j]);
                if (dist < d) {
                    d = dist;
                }
            }
        }
        return d;
    }

    private static void merge(Point[] points, Point[] aux, int low, int mid, int high) {
        System.arraycopy(points, low, aux, low, high - low + 1);

        int i = low;
        int j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                points[k] = aux[j++];
            } else if (j > high) {
                points[k] = aux[i++];
            } else if (aux[i].y <= aux[j].y) {
                points[k] = aux[i++];
            } else {
                points[k] = aux[j++];
            }
        }
    }
}