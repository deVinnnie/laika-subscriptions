package be.mira.jongeren.mailinglist.util.date;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class DateTimeFormatterHelperTest {

    @Test
    public void shouldReturnDateInCorrectFormat(){
        DateTimeFormatterHelper dateTimeFormatterHelper = new DateTimeFormatterHelper();
        String formatted = dateTimeFormatterHelper.format(LocalDateTime.of(2017, 03, 05, 10, 55), "YYYY-MM-dd HH:mm");
        assertEquals("2017-03-05 10:55", formatted);
    }
}
