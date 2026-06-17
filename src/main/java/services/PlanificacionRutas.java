package services;

import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.GraphADT;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.implementation.dynamic.DinamicSetADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import TDAs.structure.implementation.dynamic.DinamicGraphADT;
import TDAs.structure.implementation.node.Edge;
import TDAs.structure.implementation.node.NodeGraph;
import models.Terminal;

public class PlanificacionRutas {

    public PlanificacionRutas() {
    }

    public void agregarTerminal(Terminal terminal) {
        MapaFisico.getGrafo().addVertx(terminal);
    }

    public void agregarConexion(Terminal origen, Terminal destino) {
        // Asumimos peso ficticio 100km por defecto
        MapaFisico.getGrafo().addEdge(origen, destino, 100);
    }

    public GraphADT<Terminal> getConexiones() {
        return MapaFisico.getGrafo();
    }

    public SetADT<Terminal> identificarTerminalesDesconectadas() {
        SetADT<Terminal> desconectadas = new DinamicSetADT<>();
        DinamicGraphADT<Terminal> grafo = MapaFisico.getGrafo();
        SetADT<Terminal> vertices = grafo.getVertxs();
        SetADT<Terminal> verticesTemp = new DinamicSetADT<>();

        while (!vertices.isEmpty()) {
            try {
                Terminal t = vertices.choose();
                vertices.remove(t);
                verticesTemp.add(t);

                NodeGraph<Terminal> vNode = grafo.findVertexNode(t);
                if (vNode != null && vNode.getIncomingEdges().isEmpty() && vNode.getOutgoingEdges().isEmpty()) {
                    desconectadas.add(t);
                }
            } catch (EmptyADTException e) {
                break;
            }
        }

        while (!verticesTemp.isEmpty()) {
            try {
                Terminal t = verticesTemp.choose();
                verticesTemp.remove(t);
                vertices.add(t);
            } catch (EmptyADTException e) {
                break;
            }
        }

        return desconectadas;
    }

    public LinkedListADT<LinkedListADT<Terminal>> determinarRutasPosibles(Terminal origen, Terminal destino, int maxParadas) {
        LinkedListADT<LinkedListADT<Terminal>> todasLasRutas = new DynamicLinkedListADT<>();
        LinkedListADT<Terminal> rutaActual = new DynamicLinkedListADT<>();
        SetADT<Terminal> visitados = new DinamicSetADT<>();

        dfs(origen, destino, maxParadas, todasLasRutas, rutaActual, visitados, 0);

        return todasLasRutas;
    }

    private void dfs(Terminal actual, Terminal destino, int maxParadas,
                     LinkedListADT<LinkedListADT<Terminal>> rutas,
                     LinkedListADT<Terminal> rutaActual,
                     SetADT<Terminal> visitados, int paradasActuales) {
        
        visitados.add(actual);
        rutaActual.add(actual);

        if (actual.equals(destino)) {
            LinkedListADT<Terminal> rutaCopia = new DynamicLinkedListADT<>();
            for (int i = 0; i < rutaActual.size(); i++) {
                rutaCopia.add(rutaActual.get(i));
            }
            rutas.add(rutaCopia);
        } else if (paradasActuales <= maxParadas) {
            DinamicGraphADT<Terminal> grafo = MapaFisico.getGrafo();
            NodeGraph<Terminal> vNode = grafo.findVertexNode(actual);
            if (vNode != null) {
                LinkedListADT<Edge<Terminal>> salidas = vNode.getOutgoingEdges();
                for (int i = 0; i < salidas.size(); i++) {
                    Terminal adyacente = salidas.get(i).getDestination();
                    if (!visitados.exist(adyacente)) {
                        dfs(adyacente, destino, maxParadas, rutas, rutaActual, visitados, paradasActuales + 1);
                    }
                }
            }
        }

        rutaActual.remove(rutaActual.size() - 1);
        visitados.remove(actual);
    }
}
