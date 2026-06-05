package TDAs.structure.implementation.dynamic;

import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.StackADT;
import TDAs.structure.implementation.node.Nodo;

// Esta clase representa la implementacion dinamica del TDA Pila.
public class DynamicStackADT implements StackADT {


    private Nodo top = null;
    private int size = 0;

    /**
     * Descripcion: Devuelve el último elemento de la estructura.
     * Precondición: La estructura debe tener elementos.
     */
    @Override
    public int getElement() {
        validateNotEmpty();
            return this.top.getValue();
    }


    /**
     * Descripcion: Agrega un elemento al final de la estructura.
     * Precondición: La estructura no debe sobrepasar la
     * capacidad.
     */
    @Override
    public void add(int value) {

        Nodo node = new Nodo(value);

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
