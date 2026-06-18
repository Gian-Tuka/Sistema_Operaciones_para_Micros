package models;

public class Viaje {
    private String idViaje;
    private Terminal origen;
    private Terminal destino;
    private Micro micro;
    private String fecha;
    private int prioridad;

    public Viaje(String idViaje, Terminal origen, Terminal destino, Micro micro, String fecha, int prioridad) {
        this.idViaje = idViaje;
        this.origen = origen;
        this.destino = destino;
        this.micro = micro;
        this.fecha = fecha;
        this.prioridad = prioridad;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public Terminal getOrigen() {
        return origen;
    }

    public Terminal getDestino() {
        return destino;
    }

    public Micro getMicro() {
        return micro;
    }

    public void setMicro(Micro micro) {
        this.micro = micro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Viaje [" + idViaje + "] - " + fecha + " | Prioridad: " + prioridad + "\n" +
               "Ruta: " + origen.getCodigo() + " -> " + destino.getCodigo() + "\n" +
               "Micro: " + (micro != null ? micro.getIdPatente() : "No asignado");
    }
}
