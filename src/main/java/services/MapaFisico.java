package services;

import models.Terminal;
import TDAs.structure.implementation.dynamic.DinamicGraphADT;

public class MapaFisico {

    private static DinamicGraphADT<Terminal> grafoInstancia;

    private MapaFisico() {
    }

    public static DinamicGraphADT<Terminal> getGrafo() {
        if (grafoInstancia == null) {
            grafoInstancia = new DinamicGraphADT<>();
            inicializarTerminalesBase(grafoInstancia);
        }
        return grafoInstancia;
    }

    private static void inicializarTerminalesBase(DinamicGraphADT<Terminal> grafo) {
        grafo.addVertx(new Terminal("BUE", "Terminal de Ómnibus de Retiro (Buenos Aires)"));
        grafo.addVertx(new Terminal("COR", "Terminal de Ómnibus de Córdoba Capital (Córdoba)"));
        grafo.addVertx(new Terminal("ROS", "Terminal Mariano Moreno (Rosario, Santa Fe)"));
        grafo.addVertx(new Terminal("MDZ", "Terminal del Sol (Mendoza Capital)"));
        grafo.addVertx(new Terminal("SLA", "Terminal de Ómnibus de Salta Capital"));
        grafo.addVertx(new Terminal("TUC", "Terminal de Ómnibus de San Miguel de Tucumán"));
        grafo.addVertx(new Terminal("SFE", "Terminal de Ómnibus de Santa Fe Capital"));
        grafo.addVertx(new Terminal("NQN", "Terminal de Ómnibus de Neuquén"));
        grafo.addVertx(new Terminal("BRC", "Terminal de Ómnibus de San Carlos de Bariloche"));
        grafo.addVertx(new Terminal("POS", "Terminal de Ómnibus de Posadas (Misiones)"));
        grafo.addVertx(new Terminal("RGL", "Terminal de Río Gallegos (Santa Cruz)"));
        grafo.addVertx(new Terminal("RES", "Terminal de Ómnibus de Resistencia (Chaco)"));
        grafo.addVertx(new Terminal("SDE", "Terminal de Santiago del Estero Capital"));
        grafo.addVertx(new Terminal("TRE", "Terminal de Ómnibus de Trelew (Chubut)"));
    }
}
