package controllers;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.definition.SimpleDictionaryADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicSimpleDictionaryADT;
import models.Terminal;
import exception.DuplicateTerminalException;
import exception.TerminalNotFoundException;

public class TerminalGestor {

    private SimpleDictionaryADT<String, Terminal> terminales;

    public TerminalGestor() {
        this.terminales = new DynamicSimpleDictionaryADT<>();
    }

    public void agregarTerminal(Terminal terminal) {
        // Evitar llamar a get() sobre un diccionario vacío: usar getKeys().exist()
        if (terminales.getKeys().exist(terminal.getCodigo())) {
            throw new DuplicateTerminalException("Terminal con codigo " + terminal.getCodigo() + " ya existe");
        }
        terminales.add(terminal.getCodigo(), terminal);
        GrafoRutas.getInstance().agregarTerminal(terminal);
    }

    public void eliminarTerminal(String codigo) {
        if (!terminales.getKeys().exist(codigo)) {
            throw new TerminalNotFoundException("Terminal con codigo " + codigo + " no encontrada");
        }
        Terminal t = terminales.get(codigo);
        terminales.remove(codigo);
        GrafoRutas.getInstance().eliminarTerminal(t);
    }

    public Terminal obtenerTerminal(String codigo) {
        try {
            return terminales.get(codigo);
        } catch (Exception e) {
            return null;
        }
    }

    public LinkedListADT<Terminal> listarTerminales() {
        LinkedListADT<Terminal> lista = new DynamicLinkedListADT<>();
        SetADT<String> keys = terminales.getKeys();
        
        while (!keys.isEmpty()) {
            try {
                String key = keys.choose();
                lista.add(terminales.get(key));
                keys.remove(key);
            } catch (Exception e) {
                break;
            }
        }
        return lista;
    }

    public boolean existeTerminal(String codigo) {
        return terminales.getKeys().exist(codigo);
    }
}
