package arranchamento.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class DateUtil {

    public static Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing the date: " + e.getMessage());
            return null;
        }
    }
    public static java.sql.Date convertStringToSqlDate(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  // Define o formato esperado para a string de data
            Date date = sdf.parse(strDate);  // Parseia a string para java.util.Date
            return new java.sql.Date(date.getTime());  // Converte java.util.Date para java.sql.Date e retorna
        } catch (Exception e) {
            e.printStackTrace();  // Imprime erro caso algo dê errado (string de data inválida, etc.)
            return null;  // Retorna null se houver falha na conversão
        }
    }
}
