package services;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.PriorityQueueADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicPriorityQueueADT;
import models.Micro;
import models.Viaje;
import TDAs.exceptions.EmptyADTException;

public class GestorViajes {

    private PriorityQueueADT<Viaje> pendientes;
    private LinkedListADT<Viaje> historialViajes; // Todos los viajes

    public GestorViajes() {
        this.pendientes = new DynamicPriorityQueueADT<>();
        this.historialViajes = new DynamicLinkedListADT<>();
    }

    public void registrarViaje(Viaje viaje) {
        pendientes.add(viaje, viaje.getPrioridad());
        historialViajes.add(viaje);
    }

    public boolean reprogramarViaje(Viaje antiguo, String nuevaFecha, Micro nuevoMicro, int nuevaPrioridad) {
        PriorityQueueADT<Viaje> tempQueue = new DynamicPriorityQueueADT<>();
        boolean modificado = false;

        // Desencolar todos
        while (!pendientes.isEmpty()) {
            try {
                Viaje v = pendientes.getElement();
                int p = pendientes.getPriority();
                pendientes.remove();

                if (v.equals(antiguo)) {
                    v.setFecha(nuevaFecha);
                    v.setMicro(nuevoMicro);
                    v.setPrioridad(nuevaPrioridad);
                    // No lo encolo en tempQueue todavía, lo agregaré con la nueva prioridad al final
                    modificado = true;
                } else {
                    tempQueue.add(v, p);
                }
            } catch (EmptyADTException e) {
                break;
            }
        }

        // Restaurar la cola
        while (!tempQueue.isEmpty()) {
            try {
                Viaje v = tempQueue.getElement();
                int p = tempQueue.getPriority();
                tempQueue.remove();
                pendientes.add(v, p);
            } catch (EmptyADTException e) {
                break;
            }
        }

        // Si se modificó, agregarlo de nuevo con su nueva prioridad
        if (modificado) {
            pendientes.add(antiguo, nuevaPrioridad);
        }

        return modificado;
    }

    public PriorityQueueADT<Viaje> getViajesPendientes() {
        return pendientes;
    }

    public LinkedListADT<Viaje> getHistorialViajes() {
        return historialViajes;
    }
}
