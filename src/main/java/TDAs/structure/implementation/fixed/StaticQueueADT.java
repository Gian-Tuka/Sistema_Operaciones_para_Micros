package TDAs.structure.implementation.fixed;

import TDAs.exceptions.EmptyADTException;
import TDAs.exceptions.FullADTException;
import TDAs.structure.definition.QueueADT;

public class StaticQueueADT implements QueueADT {


    private static final int CAPACITY = 100;
    private int[] data;
    private int front;
    private int back;
    private int size;



    public StaticQueueADT(){
        this.data = new int[CAPACITY];
        this.front = -1;
        this.back = -1;
        this.size = 0;
    }
    /**
     * Descripcion: Devuelve el primer elemento de la estructura.
     * Precondición: La estructura debe tener elementos.
     */
    @Override
    public int getElement() {
        validateNotEmpty();

        return this.data[front];
    }


    /**
     * Descripcion: Agrega un elemento al final de la estructura.
     * Precondición: La estructura no debe sobrepasar la
     * capacidad.
     */
    @Override
    public void add(int value) {
        validateCapacity();

        if (this.back + 1 == CAPACITY){
            throw new FullADTException("maximo limite alcanzado");
        }

        if (this.isEmpty()) {
            this.front = 0;
            this.back = 0;

        }else {
            this.back++;
        }

        this.data[back] = value;
        this.size++;
    }



    /**
     * Descripcion: Elimina el primer elemento que existe.
     * Precondición: La estructura debe tener elementos.
     */
    @Override
    public void remove() {
        validateNotEmpty();

        //Esto es que solo tengo un elemento
        if (this.front == this.back){
            this.front = -1;
            this.back = -1;
        }else{
            this.front++;

        }
        this.size--;
    }

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores.
     * Precondición: No tiene.
     */
    @Override
    public boolean isEmpty() {
        return this.front == -1;
    }


    private void validateNotEmpty() {
        if (this.isEmpty()) {
            throw new EmptyADTException("Queue vacía.");
        }
    }

    private void validateCapacity() {
        if (this.size == CAPACITY){ throw new FullADTException("Se sobrepasó el límite de la queue.");}
    }
}
