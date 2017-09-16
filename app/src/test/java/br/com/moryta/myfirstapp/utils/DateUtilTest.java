package br.com.moryta.myfirstapp.utils;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by moryta on 21/08/2017.
 */

public class DateUtilTest {
    @Test
    public void format_isCorrect() {
        String formattedDate = DateUtil.format(new Date());

        assertNotNull(formattedDate);
    }

    @Test(expected = NullPointerException.class)
    public void format_nullDate() {
        String formattedDate = DateUtil.format(null);
    }

    @Test
    public void parse_isCorrect() throws NullPointerException, ParseException {
        Date parsedDate = parsedDate = DateUtil.parse("23/05/1986");
        assertNotNull(parsedDate);
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullDate() throws NullPointerException, ParseException {
        Date parsedDate = parsedDate = DateUtil.parse(null);
    }

    @Test(expected = ParseException.class)
    public void parse_wrongFormatDate() throws NullPointerException, ParseException {
        Date parsedDate = parsedDate = DateUtil.parse("1986/05/23");
    }
}
