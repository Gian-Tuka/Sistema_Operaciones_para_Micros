package models;

public class Micro {
    private String patente;     // Identificador único (patente o interno)
    private String tipo;        // Ejecutivo, Semi-cama, Cama [cite: 366]
    private boolean disponible; // Control de disponibilidad

    public Micro(String patente, String tipo) {
        this.patente = patente;
        this.tipo = tipo;
        this.disponible = true; // Por defecto todo micro nuevo inicia disponible
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Micro micro = (Micro) obj;
        return patente != null && patente.equalsIgnoreCase(micro.patente);
    }

    @Override
    public int hashCode() {
        return patente != null ? patente.toLowerCase().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Micro Patente: " + patente + " (" + tipo + ")";
    }
}
