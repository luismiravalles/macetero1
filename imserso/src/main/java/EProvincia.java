import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EProvincia {

    ALICANTE("ALICANTE_COS", "Alicante"),
    ALMERIA("ALMERIA_COS", "Almería"),
    CADIZ("CADIZ_COS", "Cádiz"),
    BARCELONA("BARCELONA_COS", "Barcelona"),
    CASTELLON("CASTELLON_COS", "Castellón"),
    GIRONA("GIRONA_COS", "Girona"),
    HUELVA("HUELVA_COS", "Huelva"),
    MALAGA("MALAGA_COS", "Málaga"),
    MURCIA("MURCIA_COS", "Murcia"),
    TARRAGONA("TARRAGONA_COS", "Tarragona"),
    VALENCIA("VALENCIA_COS", "Valencia"),

    FUERTEVENTURA("ISL_FUE_ISL", "Fuerteventura"),
    GRAN_CANARIA("ISL_LPA_ISL", "Gran Canaria"),
    TENERIFE("ISL_TEN_ISL", "Tenerife"),

    MALLORCA("ISL_MAL_ISL", "Mallorca"),

    CANTABRIA_CP("CANTABRIA_CP", "Santander"),
    ORENSE_CP("ORENSE_CP", "Orense"),
    NAVARRA_CP("NAVARRA_CP", "Navarra"),
    CORUNA_CP("CORUNA_CP", "Coruña"),



    CANTABRIA("CANTABRIA_CC", "Cantabria");

    private final String codigo;
    private final String  nombre;

    public final static String ID="province-scheduler";

    EProvincia(String codigo, String nombre) {
        this.codigo=codigo;
        this.nombre=nombre;
    }

    public String getCodigo() { return codigo; };
    public String getNombre() { return nombre; }

    public String toString() {
        return codigo + ":" + nombre;
    }

    public static List<EProvincia> costas() {
        return Arrays.stream(EProvincia.values()).filter(provincia -> provincia.getCodigo().contains("_COS"))
            .collect(Collectors.toList());
    }

    public static List<EProvincia> canarias() {
        return Arrays.asList(EProvincia.FUERTEVENTURA, EProvincia.GRAN_CANARIA, EProvincia.TENERIFE);
    }

    public static List<EProvincia> baleares() {
        return Arrays.asList(EProvincia.MALLORCA);
    }

    public static List<EProvincia> capitales() {
        return Arrays.stream(EProvincia.values()).filter(provincia -> provincia.getCodigo().contains("_CP"))
            .collect(Collectors.toList());
    }


}
