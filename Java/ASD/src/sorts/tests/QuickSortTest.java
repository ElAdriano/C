package sorts.tests;

import org.junit.jupiter.api.Test;
import sorts.QuickSort;
import sorts.tests.DataGenerator;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void sort() {
        DataGenerator dataGenerator = new DataGenerator();

        double[] pessimisticData = dataGenerator.generatePessimisticDataForQuickSort(10 * 1000);
        double[] averageData = dataGenerator.generateAverageDataForQuickSort(10 * 1000);
        double[] optimisticData = dataGenerator.generateOptimisticDataForQuickSort(10 * 1000);

        QuickSort quickSort = new QuickSort();

        double[] sortedData = quickSort.sort(pessimisticData);
        /*for (int i = 0; i < 1000 * 1000 - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }
        System.out.println("pessimistic case done");

        sortedData = quickSort.sort(averageData);
        for (int i = 0; i < 1000 * 1000 - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }
        System.out.println("average case done");*/

        sortedData = quickSort.sort(optimisticData);
        for (int i = 0; i < 10 * 1000 - 1; i++) {
            assertEquals(true, sortedData[i] <= sortedData[i + 1]);
        }
    }

}