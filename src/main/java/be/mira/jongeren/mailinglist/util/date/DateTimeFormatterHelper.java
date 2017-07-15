package be.mira.jongeren.mailinglist.util.date;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterHelper {

    public String format(TemporalAccessor temporal, String pattern){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(temporal);
    }
}
