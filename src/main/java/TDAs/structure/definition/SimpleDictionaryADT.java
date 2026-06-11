package TDAs.structure.definition;

// Esta interfaz representa el TDA Diccionario Simple.
public interface SimpleDictionaryADT<T> {

    /**
     * Descripcion: Agrega un elemento a la estructura. Precondición: La estructura no debe sobrepasar la capacidad. Si
     * el valor existe pisa el contenido
     */
    void add(T key, T value);

    /**
     * Descripcion: Elimina el elemento de la estructura, si no existe no hace nada. Precondición: La estructura debe
     * tener elementos.
     */
    void remove(T key);

    /**
     * Descripcion: Devuelve el valor de una clave. Precondición: La estructura debe tener elementos y la clave debe
     * existir.
     */
    T get(T key);

    /**
     * Descripcion: Retorna el conjunto de claves. Precondición: No tiene.
     */
    SetADT getKeys();

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores. Precondición: No tiene.
     */
    boolean isEmpty();
}
