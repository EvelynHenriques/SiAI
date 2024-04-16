package arranchamento.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class DateUtil {

    /**
     * Converts a date string from the format dd-MM-yyyy to a java.util.Date object.
     *
     * @param dateStr the date string in the format dd-MM-yyyy
     * @return the java.util.Date object or null if there is a parsing error
     */
    public static Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing the date: " + e.getMessage());
            return null;
        }
    }
}