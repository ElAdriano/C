package sorts.tests;

import org.junit.jupiter.api.Test;
import sorts.InsertionSort;

import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test
    void sort() {
        DataGenerator dataGenerator = new DataGenerator();
        double[] pessimisticData = dataGenerator.generatePessimisticDataForInsertionSort(1000 * 1000);
        double[] averageData = dataGenerator.generateAverageDataForInsertionSort(1000 * 1000);
        double[] optimisticData = dataGenerator.generateOptimisticDataForInsertionSort(1000 * 1000);

        InsertionSort insertionSort = new InsertionSort();

        double[] sortedData = insertionSort.sort(pessimisticData);
        for (int i = 0; i < 1000 * 1000 - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }

        sortedData = insertionSort.sort(averageData);
        for (int i = 0; i < 1000 * 1000 - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }

        sortedData = insertionSort.sort(optimisticData);
        for (int i = 0; i < 1000 * 1000 - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }
    }

}