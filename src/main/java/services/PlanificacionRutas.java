package services;

import TDAs.exceptions.EmptyADTException;
import TDAs.structure.definition.GraphADT;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.implementation.dynamic.DinamicGraphADT;
import TDAs.structure.implementation.dynamic.DinamicSetADT;
import TDAs.structure.implementation.dynamic.DynamicLinkedListADT;
import models.Terminal;

public class PlanificacionRutas {
    private GraphADT<Terminal> conexiones;

    public PlanificacionRutas() {
        this.conexiones = new DinamicGraphADT<>();
    }

    public void agregarTerminal(Terminal terminal) {
        this.conexiones.addVertx(terminal);
    }

    public void agregarConexion(Terminal origen, Terminal destino) {
        // Asumimos peso 1 para la conexión base. Si se pide peso, se puede modificar.
        this.conexiones.addEdge(origen, destino, 1);
    }

    public GraphADT<Terminal> getConexiones() {
        return conexiones;
    }

    /**
     * Identificar rutas no utilizadas (terminales desconectados).
     * Devuelve las terminales que no tienen ninguna arista.
     */
    public SetADT<Terminal> identificarTerminalesDesconectadas() {
        SetADT<Terminal> desconectadas = new DinamicSetADT<>();
        SetADT<Terminal> vertices = conexiones.getVertxs();
        SetADT<Terminal> verticesTemp = new DinamicSetADT<>();

        // Para iterar el set:
        while (!vertices.isEmpty()) {
            try {
                Terminal t = vertices.choose();
                vertices.remove(t);
                verticesTemp.add(t);

                boolean tieneConexion = false;
                
                // Comprobar si tiene conexion con alguna otra terminal
                SetADT<Terminal> checkDestinos = conexiones.getVertxs();
                SetADT<Terminal> tempDestinos = new DinamicSetADT<>();
                while (!checkDestinos.isEmpty()) {
                    Terminal dest = checkDestinos.choose();
                    checkDestinos.remove(dest);
                    tempDestinos.add(dest);
                    
                    if (!t.equals(dest)) {
                        if (conexiones.existsEdge(t, dest) || conexiones.existsEdge(dest, t)) {
                            tieneConexion = true;
                        }
                    }
                }
                // Restaurar checkDestinos
                while (!tempDestinos.isEmpty()) {
                    Terminal dest = tempDestinos.choose();
                    tempDestinos.remove(dest);
                    checkDestinos.add(dest);
                }

                if (!tieneConexion) {
                    desconectadas.add(t);
                }

            } catch (EmptyADTException e) {
                break;
            }
        }

        // Restaurar vertices originales
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

    /**
     * Determinar todas las rutas posibles entre dos terminales considerando un máximo de paradas.
     * Si no se especifica maxParadas, podría usarse un número grande.
     */
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
            // Guardar copia de la ruta
            LinkedListADT<Terminal> rutaCopia = new DynamicLinkedListADT<>();
            for (int i = 0; i < rutaActual.size(); i++) {
                rutaCopia.add(rutaActual.get(i));
            }
            rutas.add(rutaCopia);
        } else if (paradasActuales <= maxParadas) {
            SetADT<Terminal> vertices = conexiones.getVertxs();
            SetADT<Terminal> tempVertices = new DinamicSetADT<>();

            while (!vertices.isEmpty()) {
                try {
                    Terminal adyacente = vertices.choose();
                    vertices.remove(adyacente);
                    tempVertices.add(adyacente);

                    if (conexiones.existsEdge(actual, adyacente) && !visitados.exist(adyacente)) {
                        dfs(adyacente, destino, maxParadas, rutas, rutaActual, visitados, paradasActuales + 1);
                    }
                } catch (EmptyADTException e) {
                    break;
                }
            }

            // Restaurar vertices
            while (!tempVertices.isEmpty()) {
                try {
                    Terminal t = tempVertices.choose();
                    tempVertices.remove(t);
                    vertices.add(t);
                } catch (EmptyADTException e) {
                    break;
                }
            }
        }

        // Backtracking
        rutaActual.remove(rutaActual.size() - 1);
        visitados.remove(actual);
    }
}
