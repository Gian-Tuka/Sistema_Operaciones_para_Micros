package TDAs.structure.implementation.node;

public class NodoPriorityQueue<T> {

    public T value;
    public int priority;
    public NodoPriorityQueue<T> next;

    public NodoPriorityQueue(T value, int priority) {
        this.value = value;
        this.priority = priority;
        this.next = null;
    }
}
