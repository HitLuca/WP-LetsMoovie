package json.adminData;

import types.annotations.toSanitize;
import utilities.InputValidator.Regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by etrunon on 22/07/15.
 */
public class DateRequest {
    private LocalDate date;
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @toSanitize(name = "date", reg = Regex.DATE)
    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        return date.format(dateFormat);
    }
}
