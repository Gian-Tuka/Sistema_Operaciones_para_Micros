package services;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.definition.SimpleDictionaryADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicSimpleDictionaryADT;
import models.Micro;
import TDAs.exceptions.EmptyADTException;

public class GestionFlota {

    private LinkedListADT<Micro> flota;
    private SimpleDictionaryADT<String, Micro> diccionarioMicros;
    private SimpleDictionaryADT<String, Integer> asignaciones;

    public GestionFlota() {
        this.flota = new DynamicLinkedListADT<>();
        this.diccionarioMicros = new DynamicSimpleDictionaryADT<>();
        this.asignaciones = new DynamicSimpleDictionaryADT<>();
    }

    public void agregarMicro(Micro micro) {
        this.flota.add(micro);
        this.diccionarioMicros.add(micro.getPatente(), micro);
        this.asignaciones.add(micro.getPatente(), 0);
    }

    public Micro obtenerMicro(String patente) {
        return this.diccionarioMicros.get(patente);
    }

    public void actualizarDisponibilidad(String patente, boolean estado) {
        Micro m = obtenerMicro(patente);
        if (m != null) {
            m.setDisponible(estado);
        }
    }

    public void registrarAsignacion(String patente) {
        Integer cantidadActual = this.asignaciones.get(patente);
        if (cantidadActual != null) {
            this.asignaciones.add(patente, cantidadActual + 1);
        }
    }

    public Micro obtenerMicroMasAsignado() {
        SetADT<String> patentes = this.asignaciones.getKeys();
        String patenteMax = null;
        int maxAsignaciones = -1;

        SetADT<String> patentesTemp = new TDAs.structure.implementation.dynamic.DinamicSetADT<>();

        while (!patentes.isEmpty()) {
            try {
                String p = patentes.choose();
                patentes.remove(p);
                patentesTemp.add(p);

                Integer asig = this.asignaciones.get(p);
                if (asig != null && asig > maxAsignaciones) {
                    maxAsignaciones = asig;
                    patenteMax = p;
                }
            } catch (EmptyADTException e) {
                break;
            }
        }

        // Restaurar claves al diccionario no es necesario porque getKeys devuelve una copia de las claves.

        if (patenteMax != null) {
            return obtenerMicro(patenteMax);
        }
        return null;
    }

    public int getCantidadAsignaciones(String patente) {
        Integer asig = this.asignaciones.get(patente);
        return asig != null ? asig : 0;
    }

    public LinkedListADT<Micro> getFlota() {
        return flota;
    }
}
