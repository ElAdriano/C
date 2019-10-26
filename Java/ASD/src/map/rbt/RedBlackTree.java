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
        /*if((Integer)key == 121){
            reorganize(node,true);
        }else{
            reorganize(node, false);
        }*/
        reorganize(node,true);
    }

    private void reorganize(Node node, boolean cansee) {
        while(node != null){
            int incorrectCase = checkTreeCorrectionInNode(node);

            while(incorrectCase != 0){
                //if(cansee)showTree();
                incorrectCase = checkTreeCorrectionInNode(node);
                /*System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println(node.getValue());
                System.out.println(node.isRed());
                System.out.println(incorrectCase);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");*/
                switch(incorrectCase){
                    case 1:
                        node.setRed(true);
                        if(node.getParent() == null){
                            node.setRed(false);
                        }
                        node.getLeftSon().setRed(false);
                        node.getRightSon().setRed(false);
                        break;
                    case 2:
                        leftRotation(node);
                        node.setRed(true);
                        node.getParent().setRed(false);
                        break;
                    case 3:
                        leftRotation(node);

                        /*node = node.getParent();
                        System.out.println(">>>>>>>>>>> After left rotation");
                        showTree();
                        rightRotation(node.getParent());
                        node.setRed(true);
                        node.getLeftSon().setRed(false);*/
                        break;
                    case 4:
                        rightRotation(node.getParent());
                        node.setRed(false);
                        node.getRightSon().setRed(true);
                        break;
                }
            }
            node = node.getParent();
        }
    }

    private int checkTreeCorrectionInNode(Node node) {
        if (node == null) {
            node = root;
        }
        if(node.getLeftSon().isRed() && node.getRightSon().isRed()){    // both sons are red
            return 1;
        }else if(!node.isRed() && node.getRightSon().isRed()){          // I am black and have red right son
            return 2;
        }else if(node.isRed() && node.getRightSon().isRed()){           // I'm red and have red right son
            return 3;
        }else if(node.isRed() && node.getLeftSon().isRed()){            // I'm red and have red left son
            return 4;
        }
        return 0;
    }

    private void leftRotation(Node node) {
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

    private void rightRotation(Node node) {
        Node lsNode = node.getLeftSon();
        node.setLeftSon(lsNode.getRightSon());

        if (lsNode.getRightSon() != leaf) {
            lsNode.getRightSon().setParent(node);
        }

        lsNode.setParent(node.getParent());
        if (node.getParent() == null) {
            root = lsNode;
        } else if (node.getParent().getLeftSon() == node) {
            node.getParent().setLeftSon(lsNode);
        } else {
            node.getParent().setRightSon(lsNode);
        }
        lsNode.setRightSon(node);
        node.setParent(lsNode);
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
                if (n.get(i).get(j).getValue() != null) {
                    System.out.print(n.get(i).get(j).getValue() + "   (" + (n.get(i).get(j).isRed() ? " red )    " : "black)    "));
                } else {
                    System.out.print(n.get(i).get(j).getValue() + "(" + (n.get(i).get(j).isRed() ? " red )      " : "black)    "));
                }
            }
            System.out.println();
        }
    }

    private Node findNode(Comparable key) {
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

    @Override
    public void setValue(Comparable key, Object value) throws NoSuchKeyException {
        Node node = findNode(key);
        if(node == null){
            throw new NoSuchKeyException(key, "No such key in map.");
        }else{
            node.setValue(value);
        }
    }

    @Override
    public Object getValue(Comparable key) throws NoSuchKeyException {
        Node node = findNode(key);
        if(node == null){
            throw new NoSuchKeyException(key, "No such key in map");
        }else{
            return node.getValue();
        }
    }
}
