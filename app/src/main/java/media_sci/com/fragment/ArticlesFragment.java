package media_sci.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/18/2015.
 */
public class ArticlesFragment extends Fragment {

    static int pageType = 0;
    LinearLayout lnr_home_default, lnr_home_details;
    Button btn_test;
    View actionbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_articles, container, false);
        SetupTools(v);
        CheckInterfaceVisibility();
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        pageType = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pageType = 0;
    }

    private void SetupTools(View view) {

        lnr_home_default = (LinearLayout) view.findViewById(R.id.lnr_articles_default);
        lnr_home_details = (LinearLayout) view.findViewById(R.id.lnr_articles_details);
        actionbar = (View) view.findViewById(R.id.articles_actionbar);
        Utility.ActionBarSetting(actionbar, 0, "المقالات", getActivity());

        ImageView img_action_back = (ImageView) actionbar.findViewById(R.id.img_action_back);
        img_action_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageType = 0;
                CheckInterfaceVisibility();

            }
        });


        btn_test = (Button) view.findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageType = 1;

                // CheckInterfaceVisibility();
                OpenFragment();
            }
        });
    }

    private void CheckInterfaceVisibility() {

        if (pageType == 0) {
            lnr_home_default.setVisibility(View.VISIBLE);
            lnr_home_details.setVisibility(View.GONE);
            Utility.ActionBarSetting(actionbar, 0, "المقالات", getActivity());
        } else if (pageType == 1) {
            lnr_home_default.setVisibility(View.GONE);
            lnr_home_details.setVisibility(View.VISIBLE);
            Utility.ActionBarSetting(actionbar, 1, "معلومات تهمك", getActivity());
        }
    }

    private void OpenFragment() {
        Fragment nextFrag = new DetailsFragment(); // set constructor with parameters
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, nextFrag);
         ft.hide(ArticlesFragment.this);
        ft.addToBackStack(ArticlesFragment.class.getName());
        ft.commit();
    }
}
