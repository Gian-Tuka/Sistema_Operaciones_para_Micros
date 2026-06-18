package controllers;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.PriorityQueueADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicPriorityQueueADT;
import models.Micro;
import models.Viaje;

public class ViajeGestor {

    private PriorityQueueADT<Viaje> viajesPendientes;
    private LinkedListADT<Viaje> todosLosViajes;

    public ViajeGestor() {
        this.viajesPendientes = new DynamicPriorityQueueADT<>();
        this.todosLosViajes = new DynamicLinkedListADT<>();
    }

    public void agregarViaje(Viaje viaje) {
        todosLosViajes.add(viaje);
        viajesPendientes.add(viaje, viaje.getPrioridad());
    }

    public LinkedListADT<Viaje> listarViajes() {
        return todosLosViajes;
    }

    public LinkedListADT<Viaje> listarViajesPendientes() {
        // Hacemos una copia destructiva de la cola para listarla sin perder datos reales, 
        // o mejor solo devolvemos una lista ordenada usando la priority queue.
        LinkedListADT<Viaje> lista = new DynamicLinkedListADT<>();
        PriorityQueueADT<Viaje> colaAux = new DynamicPriorityQueueADT<>();

        while (!viajesPendientes.isEmpty()) {
            Viaje v = viajesPendientes.getElement();
            int p = viajesPendientes.getPriority();
            lista.add(v);
            colaAux.add(v, p);
            viajesPendientes.remove();
        }

        // Restaurar
        while (!colaAux.isEmpty()) {
            viajesPendientes.add(colaAux.getElement(), colaAux.getPriority());
            colaAux.remove();
        }

        return lista;
    }

    public void reprogramarViaje(String idViaje, String nuevaFecha, Micro nuevoMicro) {
        Viaje viajeTarget = null;
        for (int i = 0; i < todosLosViajes.size(); i++) {
            if (todosLosViajes.get(i).getIdViaje().equals(idViaje)) {
                viajeTarget = todosLosViajes.get(i);
                break;
            }
        }

        if (viajeTarget != null) {
            viajeTarget.setFecha(nuevaFecha);
            if (nuevoMicro != null) {
                viajeTarget.setMicro(nuevoMicro);
            }
            reconstruirCola();
        } else {
            throw new RuntimeException("Viaje no encontrado");
        }
    }

    public void rePriorizarViaje(String idViaje, int nuevaPrioridad) {
        Viaje viajeTarget = null;
        for (int i = 0; i < todosLosViajes.size(); i++) {
            if (todosLosViajes.get(i).getIdViaje().equals(idViaje)) {
                viajeTarget = todosLosViajes.get(i);
                break;
            }
        }

        if (viajeTarget != null) {
            viajeTarget.setPrioridad(nuevaPrioridad);
            reconstruirCola();
        } else {
            throw new RuntimeException("Viaje no encontrado");
        }
    }

    private void reconstruirCola() {
        PriorityQueueADT<Viaje> nuevaCola = new DynamicPriorityQueueADT<>();
        for (int i = 0; i < todosLosViajes.size(); i++) {
            Viaje v = todosLosViajes.get(i);
            nuevaCola.add(v, v.getPrioridad());
        }
        this.viajesPendientes = nuevaCola;
    }
}
