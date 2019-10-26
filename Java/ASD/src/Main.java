import map.rbt.RedBlackTree;

public class Main {

    public static void main(String[] args) {
        //RedBlackTree<Character, Character> rbt = new RedBlackTree();
        RedBlackTree<Integer, Integer> rbt = new RedBlackTree();

        //char[] values = {'a','l','g','o','r','y','t','m'};
        //char[] keys = {'a','l','g','o','r','y','t','m'};
        int[] keys = {156, 202, 243, 734, 58, 47, 73, 121, 49, 256};
        int[] values = {156, 202, 243, 734, 58, 47, 73, 121, 49, 256};

        for(int i = 0; i < keys.length; i++){
            rbt.add(keys[i],values[i]);
        }

        rbt.showTree();
    }
}
