package map.rbt;

import java.util.ArrayList;

public class RedBlackTree<K extends Comparable<K>, V> implements MapInterface {
    private Node root;
    private Node leaf;

    public RedBlackTree() {
        this.root = null;
        this.leaf = new Node(null, null, null, null, null, false);
    }

    public void add(K key, V value) {
        Node<K, V> node = new Node<>(key, value, null, leaf, leaf, true);

        Node prevNode = root;
        Node pointer = root;

        if (root == null) {
            root = node;
            root.setRed(false);
            return;
        } else {
            while (pointer != leaf) {
                prevNode = pointer;
                if (pointer.getKey().compareTo(node.getKey()) < 0) {
                    pointer = pointer.getRightSon();
                } else if (pointer.getKey().compareTo(node.getKey()) == 0) {
                    pointer.setValue(node.getValue());
                    return;
                } else {
                    pointer = pointer.getLeftSon();
                }
            }
            pointer = prevNode;

            if (pointer.getKey().compareTo(node.getKey()) < 0) {
                pointer.setRightSon(node);
            } else {
                pointer.setLeftSon(node);
            }
            node.setParent(pointer);
        }
        reorganize(node);
    }

    private void reorganize(Node node) {
        int incorrectCase = checkTreeCorrectionInNode(node.getParent());
        System.out.println(">>>>>>>>>>>>>>>>>>> reorganize");
        System.out.println(node.getValue());
        System.out.println(incorrectCase);
        System.out.println(">>>>>>>>>>>>>>>>>>> reorganize");
        Node grandNode = node.getParent();
        switch(incorrectCase){
            case 1:     // when node is red and has red left child
                rightRotation(grandNode);
                node.setRed(false);
                node.getLeftSon().setRed(false);
                node.getRightSon().setRed(false);
                node.setRed(true);
                break;
            case 2:  // debugged                       // when node is red and has red right child
                leftRotation(node.getParent());
                grandNode = node.getParent();
                rightRotation(grandNode);
                node.setRed(false);
                node.getLeftSon().setRed(false);
                node.getRightSon().setRed(false);
                break;
            case 3: // debugged
                leftRotation(node.getParent());
                node.setRed(false);
                node.getLeftSon().setRed(true);
                break;
            case 4:
                node.getLeftSon().setRed(false);
                node.getRightSon().setRed(false);
                break;
        }
    }

    private int checkTreeCorrectionInNode(Node node) {
        if(node != null){
            System.out.println(">>>>>>>>>>>>>>>>>>>> checkTreeCorrection");
            System.out.println(node.getValue());
            System.out.println(">>>>>>>>>>>>>>>>>>>> checkTreeCorrection");
            if(node.getLeftSon() != leaf && node.getRightSon() == leaf){        // node has only left son
                if(node.isRed()){                                               // when node is red and has red child
                    return 1;
                }
            }else if(node.getLeftSon() == leaf && node.getRightSon() != leaf){  // node has only right son
                if(node.isRed() && node.getRightSon().isRed()){                 // node and right son is red
                    return 2;
                }
                if(node.getRightSon().isRed()){                                 // node has red right son and node is black
                    return 3;
                }
            }else{                                                              // node has both sons
                if(node.getLeftSon().isRed() && node.getRightSon().isRed()){
                    return 4;
                }
            }
        }
        return 0;
    }

    private void leftRotation(Node node) {
        System.out.println(">>>>>>>>>>>>>>> LEFT ROTATION");
        System.out.println(node.getValue());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Node rsNode = node.getRightSon();
        node.setRightSon(rsNode.getLeftSon());

        if (rsNode.getLeftSon() != leaf) {
            rsNode.getLeftSon().setParent(node);
        }

        rsNode.setParent(node.getParent());
        if (node.getParent() == null) {
            root = rsNode;
        } else if (node == node.getParent().getLeftSon()) {
            node.getParent().setLeftSon(rsNode);
        } else {
            node.getParent().setRightSon(rsNode);
        }
        rsNode.setLeftSon(node);
        node.setParent(rsNode);
    }

    private void rightRotation(Node node){
        Node lsNode = node.getLeftSon();
        node.setLeftSon(lsNode.getRightSon());

        if(lsNode.getRightSon() != leaf){
            lsNode.getRightSon().setParent(node);
        }

        lsNode.setParent(node.getParent());
        if(node.getParent() == null){
            root = lsNode;
        }else if(node.getParent().getLeftSon() == node){
            node.getParent().setLeftSon(lsNode);
        }else{
            node.getParent().setRightSon(lsNode);
        }
        lsNode.setRightSon(node);
        node.setParent(lsNode);
    }

    private Node findNode(int key) {
        Node pointer = root;
        if (pointer == null) {
            return null;
        } else {
            while (pointer != leaf) {
                if (pointer.getKey().compareTo(key) == 0) {
                    return pointer;
                } else if (pointer.getKey().compareTo(key) < 0) {
                    pointer = pointer.getRightSon();
                } else {
                    pointer = pointer.getLeftSon();
                }
            }
            return null;
        }
    }

    public void testRRotation(int key) {
        Node node = findNode(key);
        this.rightRotation(node);
    }

    private void showTree(ArrayList<ArrayList<Node>> list, Node n, int lvl) {
        list.get(lvl).add(n);
        if (n.getLeftSon() != null) {
            showTree(list, n.getLeftSon(), lvl + 1);
        }
        if (n.getRightSon() != null) {
            showTree(list, n.getRightSon(), lvl + 1);
        }
    }

    public void showTree() {
        ArrayList<ArrayList<Node>> n = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            n.add(new ArrayList<Node>());
        }
        showTree(n, root, 0);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < n.get(i).size(); j++) {
                System.out.print(n.get(i).get(j).getValue() + "("+(n.get(i).get(j).isRed()?"red)":"black)"));
            }
            System.out.println();
        }
    }

    @Override
    public void setValue(Comparable key, Object value) {

    }

    @Override
    public Object getValue(Comparable key) {
        return null;
    }
}
