package media_sci.com.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.adapter.SimpleAdapter;
import media_sci.com.models.UserData;
import media_sci.com.shaklak_aklak.MainActivity;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.shaklak_aklak.Splash;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

public class SettingFragment extends Fragment implements View.OnClickListener
        , AdapterView.OnItemClickListener {

    private TextView tv_guide, tv_terms, tv_language, tv_language_selected;
    private LinearLayout lnr_guide, lnr_terms, lnr_language, lnr_main_language;
    private View actionbar;
    private Button btn_logout, btn_cancel;
    private ListView lst_language;
    private ArrayList<String> lst_lang = new ArrayList<>();
    private String[] language;
    private UserData userData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {


        language = getResources().getStringArray(R.array.arr_language);
        Log.e("language_size", language.length + "");

        tv_guide = (TextView) view.findViewById(R.id.tv_setting_guide);
        tv_terms = (TextView) view.findViewById(R.id.tv_setting_terms);
        tv_language = (TextView) view.findViewById(R.id.tv_language);
        tv_language_selected = (TextView) view.findViewById(R.id.tv_language_selected);
        lnr_guide = (LinearLayout) view.findViewById(R.id.lnr_setting_guide);
        lnr_terms = (LinearLayout) view.findViewById(R.id.lnr_setting_terms);
        lnr_language = (LinearLayout) view.findViewById(R.id.lnr_language);
        actionbar = (View) view.findViewById(R.id.actionbar_setting);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);

        lnr_main_language = (LinearLayout) getActivity().
                findViewById(R.id.lnr_main_language);
        lst_language = (ListView) lnr_main_language.findViewById(R.id.lst_language);
        btn_cancel = (Button) lnr_main_language.findViewById(R.id.btn_language_cancel);

       /*btn_lang = (Button) view.findViewById(R.id.btn_lang);
        btn_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.setLocale(getActivity());
            }
        });*/

        // type 0 in actionbar mean without icons
        Utility.ActionBarSetting(actionbar, getString(R.string.setting),
                0, "", getActivity());
        lnr_guide.setOnClickListener(this);
        lnr_terms.setOnClickListener(this);
        lnr_language.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        lst_language.setOnItemClickListener(this);
        btn_cancel.setOnClickListener(this);
        SetFont();
        SetDefaultLangName();

    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(getActivity());
        tv_terms.setTypeface(typeface, Typeface.BOLD);
        tv_guide.setTypeface(typeface, Typeface.BOLD);
        tv_language.setTypeface(typeface, Typeface.BOLD);
        tv_language_selected.setTypeface(typeface);
        btn_logout.setTypeface(typeface, Typeface.BOLD);
        btn_cancel.setTypeface(typeface, Typeface.BOLD);

    }

    private void SetDefaultLangName() {
        userData = new UserData(getActivity());
        if (userData.GetLanguage() == StaticVarClass.English)
            tv_language_selected.setText(language[StaticVarClass.English]);
        else if (userData.GetLanguage() == StaticVarClass.Arabic)
            tv_language_selected.setText(language[StaticVarClass.Arabic]);
    }

    @Override
    public void onClick(View v) {

        if (MainActivity.screenNo == 2)
            HideLanguage();

        if (v == btn_logout) {
            Logout();
        } else if (v == lnr_guide) {

            OpenFragment(1);

        } else if (v == lnr_terms) {

            OpenFragment(2);

        } else if (v == lnr_language) {

            ViewLanguage();
        }
        /*
        else if (v == btn_cancel) {
            HideLanguage();
        }*/
    }

    private void HideLanguage() {

        MainActivity.screenNo = 0;
        MainActivity.mTabHost.getTabWidget().setEnabled(true);
        Animation bottom_down = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);
        lnr_main_language.startAnimation(bottom_down);
        lnr_main_language.setVisibility(View.GONE);
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

    private void ViewLanguage() {

        lst_lang.clear();
        Log.e("lang_size", lst_lang.size() + "");
        MainActivity.screenNo = 2;
        MainActivity.mTabHost.getTabWidget().setEnabled(false);
        lnr_main_language.setVisibility(View.VISIBLE);
        Animation bottomUp = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);

        lnr_main_language.setAnimation(bottomUp);


        for (int i = 0; i < language.length; i++) {

            lst_lang.add(language[i]);
        }
        Log.e("lang_size", lst_lang.size() + "");

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), R.layout.adapter_simple
                , lst_lang, StaticVarClass.Pink_Color);
        lst_language.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SelectLanguage(position);
    }

    private void SelectLanguage(int selected) {

        HideLanguage();
        tv_language_selected.setText(lst_lang.get(selected));

        if (selected != userData.GetLanguage()) {
            Utility.SetSettingLanguage(getActivity(), selected);
            Intent intent = new Intent(getActivity(), Splash.class);
            startActivity(intent);
            getActivity().finish();
        }

        tv_language_selected.setText(lst_lang.get(selected));
    }


}
