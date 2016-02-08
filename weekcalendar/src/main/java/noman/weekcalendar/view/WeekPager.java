package noman.weekcalendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.squareup.otto.Subscribe;

import org.joda.time.DateTime;

import noman.weekcalendar.R;
import noman.weekcalendar.adapter.PagerAdapter;
import noman.weekcalendar.eventbus.BusProvider;
import noman.weekcalendar.eventbus.Event;
import noman.weekcalendar.fragment.WeekFragment;

/**
 * Created by nor on 12/5/2015.
 */
public class WeekPager extends ViewPager {
    private static final String TAG = "WeekCalendar";
    public  PagerAdapter adapter;

    public static int NUM_OF_PAGES;
    private int pos;
    private boolean check;
    private TypedArray typedArray;

    public WeekPager(Context context) {
        super(context);
        initialize(null);
    }

    private void initialize(AttributeSet attrs) {
        if (attrs != null) {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WeekCalendar);
            NUM_OF_PAGES = typedArray.getInt(R.styleable.WeekCalendar_numOfPages, 100);
        }
        setId(idCheck());
        initPager();
        BusProvider.getInstance().register(this);

    }

    private int idCheck() {

        int id = 0;
        while (findViewById(++id) != null) ;
        return id;
    }

    private void initPager() {
        pos = NUM_OF_PAGES / 2;
        adapter = new PagerAdapter(getContext(), ((FragmentActivity) getContext())
                .getSupportFragmentManager(), new DateTime(), typedArray);
        setAdapter(adapter);
        addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (!check)
                    if (position < pos)
                        adapter.swipeBack();
                    else if (position > pos)
                        adapter.swipeForward();
                pos = position;
                check = false;

            }

        });
        setOverScrollMode(OVER_SCROLL_NEVER);
        setCurrentItem(pos);
        if (typedArray != null)
            setBackgroundColor(typedArray.getColor(R.styleable.WeekCalendar_daysBackgroundColor,
                    ContextCompat.getColor(getContext(), R.color.colorPrimary)));
        WeekFragment.selectedDate = new DateTime();

    }

    public WeekPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    @Subscribe
    public void setCurrentPage(Event.SetCurrentPageEvent event) {
        check = true;
        if (event.getDirection() == 1)
            adapter.swipeForward();
        else
            adapter.swipeBack();
        setCurrentItem(getCurrentItem() + event.getDirection());

    }

    @Subscribe
    public void reset(Event.ResetEvent event) {
        initPager();
    }
}
