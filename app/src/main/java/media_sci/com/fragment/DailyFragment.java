package media_sci.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.limc.androidcharts.diagram.PieChart;
import cn.limc.androidcharts.series.TitleValueColorEntity;
import media_sci.com.shaklak_aklak.ChangeCaloriesActivity;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

public class DailyFragment extends Fragment implements View.OnClickListener {

    View actionbar;
    private LinearLayout lnr_calories, lnr_nutrients, lnr_pieChart;
    private Button btn_calories, btn_nutrients, btn_pieChart;
    private TextView tv_selected_date;
    private ImageView img_edit;
    // private WeekCalendar weekCalendar;

    private PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {

        actionbar = (View) view.findViewById(R.id.actionbar_daily);
        Utility.ActionBarSetting(actionbar, getString(R.string.daily), 3, "");

        lnr_calories = (LinearLayout) view.findViewById(R.id.lnr_daily_calories);
        lnr_nutrients = (LinearLayout) view.findViewById(R.id.lnr_daily_nutrients);
        lnr_pieChart = (LinearLayout) view.findViewById(R.id.lnr_daily_pieChart);
        btn_calories = (Button) view.findViewById(R.id.btn_daily_calories);
        btn_nutrients = (Button) view.findViewById(R.id.btn_daily_nutrients);
        btn_pieChart = (Button) view.findViewById(R.id.btn_daily_pieChart);
        img_edit = (ImageView) actionbar.findViewById(R.id.img_action_icon);
        pieChart = (PieChart) view.findViewById(cn.limc.androidcharts.R.id.piechart);
        tv_selected_date = (TextView) view.findViewById(R.id.tv_selected_date);
        //weekCalendar = (WeekCalendar) view.findViewById(R.id.weekCalendar);

        btn_calories.setOnClickListener(this);
        btn_nutrients.setOnClickListener(this);
        btn_pieChart.setOnClickListener(this);
        img_edit.setOnClickListener(this);


        SetupCalendar();
        SetupPieChart();

        SetVisibleView(lnr_calories, lnr_nutrients, lnr_pieChart
                , btn_calories, btn_nutrients, btn_pieChart);
    }

    private void SetupCalendar() {


        Date today = Calendar.getInstance().getTime();

        // (2) create a date "formatter" (the date format we want)
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE MMMM dd,yyyy");

        // (3) create a new String using the date format we want
        String today_Txt = formatter.format(today);
        tv_selected_date.setText(today_Txt);

        /*
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {

                SimpleDateFormat df1 = new SimpleDateFormat("EEEE MMMM dd,yyyy");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

                try {

                    Date date = df2.parse(dateTime.toString());
                    tv_selected_date.setText(df1.format(date));

                } catch (Exception e) {
                    Log.e("date error", "" + e);
                }
            }
        });*/

    }

    private void SetupPieChart() {

        final List<TitleValueColorEntity> data3 = new ArrayList<TitleValueColorEntity>();
        data3.add(new TitleValueColorEntity(getResources().getString(cn.limc.androidcharts.R.string.piechart_title1), 2,
                getResources().getColor(R.color.colorPrimaryDark)));
        data3.add(new TitleValueColorEntity(getResources().getString(cn.limc.androidcharts.R.string.piechart_title2), 3,
                getResources().getColor(R.color.pink)));
        data3.add(new TitleValueColorEntity(getResources().getString(cn.limc.androidcharts.R.string.piechart_title3), 3,
                getResources().getColor(R.color.dialog_color)));
        pieChart.setBorderWidth(0);
        pieChart.setData(data3);

    }

    private void SetVisibleView(LinearLayout lnr_first, LinearLayout lnr_second
            , LinearLayout lnr_third, Button btn_first, Button btn_second
            , Button btn_third) {

        lnr_first.setVisibility(View.VISIBLE);
        lnr_second.setVisibility(View.GONE);
        lnr_third.setVisibility(View.GONE);

        btn_first.setBackground(getResources().getDrawable
                (R.drawable.rectangle_border_pressed));
        btn_second.setBackground(getResources().getDrawable(R.drawable.rectangle_border));
        btn_third.setBackground(getResources().getDrawable(R.drawable.rectangle_border));


    }

    @Override
    public void onClick(View v) {

        if (v == btn_calories) {
            SetVisibleView(lnr_calories, lnr_nutrients, lnr_pieChart,
                    btn_calories, btn_nutrients, btn_pieChart);
        } else if (v == btn_nutrients) {
            SetVisibleView(lnr_nutrients, lnr_calories, lnr_pieChart,
                    btn_nutrients, btn_calories, btn_pieChart);
        } else if (v == btn_pieChart) {
            SetVisibleView(lnr_pieChart, lnr_nutrients, lnr_calories,
                    btn_pieChart, btn_nutrients, btn_calories);
        } else if (v == img_edit) {

            Intent intent = new Intent(getActivity(), ChangeCaloriesActivity.class);
            startActivity(intent);
        }

    }
}
