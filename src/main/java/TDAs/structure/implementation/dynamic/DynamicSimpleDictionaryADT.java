package TDAs.structure.implementation.dynamic;

import TDAs.structure.definition.SetADT;
import TDAs.structure.definition.SimpleDictionaryADT;
import TDAs.structure.implementation.node.NodoDiccionario;

public class DynamicSimpleDictionaryADT<K, V> implements SimpleDictionaryADT<K, V> {

    private NodoDiccionario<K, V> head;

    @Override
    public void add(K key, V value) {
        if (key == null) {
            throw new TDAs.exceptions.GenericADTException("La clave no puede ser nula");
        }
        NodoDiccionario<K, V> actual = this.head;

        while (actual != null) {
            if (actual.key.equals(key)) {
                actual.value = value;
                return;
            }
            actual = actual.next;
        }

        NodoDiccionario<K, V> nuevoNodo = new NodoDiccionario<>(key, value);
        nuevoNodo.next = this.head;
        this.head = nuevoNodo;
    }

    @Override
    public void remove(K key) {
        if (isEmpty()){
            throw new TDAs.exceptions.EmptyADTException("El diccionario está vacio");
        }

        if (this.head.key.equals(key)) {
            this.head = this.head.next;
            return;
        }

        NodoDiccionario<K, V> actual = this.head;
        while (actual.next != null) {
            if (actual.next.key.equals(key)) {
                actual.next = actual.next.next;
                return;
            }
            actual = actual.next;
        }
        throw new TDAs.exceptions.ElementNotFoundADTException("La clave no existe en el diccionario");
    }

    /**
     * Descripcion: Devuelve el valor de una clave.
     * Precondición: La estructura debe tener elementos y la clave debe existir.
     */
    @Override
    public V get(K key) {
        if (isEmpty()){
            throw new TDAs.exceptions.EmptyADTException("El diccionario está vacio");
        }
        NodoDiccionario<K, V> actual = this.head;

        while (actual != null) {
            if (actual.key.equals(key)) {
                return actual.value;
            }
            actual = actual.next;
        }
        throw new TDAs.exceptions.ElementNotFoundADTException("La clave no existe en el diccionario");
    }

    @Override
    public SetADT<K> getKeys() {
        SetADT<K> setKeys = new DinamicSetADT<>();
        NodoDiccionario<K, V> actual = this.head;

        while(actual != null) {
            setKeys.add(actual.key);
            actual = actual.next;
        }
        return setKeys;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }
}
