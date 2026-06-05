package TDAs.structure.implementation.node;

public class Nodo {
    private int value;
    private Nodo next = null;



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Nodo getNext() {
        return next;
    }

    public void setNext(Nodo next) {
        this.next = next;
    }

    public Nodo(int value){
        this.value = value;
        this.next = null;
    }


}
