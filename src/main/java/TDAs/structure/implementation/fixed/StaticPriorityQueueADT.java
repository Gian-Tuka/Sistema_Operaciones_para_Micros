package TDAs.structure.implementation.fixed;

import TDAs.exceptions.EmptyADTException;
import TDAs.exceptions.FullADTException;
import TDAs.structure.definition.PriorityQueueADT;

public class StaticPriorityQueueADT implements PriorityQueueADT {

    private static final int CAPACITY = 20;
    private int[] data;
    private int[] priority;
    private int size;

    public StaticPriorityQueueADT() {
        this.data = new int[CAPACITY];
        this.priority = new int[CAPACITY];
        this.size = 0;
    }


    @Override
    public int getElement() {
        validateNotEmpty();
        return this.data[0];

    }

    @Override
    public int getPriority() {
        validateNotEmpty();
        return this.priority[0];
    }

    @Override
    public void add(int value, int priority) {
        validateCapacity();

        // primera posición donde la prioridad es menor a la nueva
        int index = 0;
        while (index < this.size && this.priority[index] >= priority) {
            index++;
        }

        // Desplazo una posición a la derecha desde index
        for (int i = this.size; i > index; i--) {
            this.data[i] = this.data[i - 1];
            this.priority[i] = this.priority[i - 1];
        }

        // insert en esa posicion
        this.data[index] = value;
        this.priority[index] = priority;
        this.size++;
    }

    @Override
    public void remove() {
        validateNotEmpty();

        for (int i = 0; i < this.size - 1; i++) {
            this.data[i] = this.data[i + 1];
            this.priority[i] = this.priority[i + 1];
        }

        this.size--;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void validateNotEmpty() {
        if (this.isEmpty()) {
            throw new EmptyADTException("La cola de prioridad está vacía.");
        }
    }

    private void validateCapacity() {
        if (this.size >= CAPACITY) {
            throw new FullADTException("Se sobrepasó el límite de la cola.");
        }
    }
}
