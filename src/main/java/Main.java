import controllers.ConsoleMenu;
import controllers.GrafoRutas;
import controllers.MicroGestor;
import controllers.TerminalGestor;
import controllers.ViajeGestor;
import models.Micro;
import models.Terminal;
import models.TipoMicro;
import models.Viaje;

public class Main {
    public static void main(String[] args) {
        System.out.println("Inicializando Sistema TPO...");

        TerminalGestor terminalGestor = new TerminalGestor();
        MicroGestor microGestor = new MicroGestor();
        ViajeGestor viajeGestor = new ViajeGestor();
        GrafoRutas grafo = GrafoRutas.getInstance();

        // 1. Cargar 14 Terminales
        terminalGestor.agregarTerminal(new Terminal("BUE", "Terminal de Ómnibus de Retiro (Buenos Aires)"));
        terminalGestor.agregarTerminal(new Terminal("COR", "Terminal de Ómnibus de Córdoba Capital (Córdoba)"));
        terminalGestor.agregarTerminal(new Terminal("ROS", "Terminal Mariano Moreno (Rosario, Santa Fe)"));
        terminalGestor.agregarTerminal(new Terminal("MDZ", "Terminal del Sol (Mendoza Capital)"));
        terminalGestor.agregarTerminal(new Terminal("SLA", "Terminal de Ómnibus de Salta Capital"));
        terminalGestor.agregarTerminal(new Terminal("TUC", "Terminal de Ómnibus de San Miguel de Tucumán"));
        terminalGestor.agregarTerminal(new Terminal("SFE", "Terminal de Ómnibus de Santa Fe Capital"));
        terminalGestor.agregarTerminal(new Terminal("NQN", "Terminal de Ómnibus de Neuquén"));
        terminalGestor.agregarTerminal(new Terminal("BRC", "Terminal de Ómnibus de San Carlos de Bariloche"));
        terminalGestor.agregarTerminal(new Terminal("POS", "Terminal de Ómnibus de Posadas (Misiones)"));
        terminalGestor.agregarTerminal(new Terminal("RGL", "Terminal de Río Gallegos (Santa Cruz)"));
        terminalGestor.agregarTerminal(new Terminal("RES", "Terminal de Ómnibus de Resistencia (Chaco)"));
        terminalGestor.agregarTerminal(new Terminal("SDE", "Terminal de Santiago del Estero Capital"));
        terminalGestor.agregarTerminal(new Terminal("TRE", "Terminal de Ómnibus de Trelew (Chubut)"));

        // Crear algunas conexiones (rutas) de ejemplo
        grafo.conectarTerminales(terminalGestor.obtenerTerminal("BUE"), terminalGestor.obtenerTerminal("ROS"), 300);
        grafo.conectarTerminales(terminalGestor.obtenerTerminal("ROS"), terminalGestor.obtenerTerminal("COR"), 400);
        grafo.conectarTerminales(terminalGestor.obtenerTerminal("BUE"), terminalGestor.obtenerTerminal("MDZ"), 1050);
        grafo.conectarTerminales(terminalGestor.obtenerTerminal("COR"), terminalGestor.obtenerTerminal("SLA"), 850);

        // 2. Cargar 15 Micros
        for (int i = 1; i <= 15; i++) {
            TipoMicro tipo = (i % 3 == 0) ? TipoMicro.CAMA : (i % 2 == 0) ? TipoMicro.SEMI_CAMA : TipoMicro.EJECUTIVO;
            microGestor.agregarMicro(new Micro("MIC-" + String.format("%03d", i), tipo));
        }

        // 3. Cargar 20 Viajes
        for (int i = 1; i <= 20; i++) {
            Terminal orig = terminalGestor.obtenerTerminal((i % 2 == 0) ? "BUE" : "ROS");
            Terminal dest = terminalGestor.obtenerTerminal((i % 2 == 0) ? "ROS" : "COR");
            Micro m = microGestor.obtenerMicro("MIC-" + String.format("%03d", (i % 15) + 1));
            int prio = (i % 5) + 1; // Prioridad del 1 al 5

            Viaje v = new Viaje("V-" + i, orig, dest, m, "2026-07-0" + (i % 9 + 1), prio);
            viajeGestor.agregarViaje(v);
            microGestor.marcarMicroAsignado(m.getIdPatente());
        }

        ConsoleMenu menu = new ConsoleMenu(terminalGestor, microGestor, viajeGestor);
        menu.start();
    }
}