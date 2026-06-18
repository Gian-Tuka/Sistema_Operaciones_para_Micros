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
        Nodo<T> actual = head;
        while (actual != null) {
            T val = actual.getValue();
            if (val == null) {
                if (value == null) return true;
            } else if (val.equals(value)) {
                return true;
            }
            actual = actual.getNext();
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
        }

        if ((head.getValue() == null && value == null) || (head.getValue() != null && head.getValue().equals(value))) {
            head = head.getNext(); // Desplazamos la cabeza al siguiente, NO a null
            size--;
            return;
        }

        Nodo<T> actual = head;
        while (actual.getNext() != null) {
            T nextVal = actual.getNext().getValue();
            if ((nextVal == null && value == null) || (nextVal != null && nextVal.equals(value))) {
                actual.setNext(actual.getNext().getNext()); // Saltamos el nodo eliminado
                size--;
                return;
            }
            actual = actual.getNext();
        }

        throw new TDAs.exceptions.ElementNotFoundADTException("El valor '" + value + "' no existe");
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
