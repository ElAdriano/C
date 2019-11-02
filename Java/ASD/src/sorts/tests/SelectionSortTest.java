package sorts.tests;

import org.junit.jupiter.api.Test;
import sorts.SelectionSort;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectionSortTest {

    @Test
    void sort() {
        DataGenerator dataGenerator = new DataGenerator();
        int dataAmount = 1000;
        double[] pessimisticData = dataGenerator.generatePessimisticDataForSelectionSort(dataAmount);
        double[] averageData = dataGenerator.generateAverageDataForSelectionSort(dataAmount);
        double[] optimisticData = dataGenerator.generateOptimisticDataForSelectionSort(dataAmount);

        SelectionSort selectionSort = new SelectionSort();

        double[] sortedData = selectionSort.sort(pessimisticData);
        for (int i = 0; i < sortedData.length - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }

        sortedData = selectionSort.sort(averageData);
        for (int i = 0; i < sortedData.length - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }

        sortedData = selectionSort.sort(optimisticData);
        for (int i = 0; i < sortedData.length - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }
    }

}