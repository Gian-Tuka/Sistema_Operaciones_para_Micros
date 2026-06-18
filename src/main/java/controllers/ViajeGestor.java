package controllers;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.PriorityQueueADT;
import TDAs.structure.definition.SimpleDictionaryADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicPriorityQueueADT;
import TDAs.structure.implementation.dynamic.DynamicSimpleDictionaryADT;
import models.Micro;
import models.Terminal;
import models.Viaje;
import exception.ViajeNoEncontradoException;
import exception.ViajeDuplicadoException;

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

    public SimpleDictionaryADT<String, Integer> contarSalidas() {
        SimpleDictionaryADT<String, Integer> salidas = new DynamicSimpleDictionaryADT<>();

        for (int i = 0; i < todosLosViajes.size(); i++) {
            String codOrigen = todosLosViajes.get(i).getOrigen().getCodigo();
            try {
                int count = salidas.get(codOrigen);
                salidas.remove(codOrigen);
                salidas.add(codOrigen, count + 1);
            } catch (Exception e) {
                // Si no existe la clave, la agregamos con valor 1
                salidas.add(codOrigen, 1);
            }
        }
        return salidas;
    }

    /** * Cuenta cuántas veces cada terminal es destino de un viaje * @return SimpleDictionaryADT con Terminal como clave y cantidad de llegadas como valor */
    public SimpleDictionaryADT<String, Integer> contarLlegadas() {
        SimpleDictionaryADT<String, Integer> llegadas = new DynamicSimpleDictionaryADT<>();

        for (int i = 0; i < todosLosViajes.size(); i++) {
            String codDestino = todosLosViajes.get(i).getDestino().getCodigo();
            try {
                int count = llegadas.get(codDestino);
                llegadas.remove(codDestino);
                llegadas.add(codDestino, count + 1);
            } catch (Exception e) {
                // Si no existe la clave, la agregamos con valor 1
                llegadas.add(codDestino, 1);
            }
        }
        return llegadas;
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
            throw new ViajeNoEncontradoException("Viaje con ID " + idViaje + " no encontrado");
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
            throw new ViajeNoEncontradoException("Viaje con ID " + idViaje + " no encontrado");
        }
    }

    public String generarID() {
        // Generar un número aleatorio entre 1000 y 9999
        int idGenerado = 1000 + (int) (Math.random() * 9000);
        String idString = String.valueOf(idGenerado);

        // Si el ID ya existe, generar uno nuevo recursivamente
        if (!validarID(idString)) {
            return generarID();
        }

        return idString;
    }

    public boolean validarPrioridad(String prioridadStr) {
        if (prioridadStr == null || prioridadStr.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(prioridadStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //validar id para que no se repita y este dentro de un ID 1000 a 9999
    public Boolean validarID(String idViaje) {
        // Validar que no sea nulo o vacío
        if (idViaje == null || idViaje.isEmpty()) {
            return false;
        }

        // Validar que sea un número
        try {
            int id = Integer.parseInt(idViaje);

            // Validar que esté en el rango 1000-9999
            if (id < 1000 || id > 9999) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Validar que el ID no esté duplicado
        for (int i = 0; i < todosLosViajes.size(); i++) {
            if (todosLosViajes.get(i).getIdViaje().equals(idViaje)) {
                return false;
            }
        }

        return true;
    }

    /**     * Valida que no exista otro viaje con el mismo origen y destino.     * @param origen Terminal de origen     * @param destino Terminal de destino     * @return true si ya existe un viaje con ese par origen-destino     */
    public boolean existeViajeConOriginDestino(Terminal origen, Terminal destino) {
        for (int i = 0; i < todosLosViajes.size(); i++) {
            Viaje v = todosLosViajes.get(i);
            if (v.getOrigen().equals(origen) && v.getDestino().equals(destino)) {
                return true;
            }
        }
        return false;
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