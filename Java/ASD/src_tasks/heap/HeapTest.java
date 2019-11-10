package heap;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class HeapTest {

    public HeapTest() {
    }

    @Test
    public void testGetHeapSize() {
        Random randomizer = new Random();
        Heap<Double> h = new Heap<>();
        int size = h.getHeapSize();
        assertEquals(0, size);

        for (int i = 0; i < 50000; i++) {
            h.put(randomizer.nextDouble());
        }

        assertEquals(50000, h.getHeapSize());

        for (int i = 0; i < 100000; i++) {
            h.pop();
        }

        assertEquals(0, h.getHeapSize());
    }

    @Test
    public void testGetElement() {
        Random randomizer = new Random();
        Heap<String> h = new Heap<>();

        for (int i = 0; i < 1000000; i++) {
            int len = randomizer.nextInt(50);
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < len; j++) {
                char c = (char) (randomizer.nextInt(26) + 'a');
                s.append(c);
            }
            h.put(s.toString());
        }

        for (int i = 0; i < randomizer.nextInt(1000000); i++) {
            h.pop();
        }

        for (int i = 0; i < 500000; i++) {
            int index = randomizer.nextInt(1000000);
            try {
                String s = h.getElement(index);
                if (index >= h.getHeapSize()) {
                    assertEquals(s, null);
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage() + " index: " + index);
            }
        }
    }

    @Test
    public void testPut() {
        Random randomizer = new Random();

        Heap<Double> h1 = new Heap<>();
        for (int i = 0; i < 10000; i++) {
            double el = randomizer.nextDouble();

            int sizeBefore = h1.getHeapSize();
            h1.put(el);

            for (int j = h1.getHeapSize() - 1; j > 0; j--) {
                try {
                    int index = j;
                    while (index > 0) {
                        assertEquals(h1.getElement(index) <= h1.getElement((index - 1) / 2), true);
                        index = (index - 1) / 2;
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }

            int sizeAfter = h1.getHeapSize();
            assertEquals(sizeAfter - sizeBefore, 1);
        }

        Heap<String> h2 = new Heap<>();
        for (int i = 0; i < 10000; i++) {
            int len = randomizer.nextInt(50);
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < len; j++) {
                char c = (char) (randomizer.nextInt(26) + 'a');
                s.append(c);
            }

            int sizeBefore = h2.getHeapSize();
            h2.put(s.toString());

            for (int j = h2.getHeapSize() - 1; j > 0; j--) {
                try {
                    int index = j;
                    while (index > 0) {
                        assertEquals(h2.getElement(index).compareTo(h2.getElement((index - 1) / 2)) <= 0, true);
                        index = (index - 1) / 2;
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }

            int sizeAfter = h2.getHeapSize();
            assertEquals(sizeAfter - sizeBefore, 1);
        }

        Heap<Integer> h3 = new Heap<>();
        for (int i = 0; i < 10000; i++) {
            int el = (int) (randomizer.nextInt() * Math.pow(-1, i));

            int sizeBefore = h3.getHeapSize();
            h3.put(el);

            for (int j = h3.getHeapSize() - 1; j > 0; j--) {
                try {
                    int index = j;
                    while (index > 0) {
                        assertEquals(h3.getElement(index) <= h3.getElement((index - 1) / 2), true);
                        index = (index - 1) / 2;
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }

            int sizeAfter = h3.getHeapSize();
            assertEquals(sizeAfter - sizeBefore, 1);
        }
    }

    @Test
    public void testPop() {
        Random randomizer = new Random();

        Heap<Double> h = new Heap<>();
        for (int i = 0; i < 100000; i++) {
            double el = randomizer.nextDouble();
            h.put(el);
        }

        for (int i = 0; i < 100000; i++) {
            int sizeBeforePop = h.getHeapSize();
            h.pop();
            int sizeAfterPop = h.getHeapSize();
            assertEquals(sizeBeforePop - sizeAfterPop, 1);

            int index = sizeAfterPop - 1;
            while (index > 0) {
                try {
                    assertEquals(h.getElement(index) <= h.getElement((index - 1) / 2), true);
                    index = (index - 1) / 2;
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Test
    public void testSort() {
        Random randomizer = new Random();
        Heap<Double> h1 = new Heap<>();

        double[] unsortedDoubleVector = new double[1000000];
        for (int i = 0; i < 1000000; i++) {
            unsortedDoubleVector[i] = randomizer.nextDouble();
        }

        double[] sortedDoubleVector = h1.sort(unsortedDoubleVector);
        for (int i = 0; i < 1000000 - 1; i++) {
            assertEquals(sortedDoubleVector[i] >= sortedDoubleVector[i + 1], true);
        }

        double[] unsortedDoubleEmptyVector = new double[0];
        double[] sortedEmptyVector = h1.sort(unsortedDoubleEmptyVector);
        for (int i = 0; i < sortedEmptyVector.length; i++) {
            assertEquals(sortedEmptyVector[i] >= sortedEmptyVector[i + 1], true);
        }
    }

}
