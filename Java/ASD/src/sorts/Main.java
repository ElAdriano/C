package sorts;

import sorts.tests.DataGenerator;

public class Main {

    public static void main(String[] args) {
        //DataGenerator dg = new DataGenerator();

        InsertionSort insertionSort = new InsertionSort();
        //double[] data = dg.generateAverageDataForInsertionSort(10000);
        //insertionSort.sort(data);
       /* for(int i = 0; i < 10000-1; i++){
            System.out.println(data[i]);
        }*/
        insertionSort.generateDataForChart(100000);
        /*QuickSort qs = new QuickSort();
        double[] data = {3.5, 1.23, 3.14, 2.71, -1, -2.3, -3.14, -2, 78};
        qs.sort(data);
        for (int i = 0; i < data.length - 1; i++) {
            System.out.println(data[i] < data[i + 1]);
        }*/
    }

}
