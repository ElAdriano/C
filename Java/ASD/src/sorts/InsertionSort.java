package sorts;

public class InsertionSort implements SortingAlgorithm {

    public InsertionSort() {

    }

    @Override
    public double[] sort(double[] unsortedVector) {
        for (int i = 1; i < unsortedVector.length; i++) {
            int j = i - 1;
            double value = unsortedVector[i];
            while (j >= 0 && unsortedVector[j] > value) {
                unsortedVector[j + 1] = unsortedVector[j];
                j--;
            }
            unsortedVector[j + 1] = value;
        }
        return unsortedVector;
    }
    
}
