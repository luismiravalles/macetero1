public enum EZona {

    COSTAS("COS", "Costas"),
    CIRCUITOS_CULTURALES("CC", "Circuitos Culturales"),
    CANARIAS("ISL_ZONCAN_ISL", "Canarias"),
    BALEARES("ISL_ZONBAL_ISL", "Baleares");

    private String codigo;
    private String nombre;

    public final static String ID="destination-scheduler";

    EZona(String codigo, String nombre) {
        this.codigo=codigo;
        this.nombre=nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    public String toString() {
        return codigo + ":" + nombre;
    }
}
