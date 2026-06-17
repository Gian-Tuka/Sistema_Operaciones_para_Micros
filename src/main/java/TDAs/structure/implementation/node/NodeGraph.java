package TDAs.structure.implementation.node;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;

public class NodeGraph<T> {

    private T value;
    private NodeGraph<T> pointer;
    private LinkedListADT<Edge<T>> outgoingEdges;
    private LinkedListADT<Edge<T>> incomingEdges;

    public NodeGraph(T value) {
        this.value = value;
        this.pointer = null;
        this.outgoingEdges = new DynamicLinkedListADT<>();
        this.incomingEdges = new DynamicLinkedListADT<>();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public NodeGraph<T> getPointer() {
        return pointer;
    }

    public void setPointer(NodeGraph<T> pointer) {
        this.pointer = pointer;
    }

    public LinkedListADT<Edge<T>> getOutgoingEdges() {
        return outgoingEdges;
    }

    public LinkedListADT<Edge<T>> getIncomingEdges() {
        return incomingEdges;
    }
}
