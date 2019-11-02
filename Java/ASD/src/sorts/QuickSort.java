package sorts;

import java.util.ArrayList;
import java.util.Stack;

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

            System.out.println(">>>>>>>>>>>>>>>>>>>");
            System.out.println(">>>>> start: " + el.start);
            System.out.println(">>>>> end: " + el.end);
            System.out.println("\n");
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
            swap(el.vector, j, el.start);

            if (i < el.end) {
                stack.add(new StackElement(el.vector, i, el.end));
            }
            if (el.start < j) {
                stack.add(new StackElement(el.vector, el.start, j));
            }
        }
        return unsortedVector;
    }

    private int getPivotIndex(double[] v, int s, int e) {
        int m = ((e - s) / 2) + s;

        double[] tmp = {v[s], v[m], v[e]};
        InsertionSort insortMed = new InsertionSort();
        tmp = insortMed.sort(tmp);

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

}
