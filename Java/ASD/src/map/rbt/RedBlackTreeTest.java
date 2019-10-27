package map.rbt;

import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {

    @Test
    public void testSetValue() throws NoKeyInTreeException {
        testSetValueForSmallData();
        testSetValueForBigData();
    }

    @Test
    public void testGetValue() throws NoKeyInTreeException {
        testGetValueForSmallData();
        testGetValueForBigData();
    }

    private void testSetValueForSmallData() {
        RedBlackTree<Double, Double> rbt = new RedBlackTree<>();
        double[] keys = {156, 202, 243, 734, 58, 47, 73, 121, 49, 256};
        double[] valuesBeforeSet = {156, 202, 243, 734, 58, 47, 73, 121, 49, 256};

        for (int i = 0; i < Math.min(keys.length, valuesBeforeSet.length); i++) {
            rbt.setValue(keys[i], valuesBeforeSet[i]);
        }

        for (int i = 0; i < Math.min(keys.length, valuesBeforeSet.length); i++) {
            Node tmp = rbt.findNode(keys[i]);
            if (tmp != null) {
                assertEquals(keys[i], tmp.getKey());
            } else {
                assertEquals(keys[i], null);
            }
        }

        double[] keysToValueSet = {156, 307, 47, 512, 121, 1024, 23, 7, 18, 256, 24, 503};
        double[] valuesAfterSet = {245, 408, 49, 21, 105, 86, 412, 302, 51, 64, 102, 113};

        for (int i = 0; i < Math.min(keysToValueSet.length, valuesAfterSet.length); i++) {
            try {
                rbt.setValue(keysToValueSet[i], valuesAfterSet[i]);
                assertEquals(valuesAfterSet[i], rbt.findNode(keysToValueSet[i]).getValue());
            } catch (NoKeyInTreeException e) {
                assertEquals(false, containsKey(keys, e.getKey()));
            }
        }
    }

    private void testSetValueForBigData() {
        RedBlackTree<String, String> rbt = new RedBlackTree<>();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> valuesToSet = new ArrayList<>();
        ArrayList<String> keysToValueSet = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            String s = generateString();
            rbt.setValue(s, s);
            keys.add(s);
            if (i % 3 == 0) {
                keysToValueSet.add(s);
                valuesToSet.add(generateString());
            }
        }

        for (int i = 0; i < keysToValueSet.size(); i++) {
            if (i % 5 == 0) {
                keysToValueSet.set(i, generateString());
            }
        }

        for (int i = 0; i < keysToValueSet.size(); i++) {
            try {
                rbt.setValue(keysToValueSet.get(i), valuesToSet.get(i));
                assertEquals(valuesToSet.get(i), rbt.findNode(keysToValueSet.get(i)).getValue());
            } catch (NoKeyInTreeException e) {
                assertEquals(false, keys.contains(e.getKey()));
            }
        }
    }

    private void testGetValueForSmallData() {
        RedBlackTree<Double, Double> rbt = new RedBlackTree<>();
        double[] keys = {156, 202, 243, 734, 58, 47, 73, 121, 49, 256};
        double[] values = {156, 202, 243, 734, 58, 47, 73, 121, 49, 256};
        double[] keysToCheckDouble = {156, 202, 2243, 74, 158, 47, 73, 121, 49, 256};

        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            rbt.setValue(keys[i], values[i]);
        }

        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            try {
                assertEquals(values[i], rbt.getValue(keysToCheckDouble[i]));
            } catch (NoKeyInTreeException e) {
                assertEquals(false, containsKey(keys, e.getKey()));
            }
        }
    }

    private void testGetValueForBigData() {
        RedBlackTree<String, String> rbt = new RedBlackTree<>();
        ArrayList<String> keysToCheck = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            String s = generateString();
            rbt.setValue(s, s);
            keys.add(s);
            if (i % 2 == 0) {
                keysToCheck.add(s);
            } else {
                keysToCheck.add(generateString());
            }
        }

        for (int i = 0; i < keysToCheck.size(); i++) {
            try {
                assertEquals(keysToCheck.get(i), rbt.getValue(keysToCheck.get(i)));
            } catch (NoKeyInTreeException e) {
                assertEquals(false, keys.contains(e.getKey()));
            }
        }
    }

    private boolean containsKey(double[] a, Object key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == (double) key) {
                return true;
            }
        }
        return false;
    }

    private String generateString() {
        Random randomizer = new Random();
        int len = randomizer.nextInt(50);
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char c = (char) (randomizer.nextInt(26) + 'a');
            s.append(c);
        }

        return s.toString();
    }

}
