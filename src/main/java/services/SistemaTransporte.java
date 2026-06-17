package services;

import models.Micro;
import models.Terminal;
import models.Viaje;
import exception.MicroNoDisponibleException;

public class SistemaTransporte {

    private PlanificacionRutas planificacionRutas;
    private GestionFlota gestionFlota;
    private GestorViajes gestorViajes;
    private AnalisisConexiones analisisConexiones;

    public SistemaTransporte() {
        this.planificacionRutas = new PlanificacionRutas();
        this.gestionFlota = new GestionFlota();
        this.gestorViajes = new GestorViajes();
        this.analisisConexiones = new AnalisisConexiones(planificacionRutas, gestorViajes);
    }

    public PlanificacionRutas getPlanificacionRutas() {
        return planificacionRutas;
    }

    public GestionFlota getGestionFlota() {
        return gestionFlota;
    }

    public GestorViajes getGestorViajes() {
        return gestorViajes;
    }

    public AnalisisConexiones getAnalisisConexiones() {
        return analisisConexiones;
    }

    // Facade Methods

    public void registrarTerminal(Terminal t) {
        planificacionRutas.agregarTerminal(t);
    }

    public void registrarRuta(Terminal origen, Terminal destino) {
        planificacionRutas.agregarConexion(origen, destino);
    }

    public void registrarMicro(Micro m) {
        gestionFlota.agregarMicro(m);
    }

    public void crearViaje(Terminal origen, Terminal destino, String patenteMicro, String fecha, int prioridad) throws MicroNoDisponibleException {
        Micro m = gestionFlota.obtenerMicro(patenteMicro);
        if (m == null || !m.isDisponible()) {
            throw new MicroNoDisponibleException(patenteMicro, fecha);
        }
        
        Viaje nuevoViaje = new Viaje(origen, destino, m, fecha, prioridad);
        gestorViajes.registrarViaje(nuevoViaje);
        gestionFlota.registrarAsignacion(patenteMicro);
    }

    public boolean reprogramarViaje(Viaje v, String nuevaFecha, String nuevaPatenteMicro, int nuevaPrioridad) throws MicroNoDisponibleException {
        Micro nuevoMicro = gestionFlota.obtenerMicro(nuevaPatenteMicro);
        if (nuevoMicro == null || !nuevoMicro.isDisponible()) {
             throw new MicroNoDisponibleException(nuevaPatenteMicro, nuevaFecha);
        }
        
        boolean modificado = gestorViajes.reprogramarViaje(v, nuevaFecha, nuevoMicro, nuevaPrioridad);
        if (modificado) {
            gestionFlota.registrarAsignacion(nuevaPatenteMicro);
        }
        return modificado;
    }

    public void mostrarUtilizacionPromedio() {
        int totalViajes = gestorViajes.getHistorialViajes().size();
        int totalMicros = gestionFlota.getFlota().size();

        if (totalMicros == 0) {
            System.out.println("No hay micros registrados.");
            return;
        }

        double promedio = (double) totalViajes / totalMicros;
        System.out.println("Utilización promedio de la flota: " + String.format("%.2f", promedio) + " viajes por micro.");
    }
}
