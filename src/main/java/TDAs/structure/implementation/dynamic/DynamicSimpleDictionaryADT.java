package TDAs.structure.implementation.dynamic;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.definition.SimpleDictionaryADT;
import TDAs.structure.implementation.node.NodoDiccionario;


public class DynamicSimpleDictionaryADT<T> implements SimpleDictionaryADT<T> {

    private NodoDiccionario<T> head;

    @Override
    public void add(T key, T value) {
        NodoDiccionario<T> actual = this.head;

        while (actual != null) {
            if (actual.key.equals(key)) {
                actual.value = value;
                return;
            }
            actual = actual.next;
        }

        NodoDiccionario<T> nuevoNodo = new NodoDiccionario<>(key, value);
        nuevoNodo.next = this.head;
        this.head = nuevoNodo;

    }

    @Override
    public void remove(T key) {
        if (isEmpty()){
            return;
        }

        if (this.head.key.equals(key)) {
            this.head = this.head.next;
            return;
        }

        NodoDiccionario<T> actual = this.head;
        while (actual.next != null) {
            if (actual.next.key.equals(key)) {
                actual.next = actual.next.next;
                return;
            }
            actual = actual.next;
        }

    }

    /**
     * Descripcion: Devuelve el valor de una clave.
     * Precondición: La estructura debe tener elementos y la clave debe existir.
     */
    @Override
    public T get(T key) {
        NodoDiccionario<T> actual = this.head;

        while (actual != null) {
            if (actual.key.equals(key)) {
                return actual.value;
            }
            actual = actual.next;
        }
        return null;
    }

    @Override
    public SetADT getKeys() {
        SetADT setKeys = new DynamicSetADT();
        NodoDiccionario<T> actual = this.head;

        while( actual != null) {
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
