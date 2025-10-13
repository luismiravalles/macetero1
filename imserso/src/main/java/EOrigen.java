public enum EOrigen {

    OVIEDO("QOC_ORI", "Oviedo"),
    CANTABRIA("SDR_ORI", "Santander"),

    OVIEDO_ISLAS("OVD", "Oviedo"),
    CANTABRIA_ISLAS("SDR", "Santander");


    private String codigo;
    private String nombre;

    public final static String ID="origin-scheduler";

    EOrigen(String codigo, String nombre) {
        this.codigo=codigo;
        this.nombre=nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    public String toString() {
        return codigo + ":" + nombre;
    }
}
