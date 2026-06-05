package TDAs.structure.definition;

// Esta interfaz representa el TDA Cola.
public interface QueueADT<T> {

    /**
     * Descripcion: Devuelve el primer elemento de la estructura. Precondición: La estructura debe tener elementos.
     */
    T getElement();

    /**
     * Descripcion: Agrega un elemento al final de la estructura. Precondición: La estructura no debe sobrepasar la
     * capacidad.
     */
    void add(T value);

    /**
     * Descripcion: Elimina el primer elemento que existe. Precondición: La estructura debe tener elementos.
     */
    void remove();

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores. Precondición: No tiene.
     */
    boolean isEmpty();
}
