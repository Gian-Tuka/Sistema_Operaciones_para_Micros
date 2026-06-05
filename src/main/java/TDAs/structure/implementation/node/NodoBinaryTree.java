package TDAs.structure.implementation.node;

public class NodoBinaryTree {

    private int value;
    private NodoBinaryTree left;
    private NodoBinaryTree right;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public NodoBinaryTree getLeft() {
        return left;
    }

    public void setLeft(NodoBinaryTree left) {
        this.left = left;
    }

    public NodoBinaryTree getRight() {
        return right;
    }

    public void setRight(NodoBinaryTree right) {
        this.right = right;
    }

    public NodoBinaryTree(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
