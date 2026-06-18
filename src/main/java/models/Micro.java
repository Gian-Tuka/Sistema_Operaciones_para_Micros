package models;

public class Micro {
    private String idPatente;
    private TipoMicro tipo;
    private boolean disponible;
    private int cantidadAsignaciones;

    public Micro(String idPatente, TipoMicro tipo) {
        this.idPatente = idPatente;
        this.tipo = tipo;
        this.disponible = true;
        this.cantidadAsignaciones = 0;
    }

    public String getIdPatente() {
        return idPatente;
    }

    public void setIdPatente(String idPatente) {
        this.idPatente = idPatente;
    }

    public TipoMicro getTipo() {
        return tipo;
    }

    public void setTipo(TipoMicro tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getCantidadAsignaciones() {
        return cantidadAsignaciones;
    }

    public void incrementarAsignaciones() {
        this.cantidadAsignaciones++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Micro micro = (Micro) o;
        return idPatente.equals(micro.idPatente);
    }

    @Override
    public int hashCode() {
        return idPatente.hashCode();
    }

    @Override
    public String toString() {
        return "Micro " + idPatente + " [" + tipo + "] - " + (disponible ? "Disponible" : "Asignado") + " | Asignaciones: " + cantidadAsignaciones;
    }
}
