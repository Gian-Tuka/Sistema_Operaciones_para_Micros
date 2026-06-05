package TDAs.structure.implementation.fixed;

import TDAs.exceptions.EmptyADTException;
import TDAs.exceptions.FullADTException;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.implementation.node.Nodo;

public class StaticLinkedListADT<T> implements LinkedListADT<T> {

    private static final int CAPACIDAD = 100;
    private int size;
    private Nodo<T> head;
    private Nodo<T> lastNode;


    @Override
    public void add(T value) {
        validateCapacity();
        Nodo node = new Nodo(value);

        if (this.head == null){
              this.head = node;
              this.lastNode = node;
        }
        else { //Si no está vacía la lista
              this.lastNode.setNext(node);
              this.lastNode = node;
        }
        this.size++;
    }

    @Override
    public void insert(int index, T value) {
        validateCapacity();
        validateIndexForInsert(index);

        Nodo<T> node = new Nodo<>(value);

        if (index == 0) {
            node.setNext(this.head);
            this.head = node;
            this.size++;
            return;
        }

        if (index == this.size){
            add(value);
            return;
        }

        Nodo actualNode = this.head;
        Nodo prevNode = this.head;
        int actualIndex = 0;

        while (actualIndex != index){
            prevNode = actualNode;
            actualNode = actualNode.getNext();
            actualIndex++;
        }

        node.setNext(actualNode);
        prevNode.setNext(node);
        this.size++;
    }


    @Override
    public void remove(int index) {
        validateIndex(index);

        Nodo actualNode = this.head;
        Nodo prevNode = this.head;
        int actualIndex = 0;

        if (index == 0){
            this.head = actualNode.getNext();
            this.size--;
            return;
        }

        while (actualIndex != index){
            prevNode = actualNode;
            actualNode = actualNode.getNext();
            actualIndex++;
        }

        prevNode.setNext(actualNode.getNext());
        this.size--;
    }

    @Override
    public T get(int index) {
        validateIndex(index);

        Nodo<T> actualNode = this.head;
        int actualIndex = 0;

        while(actualIndex != index){
            actualNode = actualNode.getNext();
            actualIndex++;
        }
        return actualNode.getValue();
    }

    @Override
    public int size() {

        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }


    private void validateCapacity() {
        if (this.size >= CAPACIDAD) {
            throw new FullADTException("Máximo límite alcanzado.");  // no debería crecer más
        }
    }

    private void validateIndex(int index) {
        if (this.isEmpty()) { throw new EmptyADTException("La lista se encuentra vacía."); }

        if (index < 0 || index >= this.size) { throw new FullADTException("El índice dado no existe en la lista."); }
    }

    private void validateIndexForInsert(int index){
        if (index < 0 || index > this.size) {
            throw new FullADTException("El índice dado no existe en la lista.");
        }
    }
}