import exception.MicroNoDisponibleException;
import models.Micro;
import models.Terminal;
import models.Viaje;
import services.SistemaTransporte;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.PriorityQueueADT;
import TDAs.structure.definition.SetADT;
import TDAs.exceptions.EmptyADTException;
import TDAs.exceptions.GenericADTException;

import java.util.Scanner;

public class Main {

    private static SistemaTransporte sistema = new SistemaTransporte();

    public static void main(String[] args) {
        inicializarDatos();
        mostrarMenu();
    }

    private static void inicializarDatos() {
        // Inicializar terminales hardcodeadas ya ocurre al obtener el Grafo.
        TDAs.structure.implementation.dynamic.DinamicGraphADT<Terminal> grafo = services.MapaFisico.getGrafo();

        // Conexiones (Distancias ficticias)
        Terminal bue = new Terminal("BUE", "");
        Terminal cor = new Terminal("COR", "");
        Terminal ros = new Terminal("ROS", "");
        Terminal mdz = new Terminal("MDZ", "");
        Terminal sla = new Terminal("SLA", "");
        Terminal tuc = new Terminal("TUC", "");
        Terminal sfe = new Terminal("SFE", "");
        Terminal nqn = new Terminal("NQN", "");
        Terminal brc = new Terminal("BRC", "");
        Terminal pos = new Terminal("POS", "");

        grafo.addEdge(bue, ros, 300);
        grafo.addEdge(ros, cor, 400);
        grafo.addEdge(cor, mdz, 600);
        grafo.addEdge(bue, mdz, 1000);
        grafo.addEdge(cor, sla, 850);
        grafo.addEdge(sla, tuc, 310);
        grafo.addEdge(bue, sfe, 470);
        grafo.addEdge(sfe, pos, 700);
        grafo.addEdge(bue, nqn, 1140);
        grafo.addEdge(nqn, brc, 430);

        // 15 Micros
        String[] tipos = {"Ejecutivo", "Semi-cama", "Cama"};
        for (int i = 1; i <= 15; i++) {
            String patente = "AB" + (100 + i) + "CD";
            Micro m = new Micro(patente, tipos[i % 3]);
            sistema.registrarMicro(m);
        }

        // 20 Viajes
        try {
            String[] codigosTerm = {"BUE", "COR", "ROS", "MDZ", "SLA", "TUC", "SFE", "NQN", "BRC", "POS"};
            for (int i = 0; i < 20; i++) {
                Terminal orig = new Terminal(codigosTerm[i % 10], "");
                Terminal dest = new Terminal(codigosTerm[(i + 3) % 10], ""); 
                String patente = "AB" + (100 + (i % 15) + 1) + "CD";
                sistema.crearViaje(orig, dest, patente, "2025-10-" + (10 + (i % 20)), (i % 5) + 1);
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar viajes: " + e.getMessage());
        }
    }

    private static String leerCadenaNoVacia(Scanner scanner, String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Error: El valor no puede ser vacío. Intente nuevamente.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        int valor = 0;
        boolean valido = false;
        do {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido. Intente nuevamente.");
            }
        } while (!valido);
        return valor;
    }

    private static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n===== SISTEMA DE GESTIÓN DE TRANSPORTE =====");
            System.out.println("1. Viajes");
            System.out.println("2. Micros");
            System.out.println("3. Terminales");
            System.out.println("4. Rutas");
            System.out.println("0. Salir");
            
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        menuViajes(scanner);
                        break;
                    case 2:
                        menuMicros(scanner);
                        break;
                    case 3:
                        menuTerminales(scanner);
                        break;
                    case 4:
                        menuRutas(scanner);
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (GenericADTException e) {
                System.out.println("Error interno de estructura: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado en la ejecución del sistema: " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void menuViajes(Scanner scanner) {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ VIAJES ---");
            System.out.println("1. Ver viajes");
            System.out.println("2. Agregar viaje");
            System.out.println("3. Reprogramar viaje");
            System.out.println("4. Re-priorizar viaje");
            System.out.println("0. Volver");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1: mostrarViajesPendientes(); break;
                case 2: menuAgregarViaje(scanner); break;
                case 3: menuReprogramarViaje(scanner); break;
                case 4: System.out.println("Funcionalidad a implementar..."); break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void menuMicros(Scanner scanner) {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ MICROS ---");
            System.out.println("1. Ver micros");
            System.out.println("2. Agregar micro");
            System.out.println("3. Editar micro");
            System.out.println("4. Eliminar micro");
            System.out.println("5. Reporte | Promedio Utilización micros");
            System.out.println("6. Reporte | Asignaciones Micros");
            System.out.println("0. Volver");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                    System.out.println("Funcionalidad a implementar..."); 
                    break;
                case 5: 
                    sistema.mostrarUtilizacionPromedio(); 
                    break;
                case 6: 
                    Micro masAsig = sistema.getGestionFlota().obtenerMicroMasAsignado();
                    if (masAsig != null) {
                        int asig = sistema.getGestionFlota().getCantidadAsignaciones(masAsig.getPatente());
                        System.out.println("El micro más asignado es: " + masAsig.getPatente() + " con " + asig + " asignaciones.");
                    } else {
                        System.out.println("No hay micros con asignaciones.");
                    }
                    break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void menuTerminales(Scanner scanner) {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ TERMINALES ---");
            System.out.println("1. Ver terminales");
            System.out.println("2. Agregar terminales");
            System.out.println("3. Eliminar terminales");
            System.out.println("4. Reporte | Conexiones directas");
            System.out.println("5. Reporte | Conexiones generales");
            System.out.println("0. Volver");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1: 
                case 2: 
                case 3: 
                    System.out.println("Funcionalidad a implementar..."); 
                    break;
                case 4: 
                    sistema.getAnalisisConexiones().listarTerminalesOperadas();
                    System.out.println();
                    sistema.getAnalisisConexiones().reporteTerminalMasConexionesDirectas();
                    break;
                case 5: 
                    sistema.getAnalisisConexiones().reporteTerminalMayorTrafico();
                    System.out.println("\nTerminales desconectadas:");
                    SetADT<Terminal> desc = sistema.getPlanificacionRutas().identificarTerminalesDesconectadas();
                    SetADT<Terminal> temp = new TDAs.structure.implementation.dynamic.DinamicSetADT<>();
                    boolean hayDesc = false;
                    while (!desc.isEmpty()) {
                        try {
                            Terminal t = desc.choose();
                            desc.remove(t);
                            temp.add(t);
                            System.out.println("- " + t);
                            hayDesc = true;
                        } catch (EmptyADTException e) {
                            break;
                        }
                    }
                    if (!hayDesc) System.out.println("(Ninguna)");
                    break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void menuRutas(Scanner scanner) {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ RUTAS ---");
            System.out.println("1. Ver Ruta");
            System.out.println("2. Crear Ruta");
            System.out.println("3. Eliminar Ruta");
            System.out.println("4. Reporte | Utilizacion de rutas");
            System.out.println("5. Reporte | Rutas no utilizadas");
            System.out.println("0. Volver");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1: menuRutasPosibles(scanner); break;
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                    System.out.println("Funcionalidad a implementar..."); 
                    break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void menuAgregarViaje(Scanner scanner) {
        System.out.println("\n-- Agregar Viaje --");
        String codOrigen = leerCadenaNoVacia(scanner, "Ingrese código Terminal Origen (ej: BUE): ").toUpperCase();
        String codDestino = leerCadenaNoVacia(scanner, "Ingrese código Terminal Destino (ej: COR): ").toUpperCase();
        String patente = leerCadenaNoVacia(scanner, "Ingrese Patente de Micro: ").toUpperCase();
        String fecha = leerCadenaNoVacia(scanner, "Ingrese Fecha (ej: 2025-11-01): ");
        int prioridad = leerEntero(scanner, "Ingrese Prioridad (entero mayor es más prioritario): ");

        try {
            Terminal origenDummy = new Terminal(codOrigen, "");
            Terminal destinoDummy = new Terminal(codDestino, "");

            sistema.crearViaje(origenDummy, destinoDummy, patente, fecha, prioridad);
            System.out.println("Viaje creado correctamente.");
        } catch (MicroNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al crear viaje: " + e.getMessage());
        }
    }

    private static void menuReprogramarViaje(Scanner scanner) {
        System.out.println("\n-- Reprogramar Viaje --");
        System.out.println("Nota: Para buscar el viaje a reprogramar, se requiere exactitud.");
        String codOrigen = leerCadenaNoVacia(scanner, "Ingrese código Terminal Origen Original: ").toUpperCase();
        String codDestino = leerCadenaNoVacia(scanner, "Ingrese código Terminal Destino Original: ").toUpperCase();
        String fecha = leerCadenaNoVacia(scanner, "Ingrese NUEVA Fecha: ");
        String patente = leerCadenaNoVacia(scanner, "Ingrese NUEVO Micro (Patente): ").toUpperCase();
        int prioridad = leerEntero(scanner, "Ingrese NUEVA Prioridad: ");

        Viaje antiguo = null;
        LinkedListADT<Viaje> viajes = sistema.getGestorViajes().getHistorialViajes();
        for (int i = 0; i < viajes.size(); i++) {
            Viaje v = viajes.get(i);
            if (v.getOrigen().getCodigo().equals(codOrigen) && v.getDestino().getCodigo().equals(codDestino)) {
                antiguo = v; 
                break;
            }
        }

        if (antiguo == null) {
            System.out.println("Viaje no encontrado en el historial.");
            return;
        }

        try {
            boolean modificado = sistema.reprogramarViaje(antiguo, fecha, patente, prioridad);
            if (modificado) {
                System.out.println("Viaje reprogramado exitosamente.");
            } else {
                System.out.println("No se encontró el viaje en pendientes (ya realizado o borrado).");
            }
        } catch (MicroNoDisponibleException e) {
             System.out.println("Error: " + e.getMessage());
        }
    }



    private static void menuRutasPosibles(Scanner scanner) {
        System.out.println("\n-- Rutas Posibles --");
        String codOrigen = leerCadenaNoVacia(scanner, "Ingrese código Terminal Origen (ej: BUE): ").toUpperCase();
        String codDestino = leerCadenaNoVacia(scanner, "Ingrese código Terminal Destino (ej: COR): ").toUpperCase();
        int maxParadas = leerEntero(scanner, "Máximo de paradas intermedias: ");

        Terminal origenDummy = new Terminal(codOrigen, "");
        Terminal destinoDummy = new Terminal(codDestino, "");

        LinkedListADT<LinkedListADT<Terminal>> rutas = sistema.getPlanificacionRutas().determinarRutasPosibles(origenDummy, destinoDummy, maxParadas);
        System.out.println("Se encontraron " + rutas.size() + " ruta(s):");
        for (int i = 0; i < rutas.size(); i++) {
            LinkedListADT<Terminal> ruta = rutas.get(i);
            System.out.print("Ruta " + (i+1) + ": ");
            for (int j = 0; j < ruta.size(); j++) {
                System.out.print(ruta.get(j).getCodigo());
                if (j < ruta.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
        }
    }

    private static void mostrarViajesPendientes() {
        System.out.println("\n-- Viajes Pendientes (Ordenados por Prioridad) --");
        PriorityQueueADT<Viaje> pendientes = sistema.getGestorViajes().getViajesPendientes();
        PriorityQueueADT<Viaje> tempQueue = new TDAs.structure.implementation.dynamic.DynamicPriorityQueueADT<>();

        if (pendientes.isEmpty()) {
            System.out.println("No hay viajes pendientes.");
            return;
        }

        while (!pendientes.isEmpty()) {
            try {
                Viaje v = pendientes.getElement();
                int p = pendientes.getPriority();
                pendientes.remove();
                System.out.println(v);
                tempQueue.add(v, p);
            } catch (EmptyADTException e) {
                break;
            }
        }

        // Restaurar
        while (!tempQueue.isEmpty()) {
            try {
                Viaje v = tempQueue.getElement();
                int p = tempQueue.getPriority();
                tempQueue.remove();
                pendientes.add(v, p);
            } catch (EmptyADTException e) {
                break;
            }
        }
    }
}
