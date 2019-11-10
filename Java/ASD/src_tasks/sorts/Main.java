package sorts;

import sorts.tests.DataGenerator;

public class Main {

    public static void main(String[] args) {
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.generateDataForChart(10000);

        /*
        jest OK
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.generateDataForChart(10000);
        */

        /*
        jest OK
        QuickSort quickSort = new QuickSort();
        quickSort.generateDataForChart(10000);
        */
    }

}
