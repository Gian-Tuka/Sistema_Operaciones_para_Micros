package controllers;

import TDAs.structure.definition.GraphADT;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.implementation.dynamic.DinamicGraphADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import models.Terminal;

public class GrafoRutas {

    private static GrafoRutas instance;
    private GraphADT<Terminal> grafo;

    private GrafoRutas() {
        this.grafo = new DinamicGraphADT<>();
    }

    public static GrafoRutas getInstance() {
        if (instance == null) {
            instance = new GrafoRutas();
        }
        return instance;
    }

    public void agregarTerminal(Terminal terminal) {
        if (!grafo.getVertxs().exist(terminal)) {
            grafo.addVertx(terminal);
        }
    }

    public void eliminarTerminal(Terminal terminal) {
        if (grafo.getVertxs().exist(terminal)) {
            grafo.removeVertx(terminal);
        }
    }

    public void conectarTerminales(Terminal origen, Terminal destino, int distancia) {
        if (grafo.getVertxs().exist(origen) && grafo.getVertxs().exist(destino)) {
            if (!grafo.existsEdge(origen, destino)) {
                grafo.addEdge(origen, destino, distancia);
            } else {
                throw new RuntimeException("La ruta ya existe");
            }
        } else {
            throw new RuntimeException("Ambas terminales deben existir para conectarlas");
        }
    }

    public void eliminarConexion(Terminal origen, Terminal destino) {
        if (grafo.existsEdge(origen, destino)) {
            grafo.removeEdge(origen, destino);
        } else {
            throw new RuntimeException("La ruta no existe");
        }
    }

    public boolean existeRuta(Terminal origen, Terminal destino) {
        if (grafo.getVertxs().exist(origen) && grafo.getVertxs().exist(destino)) {
            return grafo.existsEdge(origen, destino);
        }
        return false;
    }

    public LinkedListADT<LinkedListADT<Terminal>> obtenerRutasPosibles(Terminal origen, Terminal destino, int maxParadas, LinkedListADT<Terminal> todasLasTerminales) {
        LinkedListADT<LinkedListADT<Terminal>> rutasEncontradas = new DynamicLinkedListADT<>();
        LinkedListADT<Terminal> rutaActual = new DynamicLinkedListADT<>();
        rutaActual.add(origen);
        
        dfsRutas(origen, destino, maxParadas, rutaActual, rutasEncontradas, todasLasTerminales);
        return rutasEncontradas;
    }

    private void dfsRutas(Terminal actual, Terminal destino, int maxParadas, LinkedListADT<Terminal> rutaActual, LinkedListADT<LinkedListADT<Terminal>> rutasEncontradas, LinkedListADT<Terminal> todasLasTerminales) {
        if (actual.equals(destino)) {
            LinkedListADT<Terminal> copia = new DynamicLinkedListADT<>();
            for (int i = 0; i < rutaActual.size(); i++) {
                copia.add(rutaActual.get(i));
            }
            rutasEncontradas.add(copia);
            return;
        }

        int paradasActuales = rutaActual.size() - 1;
        if (paradasActuales > maxParadas) {
            return;
        }

        for (int i = 0; i < todasLasTerminales.size(); i++) {
            Terminal vecino = todasLasTerminales.get(i);
            if (grafo.getVertxs().exist(vecino) && grafo.existsEdge(actual, vecino)) {
                boolean visitado = false;
                for (int j = 0; j < rutaActual.size(); j++) {
                    if (rutaActual.get(j).equals(vecino)) {
                        visitado = true;
                        break;
                    }
                }

                if (!visitado) {
                    rutaActual.add(vecino);
                    dfsRutas(vecino, destino, maxParadas, rutaActual, rutasEncontradas, todasLasTerminales);
                    rutaActual.remove(rutaActual.size() - 1);
                }
            }
        }
    }

    public LinkedListADT<Terminal> obtenerTerminalesDesconectadas(LinkedListADT<Terminal> todasLasTerminales) {
        LinkedListADT<Terminal> desconectadas = new DynamicLinkedListADT<>();

        for (int i = 0; i < todasLasTerminales.size(); i++) {
            Terminal t1 = todasLasTerminales.get(i);
            if (!grafo.getVertxs().exist(t1)) continue;

            boolean conectada = false;
            for (int j = 0; j < todasLasTerminales.size(); j++) {
                Terminal t2 = todasLasTerminales.get(j);
                if (!t1.equals(t2) && grafo.getVertxs().exist(t2)) {
                    if (grafo.existsEdge(t1, t2) || grafo.existsEdge(t2, t1)) {
                        conectada = true;
                        break;
                    }
                }
            }
            if (!conectada) {
                desconectadas.add(t1);
            }
        }
        return desconectadas;
    }

    public GraphADT<Terminal> getGrafo() {
        return grafo;
    }
}
