package map.rbt;

public class Node<K extends Comparable<K>,V> {
    private Node leftson;
    private Node rightson;
    private Node parent;
    private boolean isRed;

    private V value;
    private K key;

    public Node(K key, V value, Node parent, Node leftson, Node rightson, boolean isRed){
        this.isRed = isRed;
        this.parent = parent;
        this.leftson = leftson;
        this.rightson = rightson;

        this.value = value;
        this.key = key;
    }


    public Node getRightSon() {
        return rightson;
    }

    public void setRightSon(Node rightson) {
        this.rightson = rightson;
    }

    public Node getLeftSon() {
        return leftson;
    }

    public void setLeftSon(Node leftson) {
        this.leftson = leftson;
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
