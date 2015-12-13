package media_sci.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/18/2015.
 */
public class FoodPyramidFragment extends Fragment {

    ImageView img_foodPyramid;
    private View actionbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food_pyramid, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {
        img_foodPyramid = (ImageView) view.findViewById(R.id.img_foodPyramid);
        actionbar = (View) view.findViewById(R.id.food_actionbar);
        Utility.ActionBarSetting(actionbar, 0, "الهرم الغذائي", getActivity());
    }


}
