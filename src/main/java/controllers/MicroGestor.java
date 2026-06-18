package controllers;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.definition.SimpleDictionaryADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicSimpleDictionaryADT;
import models.Micro;
import exception.DuplicateMicroException;
import exception.MicroNotFoundException;

public class MicroGestor {

    private SimpleDictionaryADT<String, Micro> micros;

    public MicroGestor() {
        this.micros = new DynamicSimpleDictionaryADT<>();
    }

    public void agregarMicro(Micro micro) {
        // Evitar llamar a get() sobre un diccionario vacío: usar getKeys().exist()
        if (micros.getKeys().exist(micro.getIdPatente())) {
            throw new DuplicateMicroException("Micro con patente " + micro.getIdPatente() + " ya existe");
        }
        micros.add(micro.getIdPatente(), micro);
    }

    public void eliminarMicro(String idPatente) {
        if (!micros.getKeys().exist(idPatente)) {
            throw new MicroNotFoundException("Micro con patente " + idPatente + " no encontrado");
        }
        micros.remove(idPatente);
    }

    public boolean existeMicro(String patente) {
        // Safe existence check without throwing on empty dictionary
        return micros.getKeys().exist(patente);
    }

    public Micro obtenerMicro(String idPatente) {
        try {
            return micros.get(idPatente);
        } catch (Exception e) {
            return null;
        }
    }

    public LinkedListADT<Micro> listarMicros() {
        LinkedListADT<Micro> lista = new DynamicLinkedListADT<>();
        if (micros.isEmpty()) return lista;

        SetADT<String> keys = micros.getKeys();
        LinkedListADT<String> keysCopia = new DynamicLinkedListADT<>();

        // Copiar todas las claves
        while (!keys.isEmpty()) {
            try {
                String key = keys.choose();
                keysCopia.add(key);
                keys.remove(key);
            } catch (Exception e) {
                break;
            }
        }

        // Agregar micros a la lista
        for (int i = 0; i < keysCopia.size(); i++) {
            lista.add(micros.get(keysCopia.get(i)));
        }

        return lista;
    }

    public LinkedListADT<Micro> listarMicrosDisponibles() {
        LinkedListADT<Micro> disponibles = new DynamicLinkedListADT<>();
        LinkedListADT<Micro> todos = listarMicros();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).isDisponible()) {
                disponibles.add(todos.get(i));
            }
        }
        return disponibles;
    }

    public void marcarMicroAsignado(String idPatente) {
        Micro m = obtenerMicro(idPatente);
        if (m != null) {
            m.setDisponible(false);
            m.incrementarAsignaciones();
        }
    }

    public void liberarMicro(String idPatente) {
        Micro m = obtenerMicro(idPatente);
        if (m != null) {
            m.setDisponible(true);
        }
    }

    public LinkedListADT<Micro> obtenerMicrosMayorAsignacion() {
        LinkedListADT<Micro> todos = listarMicros();
        LinkedListADT<Micro> maximos = new DynamicLinkedListADT<>();
        int max = -1;

        for (int i = 0; i < todos.size(); i++) {
            int asignaciones = todos.get(i).getCantidadAsignaciones();
            if (asignaciones > max) {
                max = asignaciones;
            }
        }

        if (max > 0) {
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).getCantidadAsignaciones() == max) {
                    maximos.add(todos.get(i));
                }
            }
        }

        return maximos;
    }
}
