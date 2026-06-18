package controllers;

import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.definition.SimpleDictionaryADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DynamicSimpleDictionaryADT;
import exception.InvalidInputException;
import models.Terminal;
import exception.DuplicateTerminalException;
import exception.TerminalNotFoundException;

public class TerminalGestor {

    private SimpleDictionaryADT<String, Terminal> terminales;

    public TerminalGestor() {
        this.terminales = new DynamicSimpleDictionaryADT<>();
    }

    private static final String[] PROVINCIAS_ARGENTINAS = {
            "BUE", "COR", "ROS", "MDZ", "SLA", "TUC", "SFE", "NQN",
            "BRC", "POS", "RGL", "RES", "SDE", "TRE", "CHC", "FOR",
            "SGO", "LPA", "CAT", "JUJ", "MZA", "RNE", "SLU", "TDF"
    };

    public void agregarTerminal(Terminal terminal) {
        String codigo = terminal.getCodigo() != null ? terminal.getCodigo().trim().toUpperCase() : "";

        if (!esProvinciaValida(codigo)) {
            throw new InvalidInputException("Código '" + codigo + "' no es una provincia argentina válida");
        }

        terminal.setCodigo(codigo);

        if (terminales.getKeys().exist(codigo)) {
            throw new DuplicateTerminalException("Terminal con código " + codigo + " ya existe");
        }

        terminales.add(codigo, terminal);
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

    public boolean existeTerminal(String codigo) {return terminales.getKeys().exist(codigo);}

    private boolean esProvinciaValida(String codigo) {
        for (String provincia : PROVINCIAS_ARGENTINAS) {
            if (provincia.equals(codigo)) {
                return true;
            }
        }
        return false;
    }
}
