package models;

public class Terminal {
    private String codigo;      // Identificador único (ej: "BUE", "COR")
    private String descripcion; // Descripción de la terminal [cite: 357]

    public Terminal(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Terminal terminal = (Terminal) obj;
        return codigo != null && codigo.equalsIgnoreCase(terminal.codigo);
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.toLowerCase().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + descripcion;
    }
}
