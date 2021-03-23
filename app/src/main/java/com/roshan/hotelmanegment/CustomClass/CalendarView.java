package com.roshan.hotelmanegment.CustomClass;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.roshan.hotelmanegment.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarView extends LinearLayout {
    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;
    // default date format
    private static final String DATE_FORMAT = "dd MMM yyyy";
    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();
    private Date today;

    //event handling
    private EventHandler eventHandler = null;
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    // seasons' rainbow
    int[] rainbow = new int[]{
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};


    public CalendarView(Context context) {
        super(context);

    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

    }

    private void assignClickHandlers() {
        btnNext.setOnClickListener(view -> {
            currentDate.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        btnPrev.setOnClickListener(view -> {
            currentDate.add(Calendar.MONTH, -1);
            updateCalendar();
        });


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (!(position < today.getDate())) {
                    eventHandler.onDayPress((Date) adapterView.getItemAtPosition(position));
                }
            }
        });
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }


    private void assignUiElements() {
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView) findViewById(R.id.calendar_next_button);
        txtDate = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);
    }

    public void updateCalendar() {
        updateCalendar(null);
    }

    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);

            // if this day has an event, specify event image
            view.setBackgroundResource(0);
           /* if (eventDays != null)
            {
                for (Date eventDate : eventDays)
                {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year)
                    {
                        // mark this day for event
                        view.setBackgroundResource(R.drawable.reminder);
                        break;
                    }
                }
            }*/

            // clear styling
            //((TextView)view).setTypeface(null, Typeface.NORMAL);
            ((TextView) view).setTextColor(Color.BLACK);

            if (month != today.getMonth() || year != today.getYear()) {
                // if this day is outside current month, grey it out
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            } else if (day == today.getDate()) {
                // if it is today, set it to blue/bold
                ((TextView) view).setTypeface(null, Typeface.BOLD);
                ((TextView) view).setTextColor(getResources().getColor(R.color.today));
            } else if (day < today.getDate() || month < today.getMonth()) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.greyed_out));
                ((TextView) view).setEnabled(false);
                ((TextView) view).setClickable(false);
                ((TextView) view).setFocusable(false);

            }

            // set text
            ((TextView) view).setText(String.valueOf(date.getDate()));

            return view;
        }
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler {
        void onDayPress(Date date);
    }
}
