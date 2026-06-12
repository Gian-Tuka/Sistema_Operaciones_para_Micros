package TDAs.structure.implementation.fixed;

import TDAs.exceptions.EmptyADTException;
import TDAs.exceptions.FullADTException;
import TDAs.structure.definition.StackADT;

// Esta clase representa la implementacion estatica del TDA Pila.
public class StaticStackADT<T> implements StackADT<T> {

    private static final int CAPACITY = 100;
    private Object[] data;
    private int top;


    public StaticStackADT(){
        this.data = new Object[CAPACITY];
        this.top = -1;
    }
    /**
     * Descripcion: Devuelve el último elemento de la estructura.
     * Precondición: La estructura debe tener elementos.
     */
    @Override
    public T getElement() {
        validateNotEmpty();
        return (T) this.data[top];
    }


    /**
     * Descripcion: Agrega un elemento al final de la estructura.
     * Precondición: La estructura no debe sobrepasar la
     * capacidad.
     */
    @Override
    public void add(T value) {
        validateCapacity();
        this.top++;
        this.data[top] = value;
    }

    /**
     * Descripcion: Elimina el último elemento que existe.
     * Precondición: La estructura debe tener elementos.
     */
    @Override
    public void remove() {
        validateNotEmpty();

        this.top--;
    }


    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores.
     * Precondición: No tiene.
     */
    @Override
    public boolean isEmpty() {
        return this.top == -1;
    }


    private void validateCapacity() {
        if (this.top + 1 == CAPACITY) { throw new FullADTException("Máximo límite alcanzado en el stack.");
        }
    }

    private void validateNotEmpty() {
        if (this.isEmpty()) {throw new EmptyADTException("El stack se encuentra vacío.");
        }
    }
}
