package TDAs.structure.implementation.dynamic;

import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;

import java.util.Random;

// Esta clase representa la implementacion dinamica del TDA Conjunto.
public class DynamicSetADT<T> implements SetADT<T> {

    private LinkedListADT<T> set = new DynamicLinkedListADT<>();

    @Override
    public boolean exist(T value) {

        if (set.isEmpty()){
            return false;
        }

        for (int i = 0; i < set.size(); i++){
            if(set.get(i).equals(value)){
                return true;
            }
        }

        return false;
    }

    @Override
    public T choose() {
        if (set.isEmpty()){
            throw new EmptyADTException("Set vacio");
        }

        Random rand = new Random();
        int random = rand.nextInt(set.size());
        return set.get(random);

    }

    @Override
    public void add(T value) {
        if (!exist(value)){
            set.add(value);
        }
    }

    @Override
    public void remove(T element) {
        if (exist(element)){
            for (int i = 0; i < set.size(); i++){
                if (element.equals(set.get(i))){
                    set.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }
}
