package sorts.tests;

import org.junit.jupiter.api.Test;
import sorts.QuickSort;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuickSortTest {

    @Test
    void sort() {
        DataGenerator dataGenerator = new DataGenerator();

        double[] pessimisticData = dataGenerator.generatePessimisticDataForQuickSort(1000 * 1000);
        double[] averageData = dataGenerator.generateAverageDataForQuickSort(1000 * 1000);
        double[] optimisticData = dataGenerator.generateOptimisticDataForQuickSort(1000 * 1000);

        QuickSort quickSort = new QuickSort();

        double[] sortedData = quickSort.sort(pessimisticData);
        for (int i = 0; i < sortedData.length - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }

        sortedData = quickSort.sort(averageData);
        for (int i = 0; i < sortedData.length - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }

        sortedData = quickSort.sort(optimisticData);
        for (int i = 0; i < sortedData.length - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }
    }

}