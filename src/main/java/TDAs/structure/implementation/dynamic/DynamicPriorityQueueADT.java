package TDAs.structure.implementation.dynamic;


import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.PriorityQueueADT;
import TDAs.structure.implementation.node.NodoPriorityQueue;

// Esta clase representa la implementacion dinamica del TDA Cola con Prioridad.
public class DynamicPriorityQueueADT<T> implements PriorityQueueADT<T>{


    private NodoPriorityQueue<T> front;


    @Override
    public T getElement() {
        validateNotEmpty();
        return this.front.value;
    }

    @Override
    public int getPriority() {
        validateNotEmpty();
        return this.front.priority;
    }

    @Override
    public void add(T value, int priority) {
        NodoPriorityQueue<T> newNodo = new NodoPriorityQueue(value, priority);

        if (this.front == null || priority > this.front.priority) {
            newNodo.next = this.front;
            this.front = newNodo;
            return;
        }

        NodoPriorityQueue<T> current = this.front;
        while (current.next != null && current.next.priority >= priority) {
            current = current.next;
        }

        newNodo.next = current.next;
        current.next = newNodo;
    }

    @Override
    public void remove() {
        validateNotEmpty();
        this.front = this.front.next;
    }

    @Override
    public boolean isEmpty() {
        return this.front == null;
    }

    private void validateNotEmpty() {
        if (this.isEmpty()) {
            throw new EmptyADTException("La cola de prioridad está vacía.");
        }
    }
}
