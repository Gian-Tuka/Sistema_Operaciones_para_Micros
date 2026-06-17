package TDAs.structure.definition;

// Esta interfaz representa el TDA Diccionario Simple.
public interface SimpleDictionaryADT<K, V> {

    /**
     * Descripcion: Agrega un elemento a la estructura. Precondición: La estructura no debe sobrepasar la capacidad. Si
     * el valor existe pisa el contenido
     */
    void add(K key, V value);

    /**
     * Descripcion: Elimina el elemento de la estructura, si no existe no hace nada. Precondición: La estructura debe
     * tener elementos.
     */
    void remove(K key);

    /**
     * Descripcion: Devuelve el valor de una clave. Precondición: La estructura debe tener elementos y la clave debe
     * existir.
     */
    V get(K key);

    /**
     * Descripcion: Retorna el conjunto de claves. Precondición: No tiene.
     */
    SetADT<K> getKeys();

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores. Precondición: No tiene.
     */
    boolean isEmpty();
}
