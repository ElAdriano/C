package Structures;

public class Node {
        private double value;
        private Node parent;
        private Node leftSon;
        private Node rightSon;

        Node( int v ){
            value  = v;
            parent = null;
            leftSon = null;
            rightSon = null;
        }

        public Node getParent(){ return parent; }

        public Node getLeftSon(){ return leftSon; }

        public Node getRightSon(){
            return rightSon;
        }

        public double getValue(){ return value; }

        public void setParent(Node node){
            parent = node;
        }

        public void setLeftSon(Node node){
            leftSon = node;
        }

        public void setRightSon(Node node){
            rightSon = node;
        }
}
