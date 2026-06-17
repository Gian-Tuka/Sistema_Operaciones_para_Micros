package services;

import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.GraphADT;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.implementation.dynamic.DinamicSetADT;
import TDAs.structure.implementation.dynamic.DinamicGraphADT;
import TDAs.structure.implementation.node.NodeGraph;
import models.Terminal;
import models.Viaje;

public class AnalisisConexiones {

    private PlanificacionRutas planificacionRutas;
    private GestorViajes gestorViajes;

    public AnalisisConexiones(PlanificacionRutas planificacionRutas, GestorViajes gestorViajes) {
        this.planificacionRutas = planificacionRutas;
        this.gestorViajes = gestorViajes;
    }

    public void listarTerminalesOperadas() {
        System.out.println("--- Terminales Operadas ---");
        SetADT<Terminal> terminales = planificacionRutas.getConexiones().getVertxs();
        SetADT<Terminal> temp = new DinamicSetADT<>();

        while (!terminales.isEmpty()) {
            try {
                Terminal t = terminales.choose();
                terminales.remove(t);
                temp.add(t);
                System.out.println(t.toString());
            } catch (EmptyADTException e) {
                break;
            }
        }

        while (!temp.isEmpty()) {
            try {
                Terminal t = temp.choose();
                temp.remove(t);
                terminales.add(t);
            } catch (EmptyADTException e) {
                break;
            }
        }
    }

    public void reporteTerminalMayorTrafico() {
        LinkedListADT<Viaje> viajes = gestorViajes.getHistorialViajes();
        SetADT<Terminal> terminales = planificacionRutas.getConexiones().getVertxs();
        SetADT<Terminal> temp = new DinamicSetADT<>();

        Terminal maxTerminal = null;
        int maxTrafico = -1;

        while (!terminales.isEmpty()) {
            try {
                Terminal t = terminales.choose();
                terminales.remove(t);
                temp.add(t);

                int llegadasYSalidas = 0;
                for (int i = 0; i < viajes.size(); i++) {
                    Viaje v = viajes.get(i);
                    if (v.getOrigen().equals(t) || v.getDestino().equals(t)) {
                        llegadasYSalidas++;
                    }
                }

                if (llegadasYSalidas > maxTrafico) {
                    maxTrafico = llegadasYSalidas;
                    maxTerminal = t;
                }

            } catch (EmptyADTException e) {
                break;
            }
        }

        while (!temp.isEmpty()) {
            try {
                Terminal t = temp.choose();
                temp.remove(t);
                terminales.add(t);
            } catch (EmptyADTException e) {
                break;
            }
        }

        if (maxTerminal != null) {
            System.out.println("Terminal con mayor número de salidas y llegadas: " + maxTerminal.getCodigo() + " con " + maxTrafico + " movimientos.");
        } else {
            System.out.println("No hay datos suficientes para el reporte de tráfico.");
        }
    }

    public void reporteTerminalMasConexionesDirectas() {
        DinamicGraphADT<Terminal> grafo = (DinamicGraphADT<Terminal>) planificacionRutas.getConexiones();
        SetADT<Terminal> terminales = grafo.getVertxs();
        SetADT<Terminal> temp = new DinamicSetADT<>();

        Terminal maxConectada = null;
        int maxConexiones = -1;

        while (!terminales.isEmpty()) {
            try {
                Terminal t = terminales.choose();
                terminales.remove(t);
                temp.add(t);

                NodeGraph<Terminal> vNode = grafo.findVertexNode(t);
                int conexionesDirectas = vNode != null ? vNode.getOutgoingEdges().size() : 0;

                if (conexionesDirectas > maxConexiones) {
                    maxConexiones = conexionesDirectas;
                    maxConectada = t;
                }

            } catch (EmptyADTException e) {
                break;
            }
        }

        while (!temp.isEmpty()) {
            try {
                Terminal t = temp.choose();
                temp.remove(t);
                terminales.add(t);
            } catch (EmptyADTException e) {
                break;
            }
        }

        if (maxConectada != null) {
            System.out.println("Terminal con más conexiones directas: " + maxConectada.getCodigo() + " con " + maxConexiones + " conexiones salientes.");
        } else {
            System.out.println("No hay suficientes terminales para analizar conexiones.");
        }
    }
}
