package sorts;

import sorts.tests.DataGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SelectionSort implements SortingAlgorithm {

    public SelectionSort() {

    }

    @Override
    public double[] sort(double[] unsortedVector) {
        for (int i = 0; i < unsortedVector.length; i++) {
            int minIndex = i;
            int j = i + 1;
            while (j < unsortedVector.length) {
                if (unsortedVector[minIndex] > unsortedVector[j]) {
                    minIndex = j;
                }
                j++;
            }
            if (minIndex != i) {
                double tmp = unsortedVector[minIndex];
                unsortedVector[minIndex] = unsortedVector[i];
                unsortedVector[i] = tmp;
            }
        }
        return unsortedVector;
    }

    public void generateDataForChart(int amount) {
        SelectionSort selectionSort = new SelectionSort();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("PessimisticDataSelectionSort.txt"));
            long beforeSortTime, afterSortTime;
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generatePessimisticDataForSelectionSort(i);

                    beforeSortTime = System.nanoTime();
                    selectionSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter("AverageDataForSelectionSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generateAverageDataForSelectionSort(i);

                    beforeSortTime = System.nanoTime();
                    selectionSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter("OptimisticDataForSelectionSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generateOptimisticDataForSelectionSort(i);

                    beforeSortTime = System.nanoTime();
                    selectionSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();
            System.out.println("Data generating completed");
        } catch (IOException e) {
        }

    }

}
