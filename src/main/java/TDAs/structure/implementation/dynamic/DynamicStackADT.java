package TDAs.structure.implementation.dynamic;

import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.StackADT;
import TDAs.structure.implementation.node.Nodo;

// Esta clase representa la implementacion dinamica del TDA Pila.
public class DynamicStackADT<T> implements StackADT<T> {


    private Nodo<T> top = null;
    private int size = 0;

    /**
     * Descripcion: Devuelve el último elemento de la estructura.
     * Precondición: La estructura debe tener elementos.
     */
    @Override
    public T getElement() {
        validateNotEmpty();
            return this.top.getValue();
    }


    /**
     * Descripcion: Agrega un elemento al final de la estructura.
     * Precondición: La estructura no debe sobrepasar la
     * capacidad.
     */
    @Override
    public void add(T value) {

        Nodo<T> node = new Nodo(value);

        node.setNext(this.top);
        this.top = node;
        this.size++;
    }


    /**
     * Descripcion: Elimina el último elemento que existe.
     * Precondición: La estructura debe tener elementos.
     */
    @Override
    public void remove() {
        validateNotEmpty();

        this.top = this.top.getNext();
        this.size--;
    }



    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores.
     * Precondición: No tiene.
     */
    @Override
    public boolean isEmpty() {
        return this.top == null;
    }

    private void validateNotEmpty() {
        if (isEmpty()) { throw new EmptyADTException("El stack se encuentra vacío.");
        }
    }
}
