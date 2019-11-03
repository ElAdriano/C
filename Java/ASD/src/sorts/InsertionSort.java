package sorts;

import sorts.tests.DataGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    public void generateDataForChart(int amount) {
        InsertionSort insertionSort = new InsertionSort();

        try {
            System.out.println("Start generating data");

            BufferedWriter bw;
            long beforeSortTime, afterSortTime;

            /*bw = new BufferedWriter(new FileWriter("AverageDataForInsertionSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generateAverageDataForInsertionSort(i);

                    beforeSortTime = System.nanoTime();
                    insertionSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();
            System.out.println("Average data generating completed");*/

            bw = new BufferedWriter(new FileWriter("OptimisticDataForInsertionSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generateOptimisticDataForInsertionSort(i);

                    beforeSortTime = System.nanoTime();
                    insertionSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();
            System.out.println("Optimistic data generating completed");

            bw = new BufferedWriter(new FileWriter("PessimisticDataInsertionSort.txt"));
            for (int i = 100; i < amount; i += 100) {
                DataGenerator dataGenerator = new DataGenerator();
                double avgTime = 0;

                for (int k = 1; k <= 10; k++) {
                    double[] data = dataGenerator.generatePessimisticDataForInsertionSort(i);

                    beforeSortTime = System.nanoTime();
                    insertionSort.sort(data);
                    afterSortTime = System.nanoTime();

                    avgTime += (afterSortTime - beforeSortTime) / 10;
                }

                bw.write(String.valueOf(i) + " " + String.valueOf((long) avgTime));
                bw.newLine();
            }
            bw.close();
            System.out.println("Pessimistic data generating completed");
            System.out.println("Data generating completed");
        } catch (IOException e) {
        }

    }

}
