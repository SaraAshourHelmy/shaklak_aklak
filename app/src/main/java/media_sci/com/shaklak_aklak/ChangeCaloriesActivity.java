package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import media_sci.com.models.UserCalculation;
import media_sci.com.models.UserData;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/17/2015.
 */
public class ChangeCaloriesActivity extends Activity implements
        View.OnClickListener, View.OnTouchListener {

    private View actionbar;
    private TextView tv_target_calories;
    private EditText et_target_calories;
    private Button btn_change_calories;
    private ImageView img_cancel;
    private boolean check_flag = false;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_calories);
        SetupTools();

        view = getWindow().getDecorView().getRootView();
        view.setOnTouchListener(this);


    }

    private void SetupTools() {

        actionbar = (View) findViewById(R.id.actionbar_custom_calories);

        Utility.ActionBarSetting(actionbar, getString(R.string.custom_calories),
                4, "", this);

        tv_target_calories = (TextView) findViewById(R.id.tv_target_calories);
        et_target_calories = (EditText) findViewById(R.id.et_target_calories);
        btn_change_calories = (Button) findViewById(R.id.btn_change_calories);
        img_cancel = (ImageView) actionbar.findViewById(R.id.img_action_icon);
        btn_change_calories.setOnClickListener(this);
        img_cancel.setOnClickListener(this);

        et_target_calories.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                et_target_calories.setFocusableInTouchMode(true);
                return false;
            }
        });

        // set target calories;
        SetFont();
        SetUserCalories();

    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(this);
        tv_target_calories.setTypeface(typeface);
        et_target_calories.setTypeface(typeface);
    }

    private void SetUserCalories() {
        UserCalculation userCalculation = new UserCalculation(this);
        double calories = userCalculation.CaloriesCalc();
        et_target_calories.setText(Utility.GetDecimalFormat(calories));
    }

    @Override
    public void onClick(View v) {

        if (v == btn_change_calories) {
            ChangeCalories();
        } else if (v == img_cancel) {
            finish();
        }
    }

    private void ChangeCalories() {

        if (et_target_calories.getText().toString().length() > 0) {

            UserData userData = new UserData(ChangeCaloriesActivity.this);

            userData.UpdateCalories(Double.parseDouble(et_target_calories.getText()
                    .toString()));

            StaticVarClass.UserCalories = (Double.parseDouble
                    (et_target_calories.getText()
                            .toString()));

            // type 8 call change calories webservice
            if (Utility.HaveNetworkConnection(this)) {

                FavAndMeal favAndMeal = new FavAndMeal(ChangeCaloriesActivity.this, 8);

            } else {

                finish();
            }


        } else {
            et_target_calories.setError(getString(R.string.error_change_calories));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v != et_target_calories)
            Utility.HideKeyboard(this, et_target_calories);
        return true;
    }
}