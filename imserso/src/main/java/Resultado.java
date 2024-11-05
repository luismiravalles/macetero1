import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class Resultado {

    private String fecha;
    private String destino;
    private String dias;
    private String estado;
    private String hotel;
    private String precio;
    private String transporte;
    private String zona;

    public String toHtmlTable() {
        return
            "<tr><td>Fecha</td><td>" + fecha + "</td></tr>"
            + "<tr><td>Destino</td><td>" + destino + "</td></tr>"
            + "<tr><td>dias</td><td>" + dias + "</td></tr>"
            + "<tr><td>Estado</td><td>" + estado + "</td></tr>"
            + "<tr><td>Hotel</td><td>" + hotel + "</td></tr>"
            + "<tr><td>Precio</td><td>" + precio + "</td></tr>"
            + "<tr><td>Transporte</td><td>" + transporte + "</td></tr>"
            + "<tr><td>Zona</td><td>" + zona + "</td></tr>"
        ;
    }

    public boolean conTransporte() {
        return StringUtils.startsWith(transporte, "S");
    }

    public boolean esTenerife() {
        return StringUtils.containsIgnoreCase(destino, "Tenerife") ||
               StringUtils.containsIgnoreCase(zona, "Tenerife") ||
               StringUtils.containsIgnoreCase(zona, "Canarias")
               ;
    }

    public boolean esNavidad() {
        return StringUtils.startsWith(fecha, "20/12")
            || StringUtils.startsWith(fecha, "21/12")
            || StringUtils.startsWith(fecha, "22/12")
            || StringUtils.startsWith(fecha, "23/12")
            || StringUtils.startsWith(fecha, "24/12")
            || StringUtils.startsWith(fecha, "25/12")
            || StringUtils.startsWith(fecha, "26/12")
            || StringUtils.startsWith(fecha, "27/12")
            || StringUtils.startsWith(fecha, "28/12");
    }

    public boolean esPrimavera() {
        return StringUtils.contains(fecha, "/03/")
                || StringUtils.contains(fecha, "/04/")
                || StringUtils.contains(fecha, "/05/");
    }
}
