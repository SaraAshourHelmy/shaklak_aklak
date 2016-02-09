package media_sci.com.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.Utility;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private TextView tv_guide, tv_terms;
    private RelativeLayout rltv_guide, rltv_terms;
    private View actionbar;
    private Button btn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {

        tv_guide = (TextView) view.findViewById(R.id.tv_setting_guide);
        tv_terms = (TextView) view.findViewById(R.id.tv_setting_terms);
        rltv_guide = (RelativeLayout) view.findViewById(R.id.rltv_setting_guide);
        rltv_terms = (RelativeLayout) view.findViewById(R.id.rltv_setting_terms);
        actionbar = (View) view.findViewById(R.id.actionbar_setting);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);

        // type 0 in actionbar mean without icons
        Utility.ActionBarSetting(actionbar, getString(R.string.setting),
                0, "", getActivity());
        rltv_guide.setOnClickListener(this);
        rltv_terms.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        SetFont();

    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(getActivity());
        tv_terms.setTypeface(typeface, Typeface.BOLD);
        tv_guide.setTypeface(typeface, Typeface.BOLD);
        btn_logout.setTypeface(typeface, Typeface.BOLD);

    }

    @Override
    public void onClick(View v) {

        if (v == btn_logout) {
            Logout();
        } else if (v == rltv_guide) {

            OpenFragment(1);

        } else if (v == rltv_terms) {

            OpenFragment(2);
        }
    }

    private void Logout() {

        // check if stored data exist
        if (Utility.HaveNetworkConnection(getActivity())) {
            FavAndMeal favAndMeal = new FavAndMeal(getActivity(), 6);

        } else {

            Utility.ViewDialog(getActivity(), getString(R.string.no_internet));
        }


    }

    private void OpenFragment(int type) {

        Bundle bundle = new Bundle();
        Log.e("setting_type", type + "");
        bundle.putInt("type", type);

        Fragment guide_termsFragment = new Guide_TermsFragment(); // set constructor with parameters
        guide_termsFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, guide_termsFragment);
        ft.hide(SettingFragment.this);
        ft.addToBackStack(SettingFragment.class.getName());
        ft.commit();
    }

}
