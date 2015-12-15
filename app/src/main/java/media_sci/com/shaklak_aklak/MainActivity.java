package media_sci.com.shaklak_aklak;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import media_sci.com.fragment.ArticlesFragment;
import media_sci.com.fragment.FindFoodFragment;
import media_sci.com.fragment.FoodPyramidFragment;


public class MainActivity extends FragmentActivity {

    FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupTools();
    }

    private void SetupTools() {

        //  SetupTabs();
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // mTabHost.setBackgroundResource(R.drawable.tab_background);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("FindFood").setIndicator("",
                        getResources().getDrawable(R.drawable.logo)),
                FindFoodFragment.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Articles").setIndicator("",
                        getResources().getDrawable(R.drawable.back)),
                ArticlesFragment.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec("FoodPyramid").setIndicator("Food", null),
                FoodPyramidFragment.class, null);

        // hide tab 3
        // tabHost.getTabWidget().getChildAt(0).setVisibility(View.GONE);
        // tabHost.removeViewAt(0);//.setVisibility(View.GONE);
        mTabHost.setCurrentTab(0);
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                .setBackgroundResource(R.color.dialog_color);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    mTabHost.getTabWidget().getChildAt(i)
                            .setBackgroundResource(R.color.transparent); // unselected
                }
                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                        .setBackgroundResource(R.color.dialog_color); // selected

            }
        });

        TabHostSetting();
    }

    private void TabHostSetting() {
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height
                    = (int) (80 * this.getResources().getDisplayMetrics().density);
        }
    }

    public View createTabIndicator(LayoutInflater inflater, TabHost tabHost, int textResource, int iconResource) {
        View tabIndicator = inflater.inflate(R.layout.tab_indicator, tabHost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.tab_title)).setText(textResource);
        ((ImageView) tabIndicator.findViewById(R.id.tab_icon)).setImageResource(iconResource);
        return tabIndicator;
    }
}
