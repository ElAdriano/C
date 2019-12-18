package map.rbt;

import java.util.ArrayList;

public class RedBlackTree<K extends Comparable<K>, V> implements MapInterface {

    private Node root;
    private final Node leaf;

    public RedBlackTree() {
        this.root = null;
        this.leaf = new Node(null, null, null, null, null, false);
    }

    @Override
    public void setValue(Comparable key, Object value) {
        Node node = findNode(key);
        if (node == null) {
            add((K) key, (V) value);
        } else {
            node.setValue(value);
        }
    }

    @Override
    public Object getValue(Comparable key) {
        Node node = findNode(key);
        if (node == null) {
            throw new NoKeyInTreeException(key, "No such key in map"); // exception when there is no key in red-black tree
        } else {
            return node.getValue();
        }
    }

    private void add(K key, V value) {
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
        while (node != null) {
            int incorrectCase = checkTreeCorrectionInNode(node);

            while (incorrectCase != 0) {
                incorrectCase = checkTreeCorrectionInNode(node);
                switch (incorrectCase) {
                    case 1:
                        node.setRed(true);
                        if (node.getParent() == null) {
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
                        break;
                    case 4:
                        rightRotation(node.getParent());
                        node.setRed(false);
                        node.getRightSon().setRed(true);
                        node = node.getRightSon();
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
        if (node.getLeftSon().isRed() && node.getRightSon().isRed()) {
            return 1;
        } else if (!node.isRed() && node.getRightSon().isRed()) {
            return 2;
        } else if (node.isRed() && node.getRightSon().isRed()) {
            return 3;
        } else if (node.isRed() && node.getLeftSon().isRed()) {
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

    private void getTreeStructure(ArrayList<ArrayList<Node>> list, Node n, int lvl) {
        list.get(lvl).add(n);
        if (n.getLeftSon() != null) {
            getTreeStructure(list, n.getLeftSon(), lvl + 1);
        }
        if (n.getRightSon() != null) {
            getTreeStructure(list, n.getRightSon(), lvl + 1);
        }
    }

    private int getTreeHeight(Node n) {
        if (n == leaf) {
            return 1;
        }
        if (n.getLeftSon() != leaf && n.getRightSon() != leaf) {
            return Math.max(getTreeHeight(n.getLeftSon()) + 1, getTreeHeight(n.getRightSon()) + 1);
        } else if (n.getLeftSon() != leaf) {
            return getTreeHeight(n.getLeftSon()) + 1;
        } else {
            return getTreeHeight(n.getRightSon()) + 1;
        }
    }

    public void showTree() {
        ArrayList<ArrayList<Node>> tree = new ArrayList<>();
        int h = getTreeHeight(root);

        for (int i = 0; i < h; i++) {
            tree.add(new ArrayList<>());
        }

        getTreeStructure(tree, root, 0);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < tree.get(i).size(); j++) {
                if (tree.get(i).get(j).getKey() != null) {
                    System.out.print(tree.get(i).get(j).getKey() + "   (" + (tree.get(i).get(j).isRed() ? " red )    " : "black)    "));
                } else {
                    System.out.print(tree.get(i).get(j).getKey() + "(" + (tree.get(i).get(j).isRed() ? " red )      " : "black)    "));
                }
            }
            System.out.println();
        }
    }

    public Node findNode(Comparable key) {
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

}
