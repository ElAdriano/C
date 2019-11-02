package sorts.tests;

import java.util.Random;

public class DataGenerator {

    private Random randomizer;

    public DataGenerator() {
        randomizer = new Random();
    }

    public double[] generatePessimisticDataForSelectionSort(int amount) {
        double[] data = new double[amount];
        for (int i = amount - 1; i >= 0; i--) {
            data[i] = 0.0625 * i;
        }
        return data;
    }

    public double[] generateAverageDataForSelectionSort(int amount) {
        double[] data = new double[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = randomizer.nextDouble();
        }
        return data;
    }

    public double[] generateOptimisticDataForSelectionSort(int amount) {
        double[] data = new double[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = 0.0625 * i;
        }
        return data;
    }

    public double[] generatePessimisticDataForInsertionSort(int amount) {
        double[] data = new double[amount];
        for (int i = amount - 1; i >= 0; i--) {
            data[i] = 0.0625 * i;
        }
        return data;
    }

    public double[] generateAverageDataForInsertionSort(int amount) {
        double[] data = new double[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = randomizer.nextDouble();
        }
        return data;
    }

    public double[] generateOptimisticDataForInsertionSort(int amount) {
        double[] data = new double[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = 0.0625 * i;
        }
        return data;
    }

    public double[] generatePessimisticDataForQuickSort(int amount) {
        double[] data = new double[amount];
        for (int i = 1; i < amount / 2; i++) {
            data[i] = 0.0625 * (amount - i);
        }
        int val = 1;
        for (int i = amount / 2; i < amount; i++) {
            data[i] = 0.0625 * val++;
        }
        data[0] = 0.0625 * (amount / 2);
        return data;
    }

    public double[] generateAverageDataForQuickSort(int amount) {
        double[] data = new double[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = randomizer.nextDouble();
        }
        return data;
    }

    public double[] generateOptimisticDataForQuickSort(int amount) {
        double[] data = new double[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = 0.0625 * i;
        }
        return data;
    }

}
