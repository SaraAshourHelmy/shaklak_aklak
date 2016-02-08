package noman.weekcalendar.utils;

import org.joda.time.DateTime;

/**
 * Created by nor on 12/8/2015.
 */
public class DateTimeUtils {
    public static boolean isSameWeek(final DateTime d1, final DateTime d2) {
        if ((d1 == null) || (d2 == null))
            throw new IllegalArgumentException("The date must not be null");

        // It is important to use week of week year & week year

        final int week1 = d1.getWeekOfWeekyear();
        final int week2 = d2.getWeekOfWeekyear();

        final int year1 = d1.getWeekyear();
        final int year2 = d2.getWeekyear();

        final int era1 = d1.getEra();
        final int era2 = d2.getEra();

        // Return true if week, year and era matches
        if ((week1 == week2) && (year1 == year2) && (era1 == era2))
            return true;

        // Return false if none of the conditions are satisfied
        return false;
    }

}
