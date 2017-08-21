package br.com.moryta.myfirstapp.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 21/08/2017.
 */

public class DateUtil {
    private static final String FORMAT = "dd/MM/yyyy";
    private static final String PATTERN = "\\d\\d/\\d\\d/\\d\\d\\d\\d";
    private static SimpleDateFormat sdf = new SimpleDateFormat(FORMAT, Locale.getDefault());
    private static Pattern pattern = Pattern.compile(PATTERN);

    /**
     * Format Date to String in dd/MM/yyyy format
     * @param date
     * @return string of date in dd/MM/yyyy format
     */
    public static String format(@NonNull Date date) throws NullPointerException {
        checkNotNull(date, "date must not be null!");

        return sdf.format(date);
    }

    /**
     * Parse String in dd/MM/yyyy format to Date
     * @param date
     * @return
     */
    public static Date parse(String date) throws NullPointerException, ParseException {
        checkNotNull(date, "date must not be null!");

        if (!isValidFormat(date)) {
            throw new ParseException(
                    String.format("invalid date format: %s. Expecting %s format", date, FORMAT)
                    , 0);
        }

        return sdf.parse(date);
    }

    /**
     * Validate string date format
     * @param date
     * @return
     */
    public static Boolean isValidFormat(String date) {
        return pattern.matcher(date).matches();
    }
}
