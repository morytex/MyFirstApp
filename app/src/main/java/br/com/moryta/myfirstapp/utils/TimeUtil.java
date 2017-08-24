package br.com.moryta.myfirstapp.utils;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 24/08/2017.
 */

public class TimeUtil {
    private static final String PATTERN = "([0-2]\\d)\\:([0-5]\\d)";
    private static Pattern pattern = Pattern.compile(PATTERN);

    public static int HOUR_OF_DAY = 1;
    public static int MINUTE = 2;

    /**
     * Format tuple (int hourOfDay, int minute) to String in hh:mm format
     * @param hourOfDay
     * @param minute
     * @return
     */
    public static String format(int hourOfDay, int minute) {
        return String.format("%s:%s"
                , Strings.padStart(String.valueOf(hourOfDay), 2, '0')
                , Strings.padStart(String.valueOf(minute), 2, '0'));
    }

    /**
     * Parse string value of time and returns an int from selected portion of time (HOUR_OF_DAY | MINUTE)
     * @param time String value of time (i.e: "13:30")
     * @param timePortion 2 = get minutes portion from time string.
     *                    Any other value returns hour portion from time string
     * @return
     * @throws IllegalArgumentException
     */
    public static int parse(@NonNull String time, @NonNull int timePortion) throws IllegalArgumentException {
        checkNotNull(time, "time cannot be null!");
        if (!isValidFormat(time)) {
            throw new IllegalArgumentException();
        }

        Matcher matcher = pattern.matcher(time);
        // Execute matches() to generate groups
        matcher.matches();
        int index = timePortion == MINUTE ? MINUTE : HOUR_OF_DAY;
        return Integer.valueOf(matcher.group(index));
    }

    /**
     * Validate string time format
     * @param time
     * @return
     */
    public static boolean isValidFormat(String time) {
        if (Strings.isNullOrEmpty(time)) {
            return false;
        }

        return pattern.matcher(time).matches();
    }
}
