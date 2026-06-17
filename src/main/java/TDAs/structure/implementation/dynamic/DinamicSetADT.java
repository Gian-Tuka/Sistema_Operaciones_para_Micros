package TDAs.structure.implementation.dynamic;
import TDAs.structure.definition.SetADT;
import TDAs.structure.implementation.node.Nodo;
import TDAs.exceptions.EmptyADTException;

public class DinamicSetADT<T> implements SetADT<T> {

    private Nodo<T> head;
    private Nodo<T> pointer;
    private int size = 0;

    public DinamicSetADT() {
        this.head = null;
        this.pointer = null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean exist(T value) {
        if (isEmpty()) {
            return false;
        }
        pointer = head;
        for (int i = 0; i < size; i++) {
            if (pointer.getValue() == value) {
                return true;
            } else {
                pointer = pointer.getNext();
            }
        }
        return false;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Nodo(value);
            size++;
        } else if (exist(value)) {
            throw new TDAs.exceptions.GenericADTException("El valor '" + value + "' ya existe");
        } else {
            pointer = head;
            while (pointer.getNext() != null) {
                pointer = pointer.getNext();
            }
            pointer.setNext(new Nodo(value));
            size++;
        }
    }

    @Override
    public void remove(T value) {
        if (isEmpty()) {
            throw new EmptyADTException("El conjunto esta vacio");
        } else if (!exist(value)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("El valor '" + value + "' no existe");
        } else {
            pointer = head;
            if (pointer.getValue() == value) {
                head = null;
                size--;
            } else {
                while (pointer.getNext().getValue() != value) {
                    pointer = pointer.getNext();
                }
                pointer.setNext(pointer.getNext().getNext());
                size--;
            }
        }
    }

    @Override
    public T choose() throws EmptyADTException {
        if (isEmpty()) {
            throw new EmptyADTException("El conjunto esta vacio");
        }
        int choice = (int)(Math.random() * size);
        pointer = head;
        for (int i = 0; i < choice; i++) {
            pointer = pointer.getNext();
        }
        return pointer.getValue();
    }

}
