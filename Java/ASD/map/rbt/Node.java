package map.rbt;

public class Node<K extends Comparable<K>, V> {

    private Node leftSon;
    private Node rightSon;
    private Node parent;
    private boolean isRed;

    private V value;
    private K key;

    public Node(K key, V value, Node parent, Node leftSon, Node rightSon, boolean isRed) {
        this.isRed = isRed;
        this.parent = parent;
        this.leftSon = leftSon;
        this.rightSon = rightSon;

        this.value = value;
        this.key = key;
    }

    public Node getRightSon() {
        return rightSon;
    }

    public void setRightSon(Node rightson) {
        this.rightSon = rightson;
    }

    public Node getLeftSon() {
        return leftSon;
    }

    public void setLeftSon(Node leftson) {
        this.leftSon = leftson;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

}
