package controllers;

import TDAs.structure.definition.LinkedListADT;
import models.Micro;
import models.Terminal;
import models.TipoMicro;
import models.Viaje;

import java.util.Scanner;

public class ConsoleMenu {

    private TerminalGestor terminalGestor;
    private MicroGestor microGestor;
    private ViajeGestor viajeGestor;
    private GrafoRutas grafoRutas;
    private Scanner scanner;

    public ConsoleMenu(TerminalGestor terminalGestor, MicroGestor microGestor, ViajeGestor viajeGestor) {
        this.terminalGestor = terminalGestor;
        this.microGestor = microGestor;
        this.viajeGestor = viajeGestor;
        this.grafoRutas = GrafoRutas.getInstance();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU GENERAL ---");
            System.out.println("1 - Viajes");
            System.out.println("2 - Micros");
            System.out.println("3 - Terminales");
            System.out.println("4 - Rutas");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    menuViajes();
                    break;
                case "2":
                    menuMicros();
                    break;
                case "3":
                    menuTerminales();
                    break;
                case "4":
                    menuRutas();
                    break;
                case "0":
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuViajes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU VIAJES ---");
            System.out.println("1- Ver viajes (historial)");
            System.out.println("2- Agregar viaje");
            System.out.println("3- Reprogramar viaje");
            System.out.println("4- Re-priorizar viaje");
            System.out.println("0- Volver");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n[Historial de Todos los Viajes]");
                    imprimirLista(viajeGestor.listarViajes());
                    break;
                case "2":
                    // Validar terminal origen
                    Terminal origen = null;
                    while (origen == null) {
                        System.out.print("Codigo Origen: ");
                        String codOrigen = scanner.nextLine().trim();
                        if (terminalGestor.existeTerminal(codOrigen)) {
                            origen = terminalGestor.obtenerTerminal(codOrigen);
                        } else {
                            System.out.println("Error: Terminal origen no existe.");
                        }
                    }

                    // Validar terminal destino
                    Terminal destino = null;
                    while (destino == null) {
                        System.out.print("Codigo Destino: ");
                        String codDestino = scanner.nextLine().trim();
                        if (terminalGestor.existeTerminal(codDestino)) {
                            destino = terminalGestor.obtenerTerminal(codDestino);
                        } else {
                            System.out.println("Error: Terminal destino no existe.");
                        }
                    }

                    // Validar micro (opcional)
                    Micro m = null;
                    System.out.print("Patente Micro Asignado (opcional, enter para saltar): ");
                    String pat = scanner.nextLine().trim();
                    if (!pat.isEmpty()) {
                        if (microGestor.existeMicro(pat)) {
                            m = microGestor.obtenerMicro(pat);
                        } else {
                            System.out.println("Error: Micro con patente " + pat + " no existe.");
                            break;
                        }
                    }

                    // Validar fecha (no vacía)
                    System.out.print("Fecha: ");
                    String fecha = scanner.nextLine().trim();
                    if (fecha.isEmpty()) {
                        System.out.println("Error: La fecha no puede estar vacía.");
                        break;
                    }

                    // Validar prioridad
                    int prio = 0;
                    boolean prioridadValida = false;
                    while (!prioridadValida) {
                        System.out.print("Prioridad (entero): ");
                        String prioStr = scanner.nextLine().trim();
                        if (viajeGestor.validarPrioridad(prioStr)) {
                            prio = Integer.parseInt(prioStr);
                            prioridadValida = true;
                        } else {
                            System.out.println("Error: La prioridad debe ser un número entero válido.");
                        }
                    }

                    // Si todo es válido, crear el viaje
                    try {
                        Viaje v = new Viaje(viajeGestor.generarID(), origen, destino, m, fecha, prio);
                        viajeGestor.agregarViaje(v);
                        if (m != null) microGestor.marcarMicroAsignado(pat);
                        System.out.println("Viaje agregado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al agregar viaje: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("ID Viaje a reprogramar : ");
                    String idViaje = scanner.nextLine();
                    System.out.print("Nueva Fecha: ");
                    String nFecha = scanner.nextLine();
                    System.out.print("Nueva Patente Micro (opcional): ");
                    String nPat = scanner.nextLine();
                    Micro nM = nPat.isEmpty() ? null : microGestor.obtenerMicro(nPat);
                    try {
                        viajeGestor.reprogramarViaje(idViaje, nFecha, nM);
                        if (nM != null) microGestor.marcarMicroAsignado(nPat);
                        System.out.println("Viaje reprogramado.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("ID Viaje a repriorizar: ");
                    String idV = scanner.nextLine();
                    System.out.print("Nueva Prioridad: ");
                    int nPrio = Integer.parseInt(scanner.nextLine());
                    try {
                        viajeGestor.rePriorizarViaje(idV, nPrio);
                        System.out.println("Prioridad actualizada.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuMicros() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU MICROS ---");
            System.out.println("1- Ver micros");
            System.out.println("2- Agregar micro");
            System.out.println("3- Editar micro (Disponibilidad)");
            System.out.println("4- Eliminar micro");
            System.out.println("5- Reporte | Promedio Utilización micros (Disponibles vs Total)");
            System.out.println("6- Reporte | Asignaciones Micros (Mayor cantidad)");
            System.out.println("0- Volver");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    imprimirLista(microGestor.listarMicros());
                    break;
                case "2":
                    System.out.print("Patente de miecros: ");
                    String pat = scanner.nextLine();
                    System.out.print("Tipo (EJECUTIVO, SEMI_CAMA, CAMA): ");
                    TipoMicro tipo = TipoMicro.valueOf(scanner.nextLine().toUpperCase());
                    microGestor.agregarMicro(new Micro(pat, tipo));
                    System.out.println("Micro agregado.");
                    break;
                case "3":
                    System.out.print("Patente a editar: ");
                    String pEdit = scanner.nextLine();
                    System.out.print("Disponible? (true/false): ");
                    boolean disp = Boolean.parseBoolean(scanner.nextLine());
                    Micro mEdit = microGestor.obtenerMicro(pEdit);
                    if (mEdit != null) {
                        mEdit.setDisponible(disp);
                        System.out.println("Editado.");
                    } else {
                        System.out.println("No encontrado.");
                    }
                    break;
                case "4":
                    System.out.print("Patente a eliminar: ");
                    String pdelete = scanner.nextLine();
                    Micro mdelete = microGestor.obtenerMicro(pdelete);
                    if (mdelete != null) {
                        microGestor.eliminarMicro(mdelete.getIdPatente());

                    }
                    System.out.println("Proceso finalizado.");
                    break;
                case "5":
                    int total = microGestor.listarMicros().size();
                    int disponibles = microGestor.listarMicrosDisponibles().size();
                    System.out.println("Micros Totales: " + total);
                    System.out.println("Micros Disponibles: " + disponibles);
                    if (total > 0) {
                        System.out.println("Porcentaje Utilización (Asignados): " + ((total - disponibles) * 100 / total) + "%");
                    }
                    break;
                case "6":
                    System.out.println("Micros con mayor cantidad de asignaciones:");
                    imprimirLista(microGestor.obtenerMicrosMayorAsignacion());
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuTerminales() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU TERMINALES ---");
            System.out.println("1- Ver terminales");
            System.out.println("2- Agregar terminales");
            System.out.println("3- Eliminar terminales");
            System.out.println("4- Reporte | Conexiones directas");
            System.out.println("5- Reporte | Conexiones generales (Por cantidad de viajes)");
            System.out.println("0- Volver");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    imprimirLista(terminalGestor.listarTerminales());
                    break;
                case "2":
                    System.out.print("Código: ");
                    String cod = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String desc = scanner.nextLine();
                    terminalGestor.agregarTerminal(new Terminal(cod, desc));
                    System.out.println("Agregada.");
                    break;
                case "3":
                    System.out.print("Código a eliminar: ");
                    try {
                        terminalGestor.eliminarTerminal(scanner.nextLine());
                        System.out.println("Eliminada.");
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("Código Terminal para ver conexiones directas: ");
                    Terminal t = terminalGestor.obtenerTerminal(scanner.nextLine());
                    if (t != null) {
                        LinkedListADT<Terminal> todas = terminalGestor.listarTerminales();
                        System.out.println("Conexiones directas desde " + t.getCodigo() + ":");
                        for (int i = 0; i < todas.size(); i++) {
                            if (grafoRutas.existeRuta(t, todas.get(i))) {
                                System.out.println("- " + todas.get(i).getCodigo());
                            }
                        }
                    }
                    break;
                case "5":
                    System.out.println("Este reporte iteraría sobre todos los viajes para contar origen/destino.");
                    System.out.println("Actualmente hay " + viajeGestor.listarViajes().size() + " viajes registrados.");
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuRutas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU RUTAS ---");
            System.out.println("1- Ver Ruta (Buscar rutas posibles)");
            System.out.println("2- Crear Ruta (Conectar terminales)");
            System.out.println("3- Eliminar Ruta");
            System.out.println("4- Reporte | Rutas mas y menos utilizadas");
            System.out.println("5- Reporte | Rutas no utilizadas (Terminales desconectadas)");
            System.out.println("0- Volver");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Origen: ");
                    Terminal o = terminalGestor.obtenerTerminal(scanner.nextLine());
                    System.out.print("Destino: ");
                    Terminal d = terminalGestor.obtenerTerminal(scanner.nextLine());
                    System.out.print("Máximo paradas intermedias: ");
                    int maxP = Integer.parseInt(scanner.nextLine());
                    if (o != null && d != null) {
                        LinkedListADT<LinkedListADT<Terminal>> rutas = grafoRutas.obtenerRutasPosibles(o, d, maxP, terminalGestor.listarTerminales());
                        for (int i = 0; i < rutas.size(); i++) {
                            LinkedListADT<Terminal> ruta = rutas.get(i);
                            System.out.print("Ruta " + (i+1) + ": ");
                            for (int j = 0; j < ruta.size(); j++) {
                                System.out.print(ruta.get(j).getCodigo() + (j < ruta.size() - 1 ? " -> " : ""));
                            }
                            System.out.println();
                        }
                        if (rutas.size() == 0) System.out.println("No hay rutas posibles.");
                    }
                    break;
                case "2":
                    System.out.print("Origen: ");
                    Terminal oCrear = terminalGestor.obtenerTerminal(scanner.nextLine());
                    System.out.print("Destino: ");
                    Terminal dCrear = terminalGestor.obtenerTerminal(scanner.nextLine());
                    System.out.print("Distancia (peso): ");
                    int dist = Integer.parseInt(scanner.nextLine());
                    try {
                        grafoRutas.conectarTerminales(oCrear, dCrear, dist);
                        System.out.println("Conectadas.");
                    } catch(Exception e) { System.out.println(e.getMessage()); }
                    break;
                case "3":
                    System.out.print("Origen: ");
                    Terminal oElim = terminalGestor.obtenerTerminal(scanner.nextLine());
                    System.out.print("Destino: ");
                    Terminal dElim = terminalGestor.obtenerTerminal(scanner.nextLine());
                    try {
                        grafoRutas.eliminarConexion(oElim, dElim);
                        System.out.println("Ruta eliminada.");
                    } catch(Exception e) { System.out.println(e.getMessage()); }
                    break;
                case "4":
                    System.out.println("Reporte de rutas mas/menos utilizadas no implementado al 100%, se calcula en base a Viajes.");
                    break;
                case "5":
                    LinkedListADT<Terminal> desc = grafoRutas.obtenerTerminalesDesconectadas(terminalGestor.listarTerminales());
                    System.out.println("Terminales sin ninguna conexión:");
                    imprimirLista(desc);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private <T> void imprimirLista(LinkedListADT<T> lista) {
        if (lista.size() == 0) {
            System.out.println("No hay elementos.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("- " + lista.get(i).toString());
        }
    }
}
