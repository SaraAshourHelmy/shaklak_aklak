package media_sci.com.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;
import media_sci.com.utility.ZoomableImageView;

/**
 * Created by Bassem on 12/15/2015.
 */
public class Guide_TermsFragment extends Fragment {

    private TextView tv_header1, tv_header2, tv_paragraph1, tv_paragraph2;
    private LinearLayout lnr_guide, lnr_terms;
    private ZoomableImageView img_setting_guide;
    private View actionbar;
    private int type;
    private String actionTitle = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_terms, container, false);
        SetupTools(view);

        return view;
    }

    private void SetupTools(View view) {

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("type"))
            type = bundle.getInt("type");

        lnr_guide = (LinearLayout) view.findViewById(R.id.lnr_guide);
        lnr_terms = (LinearLayout) view.findViewById(R.id.lnr_terms);
        tv_header1 = (TextView) view.findViewById(R.id.tv_terms_header1);
        tv_header2 = (TextView) view.findViewById(R.id.tv_terms_header2);
        tv_paragraph1 = (TextView) view.findViewById(R.id.tv_terms_paragraph1);
        tv_paragraph2 = (TextView) view.findViewById(R.id.tv_terms_paragraph2);
        img_setting_guide = (ZoomableImageView) view.findViewById(R.id.img_setting_guide);
        actionbar = (View) view.findViewById(R.id.actionbar_guide_terms);


        Bitmap giud_bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.guidlines);
        img_setting_guide.setImageBitmap(giud_bmp);

        SetupType();
        SetFont();
        Utility.ActionBarSetting(actionbar, actionTitle, 0, "", getActivity());


    }

    private void SetupType() {
        //  Log.e("type", type + "");
        if (type == 1) {

            actionTitle = getString(R.string.guide);
            lnr_guide.setVisibility(View.VISIBLE);
            lnr_terms.setVisibility(View.GONE);

        } else if (type == 2) {

            actionTitle = getString(R.string.terms);
            lnr_guide.setVisibility(View.GONE);
            lnr_terms.setVisibility(View.VISIBLE);
        }
    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(getActivity());
        tv_header1.setTypeface(typeface);
        tv_header2.setTypeface(typeface);
        tv_paragraph1.setTypeface(typeface);
        tv_paragraph2.setTypeface(typeface);
    }
}
