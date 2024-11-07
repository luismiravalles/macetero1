import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.Test;

public class TestDiaSemana {


    public static String diaSemana(String fecha) {
                // Definir el formato de la fecha
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
                // Convertir la cadena a LocalDate
                LocalDate localDate = LocalDate.parse(fecha, formatter);
                
                // Obtener el día de la semana
                DayOfWeek dayOfWeek = localDate.getDayOfWeek();

                return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ES"));
    }

    @Test 
    public static void main(String args[]) {
        System.out.println(diaSemana("01/11/2024"));
    }

}
