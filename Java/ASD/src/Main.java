
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import map.rbt.RedBlackTree;

public class Main {

    public static void main(String[] args) {
        makeStatistics(100000);
    }

    private static void makeStatistics(int amount) {
        RedBlackTree<Double, String> rbt = new RedBlackTree<>();

        ArrayList<Double> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        generateDataForStatistics(amount, keys, values);

        long prevTime;
        long currTime;

        long[] insertionTimesVector = new long[keys.size()];
        long[] gettingTimesVector = new long[keys.size()];

        for (int i = 0; i < amount; i++) {
            prevTime = System.nanoTime();
            rbt.setValue(keys.get(i), values.get(i));
            currTime = System.nanoTime();

            insertionTimesVector[i] = currTime - prevTime;

            prevTime = System.nanoTime();
            rbt.getValue(keys.get(i));
            currTime = System.nanoTime();

            gettingTimesVector[i] = currTime - prevTime;
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("setValueTimes.txt"));
            for (int i = 0; i < keys.size(); i++) {
                if (i % 150 == 0) {
                    bw.write(String.valueOf(i + 1) + " " + String.valueOf(insertionTimesVector[i]));
                    bw.newLine();
                }
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter("getValueTimes.txt"));
            for (int i = 0; i < keys.size(); i++) {
                if (i % 150 == 0) {
                    bw.write(String.valueOf(i + 1) + " " + String.valueOf(gettingTimesVector[i]));
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateDataForStatistics(int amount, ArrayList<Double> keys, ArrayList<String> values) {
        Random randomizer = new Random();

        for (int i = 0; i < amount; i++) {
            keys.add(randomizer.nextDouble());

            StringBuilder s = new StringBuilder();
            int len = randomizer.nextInt(50);
            for (int j = 0; j < len; j++) {
                char c = (char) (randomizer.nextInt(26) + 'a');
                s.append(c);
            }

            values.add(s.toString());
        }
    }

}
