package br.com.moryta.myfirstapp.utils;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private static Date getCurrentDate() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTime();
    }

    /**
     * Format Date to String in dd/MM/yyyy format
     * @param date
     * @return string of date in dd/MM/yyyy format
     * @throws NullPointerException
     */
    public static String format(@NonNull Date date) throws NullPointerException {
        checkNotNull(date, "date must not be null!");

        return sdf.format(date);
    }

    /**
     * Parse String in dd/MM/yyyy format to Date
     * @param date
     * @return
     * @throws NullPointerException
     * @throws ParseException
     */
    public static Date parse(@NonNull String date) throws NullPointerException
            , ParseException {
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
        if (Strings.isNullOrEmpty(date)) {
            return false;
        }
        return pattern.matcher(date).matches();
    }

    /**
     * Validate if date is a past or present date
     * @param date
     * @return true if is past or present date else return false
     * @throws NullPointerException
     * @throws ParseException
     */
    public static Boolean isPastOrPresent(@NonNull String date) throws NullPointerException
            , ParseException {
        checkNotNull(date, "date cannot be null!");
        return isPastOrPresent(DateUtil.parse(date));
    }

    /**
     * Validate if date is a past or present date
     * @param date
     * @return true if is past or present date else return false
     * @throws NullPointerException
     */
    private static Boolean isPastOrPresent(@NonNull Date date) throws NullPointerException {
        checkNotNull(date, "date cannot be null!");

        Date today = getCurrentDate();
        return !date.after(today);
    }

    /**
     * Calculate age from birth date
     * @param birthDate
     * @return
     * @throws NullPointerException
     * @throws ParseException
     * @throws IllegalArgumentException
     */
    public static int getAge(@NonNull String birthDate) throws NullPointerException
            , ParseException
            , IllegalArgumentException {
        checkNotNull(birthDate, "birthDate cannot be null!");
        return getAge(DateUtil.parse(birthDate));
    }

    /**
     * Calculate age from birth date
     * @param birthDate
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    private static int getAge(@NonNull Date birthDate) throws NullPointerException
            , IllegalArgumentException {
        checkNotNull(birthDate, "birthDate cannot be null!");
        if (!isPastOrPresent(birthDate)) {
            throw new IllegalArgumentException("Birth date must be before today");
        }

        Calendar today = Calendar.getInstance();
        today.setTime(getCurrentDate());

        Calendar dob = Calendar.getInstance();
        dob.setTime(birthDate);

        // If born this year, return 0
        if (today.get(Calendar.YEAR) == dob.get(Calendar.YEAR)) {
            return 0;
        }

        // Current year minus dob's year
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        // If today is before dob's date, did not complete a year yet
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }
}
