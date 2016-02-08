package noman.weekcalendar.listener;

import org.joda.time.DateTime;

import java.text.ParseException;

/**
 * Created by nor on 12/5/2015.
 */
public interface OnDateClickListener {
    public void onDateClick(DateTime dateTime) throws ParseException;
}
