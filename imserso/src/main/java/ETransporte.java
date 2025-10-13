public enum ETransporte {
    SI("yes", "Con Transporte"),
    NO("no", "Sin Transporte");

    private String codigo;
    private String nombre;

    public final static String ID="transport-scheduler";

    ETransporte(String codigo, String nombre) {
        this.codigo=codigo;
        this.nombre=nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    public String toString() {
        return codigo + ":" + nombre;
    }
}
