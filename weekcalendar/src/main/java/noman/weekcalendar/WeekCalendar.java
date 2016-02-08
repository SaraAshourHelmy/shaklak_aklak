package noman.weekcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.text.ParseException;

import noman.weekcalendar.eventbus.BusProvider;
import noman.weekcalendar.eventbus.Event;
import noman.weekcalendar.listener.OnDateClickListener;
import noman.weekcalendar.view.WeekPager;

/**
 * Created by nor on 12/6/2015.
 */
public class WeekCalendar extends LinearLayout {
    private static final String TAG = "WeekCalendarTAG";
    private OnDateClickListener listener;
    public  WeekPager weekPager;
    private TypedArray typedArray;


    public WeekCalendar(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WeekCalendar);
        }
        BusProvider.getInstance().register(this);
        setOrientation(VERTICAL);
        addView(getDaysNames(), 0);
        weekPager = new WeekPager(getContext(), attrs);
        addView(weekPager);
    }

    private GridView getDaysNames() {
        GridView dayNames = new GridView(getContext());
        dayNames.setSelector(new StateListDrawable());
        dayNames.setNumColumns(7);
        dayNames.setAdapter(new BaseAdapter() {
            private String[] days = {"M", "T", "W", "T", "F", "S", "S"};

            @Override
            public int getCount() {
                return days.length;
            }

            @Override
            public String getItem(int position) {
                return days[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.grid_item, null);
                }
                TextView day = (TextView) convertView.findViewById(R.id.daytext);
                day.setText(days[position]);
                if (typedArray != null) {
                    day.setTextColor(typedArray.getColor(R.styleable.WeekCalendar_weekTextColor,
                            Color.WHITE));
                    day.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(R.styleable
                            .WeekCalendar_weekTextSize, day.getTextSize()));
                }
                return convertView;
            }
        });
        if (typedArray != null)
            dayNames.setBackgroundColor(typedArray.getColor(R.styleable
                    .WeekCalendar_weekBackgroundColor, ContextCompat.getColor(getContext(), R
                    .color.colorPrimary)));
        return dayNames;
    }

    public WeekCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public WeekCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    /***
     * Do not use this method
     * this is for receiving date,
     * use "setOndateClick" instead.
     */
    @Subscribe
    public void onDateClick(Event.OnDateClickEvent event) throws ParseException {
        if (listener != null)
            listener.onDateClick(event.getDateTime());
    }

    public void setOnDateClickListener(OnDateClickListener listener) {
        this.listener = listener;
    }

    public void moveToPrevious() {
        BusProvider.getInstance().post(new Event.UpdateSelectedDateEvent(-1));
    }

    public void moveToNext() {
        BusProvider.getInstance().post(new Event.UpdateSelectedDateEvent(1));
    }

    public void reset() {
        BusProvider.getInstance().post(new Event.ResetEvent());
    }
}
