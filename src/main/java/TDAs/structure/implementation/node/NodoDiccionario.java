package TDAs.structure.implementation.node;

public class NodoDiccionario<T> {
    public T key;
    public T value;
    public NodoDiccionario<T> next;

    public NodoDiccionario(T key, T value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
