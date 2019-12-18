package sorts;

import sorts.tests.DataGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuickSort implements SortingAlgorithm {

    private class StackElement {
        int start;
        int end;
        double[] vector;

        public StackElement(double[] v, int s, int e) {
            start = s;
            end = e;
            vector = v;
        }
    }

    private ArrayList<StackElement> stack;

    public QuickSort() {
        stack = new ArrayList<>();
    }

    @Override
    public double[] sort(double[] unsortedVector) {
        stack.add(new StackElement(unsortedVector, 0, unsortedVector.length - 1));

        while (!stack.isEmpty()) {
            StackElement el = stack.get(0);
            stack.remove(0);

            if (el.end - el.start == 1) {
                if (el.vector[el.start] > el.vector[el.end]) {
                    swap(el.vector, el.start, el.end);
                }
                continue;
            }

            int pivotIndex = getPivotIndex(el.vector, el.start, el.end);
            if (pivotIndex != el.start) {
                swap(el.vector, el.start, pivotIndex);
            }

            double pivot = el.vector[el.start];
            int i = el.start + 1;
            int j = el.end;

            while (i < j) {
                while (el.vector[i] <= pivot) i++;
                while (el.vector[j] > pivot) j--;
                if (i < j) {
                    swap(el.vector, i, j);
                } else {
                    break;
                }
            }

            if (i > j) {
                i = j;
            }
            swap(el.vector, j, el.start);

            if (i < el.end) {
                stack.add(new StackElement(el.vector, i + 1, el.end));
            }
            if (el.start < j) {
                stack.add(new StackElement(el.vector, el.start, j - 1));
            }
        }
        return unsortedVector;
    }

    private int getPivotIndex(double[] v, int s, int e) {
        int m = ((e - s) / 2) + s;

        double[] tmp = {v[s], v[m], v[e]};
        InsertionSort inSortMed = new InsertionSort();
        tmp = inSortMed.sort(tmp);

        if (tmp[1] == v[s]) {
            return s;
        } else if (tmp[1] == v[m]) {
            return m;
        } else {
            return e;
        }
    }

    private void swap(double[] v, int index1, int index2) {
        double tmp = v[index1];
        v[index1] = v[index2];
        v[index2] = tmp;
    }

    public void generateDataForChart(int amount) {
        QuickSort quickSort = new QuickSort();

        try{
            System.out.println("Start generating data");

            BufferedWriter bw;
            long beforeSortTime, afterSortTime;

            bw = new BufferedWriter(new FileWriter("PessimisticDataForQuickSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generatePessimisticDataForQuickSort(i);

                    beforeSortTime = System.nanoTime();
                    quickSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();
            System.out.println("Pessimistic data generating completed");

            bw = new BufferedWriter(new FileWriter("AverageDataForQuickSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generateAverageDataForQuickSort(i);

                    beforeSortTime = System.nanoTime();
                    quickSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();
            System.out.println("Average data generating completed");

            bw = new BufferedWriter(new FileWriter("OptimisticDataForQuickSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generateOptimisticDataForQuickSort(i);

                    beforeSortTime = System.nanoTime();
                    quickSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();
            System.out.println("Optimistic data generating completed");
            System.out.println("Data generating completed");
        }catch(IOException e){
        }
    }

}
