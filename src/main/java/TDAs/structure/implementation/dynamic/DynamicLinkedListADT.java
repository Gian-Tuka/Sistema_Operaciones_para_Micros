package TDAs.structure.implementation.dynamic;

import TDAs.exceptions.EmptyADTException;
import TDAs.exceptions.FullADTException;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.implementation.node.Nodo;

public class DynamicLinkedListADT<T> implements LinkedListADT<T> {

    private Nodo head = null;
    private Nodo lastNode;
    private int size = 0;


    @Override
    public void add(T value) {

        Nodo node = new Nodo(value); //ya apunta a null

        if (this.head == null){
            this.head = node;
        } else{
            this.lastNode.setNext(node);
        }

        this.lastNode = node;
        this.size++;
    }

    @Override
    public void insert(int index, T value) {
        validateIndexForInsert(index);


        if (index == this.size){
            this.add(value);
            return;
        }

        Nodo node = new Nodo(value);

        if (index == 0){
            node.setNext(this.head);
            this.head = node;
            if (this.size == 0){
                this.lastNode = node;
            }
            this.size++;
            return;
        }

        Nodo currentNode = this.head;
        Nodo prevNode = this.head;
        int currentIndex = 0;


        while (index != currentIndex){
            prevNode = currentNode;
            currentNode = currentNode.getNext();
            currentIndex++;
        }
        prevNode.setNext(node);
        node.setNext(currentNode);
        this.size++;
    }

    @Override
    public void remove(int index) {
        validateIndex(index);

        if (index == 0){
            this.head = this.head.getNext();
            if (this.size == 1){
                this.lastNode = null;
            }
            this.size--;
            return;
        }

        Nodo actualNode = this.head;
        Nodo prevNode = null;
        int actualIndex = 0;

        while (actualIndex != index){
            prevNode = actualNode;
            actualNode = actualNode.getNext();
            actualIndex++;
        }
        prevNode.setNext(actualNode.getNext());

        if (index == this.size-1){
            this.lastNode = prevNode;
        }


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

    private void validateIndex(int index) {
        if (this.isEmpty()) { throw new EmptyADTException("La lista se encuentra vacía."); }

        if (index < 0 || index >= this.size) { throw new FullADTException("El índice dado no existe en la lista."); }
    }
    private void validateIndexForInsert(int index) {
        if (index < 0 || index > this.size) { throw new FullADTException("El índice dado no existe en la lista."); }
    }
}
