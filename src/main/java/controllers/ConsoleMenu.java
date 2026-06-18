package controllers;

import TDAs.structure.definition.LinkedListADT;
import models.Micro;
import models.Terminal;
import models.TipoMicro;
import models.Viaje;
import exception.*;

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
                    try {
                        // Origen
                        Terminal origen = null;
                        while (origen == null) {
                            System.out.print("Codigo Origen: ");
                            String codOrigen = scanner.nextLine().trim();
                            if (codOrigen.isEmpty()) {
                                System.out.println("Error: Codigo de origen no puede estar vacío.");
                                continue;
                            }
                            if (terminalGestor.existeTerminal(codOrigen)) {
                                origen = terminalGestor.obtenerTerminal(codOrigen);
                            } else {
                                System.out.println("Error: Terminal origen no existe.");
                            }
                        }

                        // Destino
                        Terminal destino = null;
                        while (destino == null) {
                            System.out.print("Codigo Destino: ");
                            String codDestino = scanner.nextLine().trim();
                            if (codDestino.isEmpty()) {
                                System.out.println("Error: Codigo de destino no puede estar vacío.");
                                continue;
                            }
                            if (terminalGestor.existeTerminal(codDestino)) {
                                destino = terminalGestor.obtenerTerminal(codDestino);
                            } else {
                                System.out.println("Error: Terminal destino no existe.");
                            }
                        }

                        // Patente micro (opcional). Si se ingresa una patente nueva, ofrecer crear el micro.
                        Micro m = null;
                        System.out.print("Patente Micro Asignado (opcional, enter para saltar): ");
                        String pat = scanner.nextLine().trim().toUpperCase();
                        if (!pat.isEmpty()) {
                            if (microGestor.existeMicro(pat)) {
                                m = microGestor.obtenerMicro(pat);
                            } else {
                                System.out.println("Micro con patente " + pat + " no existe.");
                                System.out.print("Desea crearlo? (s/n): ");
                                String r = scanner.nextLine().trim().toLowerCase();
                                if (r.equals("s") || r.equals("y")) {
                                    if (!validarFormatoPatente(pat)) {
                                        System.out.println("Patente con formato inválido. Use AA-NNN-AA (ej: AB-123-CD). Se le pedirá una nueva patente.");
                                        pat = solicitarPatenteMicro("Patente del micro (formato AA-NNN-AA): ");
                                    }
                                    TipoMicro tipo = solicitarTipoMicro();
                                    microGestor.agregarMicro(new Micro(pat, tipo));
                                    m = microGestor.obtenerMicro(pat);
                                    System.out.println("Micro creado y asignado: " + pat);
                                } else {
                                    System.out.println("No se asignará micro.");
                                }
                            }
                        }

                        // Fecha
                        System.out.print("Fecha: ");
                        String fecha = scanner.nextLine().trim();
                        if (fecha.isEmpty()) {
                            throw new InvalidInputException("La fecha no puede estar vacía");
                        }

                        // Prioridad
                        int prio = 0;
                        while (true) {
                            System.out.print("Prioridad (entero): ");
                            String prioStr = scanner.nextLine().trim();
                            if (!viajeGestor.validarPrioridad(prioStr)) {
                                System.out.println("Error: La prioridad debe ser un número entero válido y no puede estar vacía.");
                                continue;
                            }
                            prio = Integer.parseInt(prioStr);
                            break;
                        }

                        // Crear viaje
                        Viaje v = new Viaje(viajeGestor.generarID(), origen, destino, m, fecha, prio);
                        viajeGestor.agregarViaje(v);
                        if (m != null) microGestor.marcarMicroAsignado(m.getIdPatente());
                        System.out.println("Viaje agregado correctamente.");
                    } catch (DuplicateMicroException | DuplicateTerminalException | InvalidInputException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error al agregar viaje: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        System.out.print("ID Viaje a reprogramar: ");
                        String idViaje = scanner.nextLine().trim();
                        if (idViaje.isEmpty()) throw new InvalidInputException("ID de viaje no puede estar vacío");

                        System.out.print("Nueva Fecha: ");
                        String nFecha = scanner.nextLine().trim();
                        if (nFecha.isEmpty()) throw new InvalidInputException("La fecha no puede estar vacía");

                        System.out.print("Nueva Patente Micro (opcional): ");
                        String nPat = scanner.nextLine().trim().toUpperCase();
                        Micro nM = null;
                        if (!nPat.isEmpty()) {
                            if (microGestor.existeMicro(nPat)) {
                                nM = microGestor.obtenerMicro(nPat);
                            } else {
                                System.out.println("Micro con patente " + nPat + " no existe.");
                                System.out.print("Desea crearlo? (s/n): ");
                                String r = scanner.nextLine().trim().toLowerCase();
                                if (r.equals("s") || r.equals("y")) {
                                    if (!validarFormatoPatente(nPat)) {
                                        nPat = solicitarPatenteMicro("Patente del micro (formato AA-NNN-AA): ");
                                    }
                                    TipoMicro tipo = solicitarTipoMicro();
                                    microGestor.agregarMicro(new Micro(nPat, tipo));
                                    nM = microGestor.obtenerMicro(nPat);
                                } else {
                                    System.out.println("No se asignará micro.");
                                }
                            }
                        }

                        viajeGestor.reprogramarViaje(idViaje, nFecha, nM);
                        if (nM != null) microGestor.marcarMicroAsignado(nM.getIdPatente());
                        System.out.println("Viaje reprogramado.");
                    } catch (ViajeNoEncontradoException | InvalidInputException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }
                    break;
                case "4":
                    try {
                        System.out.print("ID Viaje a repriorizar: ");
                        String idV = scanner.nextLine().trim();
                        if (idV.isEmpty()) throw new InvalidInputException("ID de viaje no puede estar vacío");

                        System.out.print("Nueva Prioridad: ");
                        String nPrioStr = scanner.nextLine().trim();
                        if (!viajeGestor.validarPrioridad(nPrioStr)) {
                            throw new InvalidPriorityException("Prioridad inválida");
                        }
                        int nPrio = Integer.parseInt(nPrioStr);

                        viajeGestor.rePriorizarViaje(idV, nPrio);
                        System.out.println("Prioridad actualizada.");
                    } catch (ViajeNoEncontradoException | InvalidInputException | InvalidPriorityException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
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
                    try {
                        String pat = solicitarPatenteMicro("Patente del micro (formato AA-NNN-AA): ");
                        TipoMicro tipo = solicitarTipoMicro();
                        microGestor.agregarMicro(new Micro(pat, tipo));
                        System.out.println("Micro agregado.");
                    } catch (DuplicateMicroException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }
                    break;
                case "3":
                    String pEdit = solicitarPatenteMicro("Patente a editar (formato AA-NNN-AA): ");
                    boolean disp = solicitarBooleano("Disponible? (true/false): ");
                    Micro mEdit = microGestor.obtenerMicro(pEdit);
                    if (mEdit != null) {
                        mEdit.setDisponible(disp);
                        System.out.println("Editado.");
                    } else {
                        System.out.println("No encontrado.");
                    }
                    break;
                case "4":
                    try {
                        String pdelete = solicitarPatenteMicro("Patente a eliminar (formato AA-NNN-AA): ");
                        microGestor.eliminarMicro(pdelete);
                        System.out.println("Micro eliminado.");
                    } catch (MicroNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }
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
                    try {
                        System.out.print("Código: ");
                        String cod = scanner.nextLine().trim();
                        System.out.print("Descripción: ");
                        String desc = scanner.nextLine().trim();
                        if (cod.isEmpty() || desc.isEmpty()) {
                            throw new InvalidInputException("Código y descripción no pueden estar vacíos");
                        }
                        terminalGestor.agregarTerminal(new Terminal(cod, desc));
                        System.out.println("Agregada.");
                    } catch (DuplicateTerminalException | InvalidInputException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("Código a eliminar: ");
                    try {
                        String codigoEliminar = scanner.nextLine().trim();
                        terminalGestor.eliminarTerminal(codigoEliminar);
                        System.out.println("Eliminada.");
                    } catch (TerminalNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
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
                        if (rutas.isEmpty()) System.out.println("No hay rutas posibles.");
                    }
                    break;
                case "2":
                    try {
                        System.out.print("Origen: ");
                        String oCod = scanner.nextLine().trim();
                        System.out.print("Destino: ");
                        String dCod = scanner.nextLine().trim();
                        if (!terminalGestor.existeTerminal(oCod) || !terminalGestor.existeTerminal(dCod)) {
                            throw new TerminalNotFoundException("Ambas terminales deben existir para crear la ruta");
                        }
                        Terminal oCrear = terminalGestor.obtenerTerminal(oCod);
                        Terminal dCrear = terminalGestor.obtenerTerminal(dCod);

                        System.out.print("Distancia (peso): ");
                        String distStr = scanner.nextLine().trim();
                        int dist;
                        try {
                            dist = Integer.parseInt(distStr);
                        } catch (NumberFormatException nfe) {
                            throw new InvalidInputException("Distancia inválida");
                        }

                        grafoRutas.conectarTerminales(oCrear, dCrear, dist);
                        System.out.println("Conectadas.");
                    } catch (TerminalNotFoundException | InvalidInputException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        System.out.print("Origen: ");
                        String oCod = scanner.nextLine().trim();
                        System.out.print("Destino: ");
                        String dCod = scanner.nextLine().trim();
                        if (!terminalGestor.existeTerminal(oCod) || !terminalGestor.existeTerminal(dCod)) {
                            throw new TerminalNotFoundException("Ambas terminales deben existir para eliminar la ruta");
                        }
                        Terminal oElim = terminalGestor.obtenerTerminal(oCod);
                        Terminal dElim = terminalGestor.obtenerTerminal(dCod);
                        grafoRutas.eliminarConexion(oElim, dElim);
                        System.out.println("Ruta eliminada.");
                    } catch (TerminalNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }
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
        if (lista.isEmpty()) {
            System.out.println("No hay elementos.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("- " + lista.get(i).toString());
        }
    }

    private String solicitarPatenteMicro(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String patente = scanner.nextLine().toUpperCase();
            if (validarFormatoPatente(patente)) {
                return patente;
            }
            System.out.println("Formato inválido. Debe ser AA-NNN-AA (ej: AB-123-CD).");
        }
    }

    private boolean validarFormatoPatente(String patente) {
        return patente.matches("^[A-Z]{2}-\\d{3}-[A-Z]{2}$");
    }

    private TipoMicro solicitarTipoMicro() {
        while (true) {
            System.out.println("Tipo:");
            System.out.println("1- EJECUTIVO");
            System.out.println("2- SEMI_CAMA");
            System.out.println("3- CAMA");
            System.out.print("Seleccione una opción: ");

            switch (scanner.nextLine()) {
                case "1":
                    return TipoMicro.EJECUTIVO;
                case "2":
                    return TipoMicro.SEMI_CAMA;
                case "3":
                    return TipoMicro.CAMA;
                default:
                    System.out.println("Opción inválida. Ingrese 1, 2 o 3.");
            }
        }
    }

    private boolean solicitarBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim().toLowerCase();
            if (valor.equals("true")) {
                return true;
            }
            if (valor.equals("false")) {
                return false;
            }
            System.out.println("Entrada inválida. Ingrese true o false.");
        }
    }
}
