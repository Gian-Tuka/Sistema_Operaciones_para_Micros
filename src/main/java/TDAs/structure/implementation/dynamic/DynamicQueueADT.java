package TDAs.structure.implementation.dynamic;


import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.QueueADT;
import TDAs.structure.implementation.node.Nodo;

// Esta clase representa la implementacion dinamica del TDA Cola.
public class DynamicQueueADT<T> implements QueueADT<T> {

    private Nodo<T> front = null;
    private Nodo<T> back = null;
    private int size = 0;

    @Override
    public T getElement() {
        validateNotEmpty();

        return this.front.getValue();
    }

    @Override
    public void add(T value) {
        Nodo<T> node = new Nodo(value);

        if (this.isEmpty()) {
            this.front = node;
            this.back = node;
            this.size++;
        }else {

            this.back.setNext(node);
            this.back = node;
            this.size++;
        }
    }

    @Override
    public void remove() {
        validateNotEmpty();

        //Esto es que solo tengo un elemento
        if (this.front == this.back){
            this.front = null;
            this.back = null;
            this.size--;
        }else{
            this.front = this.front.getNext();
            this.size--;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.front == null;
    }


    private void validateNotEmpty() {
        if (isEmpty()) {
            throw new EmptyADTException("Queue vacía.");
        }
    }
}