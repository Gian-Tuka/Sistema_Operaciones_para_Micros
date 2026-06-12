package TDAs.structure.implementation.node;

public class NodeGraph<T> {

    private T value;
    private int weight;
    private NodeGraph<T> pointer;
    private NodeGraph<T> adjacent;

    public NodeGraph(T value) {
        this(value, 0);
    }

    public NodeGraph(T value, int weight) {
        this.value = value;
        this.weight = weight;
        this.pointer = null;
        this.adjacent = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public NodeGraph<T> getPointer() {
        return pointer;
    }

    public void setPointer(NodeGraph<T> pointer) {
        this.pointer = pointer;
    }

    public NodeGraph<T> getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(NodeGraph<T> adjacent) {
        this.adjacent = adjacent;
    }
}
