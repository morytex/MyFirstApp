package br.com.moryta.myfirstapp.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by moryta on 24/08/2017.
 */

public class TimeUtilsTest {
    @Test
    public void format_isCorrect_singleDigits() {
        int hourOfDay = 1;
        int minute = 1;
        String formattedTime = TimeUtil.format(hourOfDay, minute);
        String expectedTime = "01:01";
        assertNotNull(formattedTime);
        assertEquals(expectedTime, formattedTime);
    }

    @Test
    public void format_isCorrect_doubleDigits() {
        int hourOfDay = 23;
        int minute = 45;
        String formattedTime = TimeUtil.format(hourOfDay, minute);
        String expectedTime = "23:45";
        assertNotNull(formattedTime);
        assertEquals(expectedTime, formattedTime);
    }

    @Test
    public void parse_isCorrect_singleDigits() {
        String time = "01:02";
        int hourOfDay = TimeUtil.parse(time, TimeUtil.HOUR_OF_DAY);
        assertNotNull(hourOfDay);
        assertEquals(1, hourOfDay);

        int minute = TimeUtil.parse(time, TimeUtil.MINUTE);
        assertNotNull(minute);
        assertEquals(2, minute);
    }

    @Test
    public void parse_isCorrect_doubleDigits() {
        String time = "11:22";
        int hourOfDay = TimeUtil.parse(time, TimeUtil.HOUR_OF_DAY);
        assertNotNull(hourOfDay);
        assertEquals(11, hourOfDay);

        int minute = TimeUtil.parse(time, TimeUtil.MINUTE);
        assertNotNull(minute);
        assertEquals(22, minute);
    }

    @Test
    public void isValidFormat_validTime() {
        String time = "11:22";
        assertTrue(TimeUtil.isValidFormat(time));
    }

    @Test
    public void isValidFormat_invalidTime() {
        String timeWithNonDigit = "a1:22";
        assertFalse("Should return false for " + timeWithNonDigit
                , TimeUtil.isValidFormat(timeWithNonDigit));

        String timeOutOfBounds = "31:22";
        assertFalse("Should return false for " + timeOutOfBounds
                , TimeUtil.isValidFormat(timeOutOfBounds));
    }
}
