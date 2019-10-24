import map.rbt.RedBlackTree;

public class Main {

    public static void main(String[] args) {
        RedBlackTree<Integer, Character> rbt = new RedBlackTree();

        rbt.add(1,'a');
        rbt.showTree();
        rbt.add(3,'l');
        rbt.showTree();
        rbt.add(2,'g');
        rbt.showTree();
/*        rbt.add(5,'o');
        rbt.showTree();
        rbt.add(6,'r');
        rbt.showTree();
        rbt.add(8,'y');
        rbt.showTree();
        rbt.add(7,'t');
        rbt.showTree();
        rbt.add(4,'m');
        rbt.showTree();
*/
    }
}
