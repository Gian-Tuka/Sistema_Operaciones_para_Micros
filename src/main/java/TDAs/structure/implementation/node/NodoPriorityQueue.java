package TDAs.structure.implementation.node;

public class NodoPriorityQueue {

    public int value;
    public int priority;
    public NodoPriorityQueue next;

    public NodoPriorityQueue(int value, int priority) {
        this.value = value;
        this.priority = priority;
        this.next = null;
    }
}
