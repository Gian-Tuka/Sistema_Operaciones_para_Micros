package TDAs.structure.definition;

import TDAs.exceptions.EmptyADTException;

// Esta interfaz representa el TDA Conjunto.
public interface SetADT<T> {

    /**
     * Descripcion: Devuelve true en caso de que el valor exista en el conjunto· Precondición: No tiene
     */
    boolean exist(T value);

    /**
     * Descripcion: Devuelve un elemento al azar de la estructura. Precondición: La estructura debe tener elementos.
     */
    T choose() throws EmptyADTException;

    /**
     * Descripcion: Agrega un elemento a la estructura. Precondición: La estructura no debe sobrepasar la capacidad. No
     * puede haber repetidos
     */
    void add(T value);

    /**
     * Descripcion: Elimina el elemento de la estructura, si no existe no hace nada. Precondición: La estructura debe
     * tener elementos.
     */
    void remove(T element);

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores. Precondición: No tiene.
     */
    boolean isEmpty();
}
