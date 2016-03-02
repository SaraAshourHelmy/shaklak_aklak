package media_sci.com.shaklak_aklak;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

import media_sci.com.fragment.CustomSortFragment;
import media_sci.com.fragment.DailyFragment;
import media_sci.com.fragment.FavouriteFragment;
import media_sci.com.fragment.FindFoodFragment;
import media_sci.com.fragment.SettingFragment;


public class MainActivity extends FragmentActivity {

    public static int screenNo = 0;
    public static FragmentTabHost mTabHost;
    LinearLayout lnr_dialog, lnr_main_language;
    LinearLayout lnr_picker;

    @Override
    public void onBackPressed() {

        mTabHost.getTabWidget().setEnabled(true);
        // Utility.NotPushLayoutKeyboard(this);
        if (screenNo == 2) {
            screenNo = 0;
            lnr_dialog.setVisibility(View.GONE);
            lnr_picker.setVisibility(View.GONE);
            lnr_main_language.setVisibility(View.GONE);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupTools();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SetupTools() {

        //  SetupTabs();
        lnr_dialog = (LinearLayout) findViewById(R.id.lnr_dialog);
        lnr_picker = (LinearLayout) findViewById(R.id.lnr_picker);
        lnr_main_language = (LinearLayout) findViewById(R.id.lnr_main_language);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        // mTabHost.setBackgroundResource(R.drawable.tab_background);

        SetupTabs();
        mTabHost.setCurrentTab(0);
        SetTabsBackground();
        // mTabHost.setCurrentTab(0);
        // mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
        //  .setBackgroundResource(R.color.app_color);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {


                getSupportFragmentManager().popBackStack(null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);

                SetTabsBackground();

                /*
                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    mTabHost.getTabWidget().getChildAt(i)
                            .setBackgroundResource(R.color.transparent); // unselected
                }

                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                        .setBackgroundResource(R.color.app_color); // selected*/


            }
        });

        TabHostSetting();
        mTabHost.getTabWidget().setEnabled(true);
    }

    private void SetupTabs() {

        mTabHost.setup(this, getSupportFragmentManager()
                , android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("FindFood").setIndicator("",
                        null),
                FindFoodFragment.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Daily").setIndicator("",
                        null),
                DailyFragment.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Favourite").setIndicator("",
                        null),
                FavouriteFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("CustomSort").setIndicator("",
                        null),
                CustomSortFragment.class, null);

        // setting tab
        mTabHost.addTab(
                mTabHost.newTabSpec("Setting").setIndicator("",
                        null),
                SettingFragment.class, null);


    }

    private void SetTabsBackground() {

        mTabHost.getTabWidget().getChildAt(0).setBackground(
                getResources().getDrawable(R.drawable.home));

        mTabHost.getTabWidget().getChildAt(1).setBackground(
                getResources().getDrawable(R.drawable.daily));

        mTabHost.getTabWidget().getChildAt(2).setBackground(
                getResources().getDrawable(R.drawable.favourite));

        mTabHost.getTabWidget().getChildAt(3).setBackground(
                getResources().getDrawable(R.drawable.custom_sort));

        mTabHost.getTabWidget().getChildAt(4).setBackground(
                getResources().getDrawable(R.drawable.setting));


        if (mTabHost.getCurrentTab() == 0) {
            mTabHost.getTabWidget().getChildAt(0).setBackground(
                    getResources().getDrawable(R.drawable.home_selected));
        } else if (mTabHost.getCurrentTab() == 1) {
            mTabHost.getTabWidget().getChildAt(1).setBackground(
                    getResources().getDrawable(R.drawable.daily_selected));
        } else if (mTabHost.getCurrentTab() == 2) {
            mTabHost.getTabWidget().getChildAt(2).setBackground(
                    getResources().getDrawable(R.drawable.favourite_selected));
        } else if (mTabHost.getCurrentTab() == 3) {
            mTabHost.getTabWidget().getChildAt(3).setBackground(
                    getResources().getDrawable(R.drawable.custom_sort_selected));
        } else if (mTabHost.getCurrentTab() == 4) {
            mTabHost.getTabWidget().getChildAt(4).setBackground(
                    getResources().getDrawable(R.drawable.setting_selected));
        }
    }

    private void TabHostSetting() {

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height
                    = (int) (50 * this.getResources().getDisplayMetrics().density);
        }
    }
}
