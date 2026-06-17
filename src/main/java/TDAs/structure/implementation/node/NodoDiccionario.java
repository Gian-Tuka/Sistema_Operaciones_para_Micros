package TDAs.structure.implementation.node;

public class NodoDiccionario<K, V> {
    public K key;
    public V value;
    public NodoDiccionario<K, V> next;

    public NodoDiccionario(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
