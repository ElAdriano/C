package heap;

public class Heap< T extends Comparable<T>> implements HeapInterface<T>, ListSorterAlgorithm {

    private T[] vector;
    private final int LEN = 64;
    private int insertionIndex;

    public Heap() {
        vector = (T[]) new Comparable[LEN];
        insertionIndex = 0;
    }

    public int getHeapSize() {
        return insertionIndex;
    }

    public T getElement(int index) throws IllegalAccessException {
        if (index < insertionIndex) {
            return vector[index];
        } else {
            throw new IllegalAccessException("Index out of heap bounds.");
        }
    }

    @Override
    public void put(T item) {
        if (insertionIndex == vector.length) {
            T[] newVector = (T[]) new Comparable[2 * vector.length];
            System.arraycopy(vector, 0, newVector, 0, vector.length);
            vector = newVector;
        }
        vector[insertionIndex++] = item;
        int index = insertionIndex - 1;
        while (index > 0 && !heapCondition(index, (index - 1) / 2)) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    @Override
    public T pop() {
        if (insertionIndex == 0) {
            return null;
        } else {
            insertionIndex--;
            return vector[insertionIndex];
        }
    }

    @Override
    public double[] sort(double[] unsortedVector) {
        Heap<Double> h = new Heap<>();
        for (int i = 0; i < unsortedVector.length; i++) {
            h.put(unsortedVector[i]);
        }
        double[] result = new double[unsortedVector.length];
        int i = 0;
        while (h.insertionIndex > 0) {
            h.swap(0, h.insertionIndex - 1);
            double item = h.pop();
            result[i++] = item;
            h.heapify(0);
        }
        return result;
    }

    private void heapify(int index) {
        int leftItemIndex = 2 * index + 1;
        int rightItemIndex = 2 * index + 2;
        int maxItemIndex = index;
        if (leftItemIndex < insertionIndex && !heapCondition(leftItemIndex, maxItemIndex)) {
            maxItemIndex = leftItemIndex;
        }
        if (rightItemIndex < insertionIndex && !heapCondition(rightItemIndex, maxItemIndex)) {
            maxItemIndex = rightItemIndex;
        }
        if (maxItemIndex != index) {
            swap(index, maxItemIndex);
            heapify(maxItemIndex);
        }
    }

    private boolean heapCondition(int firstItemIndex, int secondItemIndex) {
        return vector[firstItemIndex].compareTo(vector[secondItemIndex]) <= 0;
    }

    private void swap(int firstItemIndex, int secondItemIndex) {
        T item = vector[firstItemIndex];
        vector[firstItemIndex] = vector[secondItemIndex];
        vector[secondItemIndex] = item;
    }

    public void showHeap() {
        if (insertionIndex == 0) {
            System.out.println("Empty heap.");
            return;
        }
        for (int i = 0; i < insertionIndex; i++) {
            System.out.println(vector[i]);
        }
    }

}
