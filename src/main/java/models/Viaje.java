package models;

public class Viaje {
    private Terminal origen;
    private Terminal destino;
    private Micro micro;
    private String fecha;
    private int prioridad;


    public Viaje(Terminal origen, Terminal destino, Micro micro, String fecha, int prioridad) {
        this.origen = origen;
        this.destino = destino;
        this.micro = micro;
        this.fecha = fecha;
        this.prioridad = prioridad;
    }

    public Terminal getOrigen() {
        return origen;
    }

    public void setOrigen(Terminal origen) {
        this.origen = origen;
    }

    public Terminal getDestino() {
        return destino;
    }

    public void setDestino(Terminal destino) {
        this.destino = destino;
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

    public void setPrioridad(int prioridad) { // Permite modificar la prioridad [cite: 375]
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Viaje: " + origen.getCodigo() + " -> " + destino.getCodigo() +
                " | Fecha: " + fecha + " | Prioridad: " + prioridad + " | Micro: " + micro.getPatente();
    }
}
