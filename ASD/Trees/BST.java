package Structures;

public class BST {
    private Node root;

    public BST( int value ){
        root = new Node( value );
    }

    public BST(){
        root = null;
    }

    private void destroyBST( Node n ){
        if( n.getLeftSon() != null )destroyBST( n.getLeftSon() );
        if( n.getRightSon() != null )destroyBST( n.getRightSon() );
        n.setParent(null);
        return;
    }

    public void destroyBST(){
        destroyBST(root);
        root = null;
    }

    private Node getMinSon( Node n ){
        if(n == null)return null;
        while( n.getLeftSon() != null ) {
            n = n.getLeftSon();
        }
        return n;
    }

    private Node getMaxSon( Node n ){
        if(n == null)return null;
        while( n.getRightSon() != null ){
            n = n.getRightSon();
        }
        return n;
    }

    private Node findNode(int value){
        Node st = root;
        while(st != null){
            if(st.getValue() == value) return st;
            if(st.getValue() < value){
                st = st.getRightSon();
            }
            else{
                st = st.getLeftSon();
            }
        }
        return st;
    }

    private Node removeNode( Node n ){
        if( n.getLeftSon() != null && n.getRightSon() != null ){    //has 2 children
            Node tmp = getMinSon( n.getRightSon() );                //minimal child in right subtree
            n.getLeftSon().setParent(tmp);                          //connecting left son with new parent
            tmp.setLeftSon( n.getLeftSon() );

            if( n != root ){
                if( n.getParent().getLeftSon() == n )n.getParent().setLeftSon(tmp);
                else n.getParent().setRightSon(tmp);
            }
            else{
                root = tmp;
                tmp.getParent().setLeftSon(null);
            }
            tmp.setParent( n.getParent() );                         //connecting new node with its new parent

            if( tmp.getRightSon() != null ){
                Node maxRight = getMaxSon( tmp.getRightSon() );
                maxRight.setRightSon( n.getRightSon() );
                n.getRightSon().setParent(maxRight);
            }
            else {
                if (tmp != n.getRightSon()) {
                    tmp.setRightSon(n.getRightSon());
                    n.getRightSon().setParent(tmp);
                } else {
                    tmp.setRightSon(null);
                }
            }
        }
        else if( n.getLeftSon() != null || n.getRightSon() != null ){   //has only one child
            Node father = n.getParent();
            if( n == root ){
                root = n.getLeftSon()!=null?n.getLeftSon():n.getRightSon();
            }
            else{
                if( n.getLeftSon() != null ){                               //has only left son
                    if(father.getLeftSon() == n)father.setLeftSon( n.getLeftSon() );
                    else father.setRightSon( n.getLeftSon() );
                    n.getLeftSon().setParent(father);
                }
                else if( n.getRightSon() != null ){                         //has only right son
                    if(father.getLeftSon() == n)father.setLeftSon( n.getRightSon() );
                    else father.setRightSon( n.getRightSon() );
                    n.getRightSon().setParent(father);
                }
            }
        }
        else if( n.getLeftSon() == null && n.getRightSon() == null ){
            if( n == root ){
                root = null;
            }
            else{
                Node father = n.getParent();
                if( n == father.getLeftSon() )father.setLeftSon(null);
                else father.setRightSon(null);
            }
        }
        return n;
    }

    public void removeNode( int value ){
        Node n = findNode(value);
        removeNode(n);
    }

    public void addNode( int value ){
        Node n = new Node(value);
        Node tmp = root;

        Node prevNode = null;

        while(tmp != null){
            prevNode = tmp;
            if( tmp.getValue() < n.getValue() ) tmp = tmp.getRightSon();
            else tmp = tmp.getLeftSon();
        }

        try{
            if( prevNode.getValue() < n.getValue() ){ prevNode.setRightSon(n); }
            else{ prevNode.setLeftSon(n); }
        }catch(NullPointerException e){
            root = n;
        }
        n.setParent(prevNode);
    }

    private void showTree(Node node){
        if(root == null){
            System.out.println("Empty tree");
            return;
        }
        if(node == null)return;
        try{
            System.out.print("leftSon:" + ( node.getLeftSon() ).getValue() + " | ");
        }catch(NullPointerException e){
            System.out.print("No left son |");
        }

        System.out.print(" parent:" + node.getValue() + " | ");

        try{
            System.out.println("rightSon:" + ( node.getRightSon() ).getValue());
        }catch(NullPointerException e){
            System.out.println("No right son");
        }

        try{
            showTree( node.getLeftSon() );
            showTree( node.getRightSon() );
        }catch(NullPointerException e){
        }
    }

    public void showTree(){
        showTree(root);
    }
}
