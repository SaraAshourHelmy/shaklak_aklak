package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import media_sci.com.models.UserData;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/6/2015.
 */
public class RegisterActivity extends Activity implements View.OnClickListener,
        View.OnTouchListener {

    private int result;
    private int user_id = -1, exercise_type = 0;
    private EditText et_age, et_height, et_weight, et_password,
            et_confirm_password;

    private RadioGroup rdg_genders;
    private RadioButton rd_male, rd_female;
    private Button btn_sign_in, btn_back_login, btn_next, btn_back_registerFisrt;
    private String firstName, lastName, mobileNo, email, age, height, weight, gender, password;
    private TextView tv_firstName, tv_lastName, tv_mobileNo, tv_email;
    private TextView tv_gender, tv_age, tv_height, tv_weight, tv_password;
    private TextView tv_exercise, tv_perWeek, tv_register_title1, tv_register_title2;
    private EditText et_firstName, et_lastName, et_mobileNo, et_email;
    private Spinner spnr_exercise;
    private LinearLayout lnr_register_first, lnr_register_second;
    private ScrollView scrl_register_first, scrl_register_second;
    private double calories = 0;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SetupTools();
    }

    private void SetupTools() {

        gender = "-1"; // by default selected male

        tv_firstName = (TextView) findViewById(R.id.tv_firstName);
        tv_lastName = (TextView) findViewById(R.id.tv_lastName);
        tv_mobileNo = (TextView) findViewById(R.id.tv_mobileNo);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_height = (TextView) findViewById(R.id.tv_height);
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_exercise = (TextView) findViewById(R.id.tv_exercise);
        tv_perWeek = (TextView) findViewById(R.id.tv_perWeek);
        tv_register_title1 = (TextView) findViewById(R.id.tv_register_title1);
        tv_register_title2 = (TextView) findViewById(R.id.tv_register_title2);

        et_firstName = (EditText) findViewById(R.id.et_firstName);
        et_lastName = (EditText) findViewById(R.id.et_lastName);
        et_mobileNo = (EditText) findViewById(R.id.et_mobileNo);
        et_email = (EditText) findViewById(R.id.et_email);
        et_age = (EditText) findViewById(R.id.et_age);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_register_confirm_password);

        rdg_genders = (RadioGroup) findViewById(R.id.rgp_gender);
        rd_male = (RadioButton) findViewById(R.id.rb_male);
        spnr_exercise = (Spinner) findViewById(R.id.spnr_exercise);

        rd_female = (RadioButton) findViewById(R.id.rb_female);
        btn_sign_in = (Button) findViewById(R.id.btn_signIn);
        btn_back_login = (Button) findViewById(R.id.btn_back_login);

        scrl_register_first = (ScrollView) findViewById(R.id.scrl_register_first);
        scrl_register_second = (ScrollView) findViewById(R.id.scrl_register_second);
        lnr_register_first = (LinearLayout) findViewById(R.id.lnr_register_first);
        lnr_register_second = (LinearLayout) findViewById(R.id.lnr_register_second);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_back_registerFisrt = (Button) findViewById(R.id.btn_backTo_register);

        SetFont();

        rd_male.setButtonDrawable(R.drawable.uncheck);
        rd_female.setButtonDrawable(R.drawable.uncheck);

        rdg_genders.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Utility.HideKeyboard(RegisterActivity.this, getCurrentFocus());
                rd_male.setError(null);
                rd_female.setError(null);

                if (checkedId == R.id.rb_male) {

                    gender = StaticVarClass.Male;
                    rd_male.setButtonDrawable(R.drawable.checked);
                    rd_female.setButtonDrawable(R.drawable.uncheck);

                } else if (checkedId == R.id.rb_female) {

                    rd_female.setButtonDrawable(R.drawable.checked);
                    rd_male.setButtonDrawable(R.drawable.uncheck);
                    gender = StaticVarClass.Female;
                }
            }
        });

        btn_sign_in.setOnClickListener(this);
        btn_back_login.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_back_registerFisrt.setOnClickListener(this);

        SetupExercise();
        SetFocusInTouch();
        RemoveViewError();
    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(this);
        tv_firstName.setTypeface(typeface);
        tv_lastName.setTypeface(typeface);
        tv_mobileNo.setTypeface(typeface);
        tv_email.setTypeface(typeface);
        tv_gender.setTypeface(typeface);
        tv_age.setTypeface(typeface);
        tv_height.setTypeface(typeface);
        tv_weight.setTypeface(typeface);
        tv_password.setTypeface(typeface);
        tv_perWeek.setTypeface(typeface);
        tv_exercise.setTypeface(typeface);
        tv_register_title1.setTypeface(typeface);
        tv_register_title2.setTypeface(typeface);
        et_firstName.setTypeface(typeface);
        et_lastName.setTypeface(typeface);
        et_mobileNo.setTypeface(typeface);
        et_email.setTypeface(typeface);
        et_age.setTypeface(typeface);
        et_height.setTypeface(typeface);
        et_weight.setTypeface(typeface);
        et_password.setTypeface(typeface);
        rd_male.setTypeface(typeface);
        rd_female.setTypeface(typeface);
        btn_sign_in.setTypeface(typeface, Typeface.BOLD);
        btn_back_login.setTypeface(typeface, Typeface.BOLD);
        btn_next.setTypeface(typeface, Typeface.BOLD);
        btn_back_registerFisrt.setTypeface(typeface, Typeface.BOLD);
    }

    private void SetupExercise() {

        String[] lst_exercise = StaticVarClass.GetExerciseList(this);
        // getResources().getStringArray(R.array.arr_exercise);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, lst_exercise);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_exercise.setAdapter(spinnerArrayAdapter);

        spnr_exercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                exercise_type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void SetFocusInTouch() {

        et_firstName.setOnTouchListener(this);
        et_lastName.setOnTouchListener(this);
        et_mobileNo.setOnTouchListener(this);
        et_email.setOnTouchListener(this);
        et_password.setOnTouchListener(this);
        et_confirm_password.setOnTouchListener(this);
        et_age.setOnTouchListener(this);
        et_height.setOnTouchListener(this);
        et_weight.setOnTouchListener(this);
    }

    private void RemoveViewError() {

        et_firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Utility.RemoveError(et_firstName);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Utility.RemoveError(et_lastName);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_mobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Utility.RemoveError(et_mobileNo);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Utility.RemoveError(et_email);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Utility.RemoveError(et_password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utility.RemoveError(et_confirm_password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utility.RemoveError(et_age);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utility.RemoveError(et_height);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utility.RemoveError(et_weight);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btn_sign_in) {

            if (Utility.HaveNetworkConnection(this)) {
                if (CheckValidationSecond()) {
                    PrepareRegister();
                }
            } else {
                Utility.ViewDialog(this, getString(R.string.no_internet));
            }
        } else if (v == btn_back_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btn_next) {

            if (CheckValidationFirst()) {
                scrl_register_first.setVisibility(View.GONE);
                scrl_register_second.setVisibility(View.VISIBLE);
            }
        } else if (v == btn_back_registerFisrt) {

            scrl_register_first.setVisibility(View.VISIBLE);
            scrl_register_second.setVisibility(View.GONE);
        }
    }

    private boolean CheckValidationSecond() {

        boolean checkFlag = true;

        if (et_weight.getText().length() < 1
                || et_weight.getText().toString().equals("0")) {
            et_weight.setError(getString(R.string.error_weight));
            et_weight.requestFocus(); // this line make real focus
            checkFlag = false;
        } else if (et_weight.getText().length() > 3 &&
                !et_weight.getText().toString().contains(".")) {
            et_weight.setError(getString(R.string.wrong_weight));
            et_weight.requestFocus(); // this line make real focus
            checkFlag = false;
        }

        // height ------
        if (et_height.getText().length() < 1
                || et_height.getText().toString().equals("0")) {
            et_height.setError(getString(R.string.error_height));
            et_height.requestFocus();
            checkFlag = false;
        } else if (et_height.getText().length() > 3
                && !et_height.getText().toString().contains(".")) {
            et_height.setError(getString(R.string.wrong_height));
            et_height.requestFocus();
            checkFlag = false;
        }

        // age ----
        if (et_age.getText().length() < 1
                || et_age.getText().toString().equals("0")) {
            et_age.setError(getString(R.string.error_age));
            // change background to red rectangle or set message
            et_age.requestFocus();
            checkFlag = false;
        }

        if (rdg_genders.getCheckedRadioButtonId() <= 0) {
            checkFlag = false;
            Log.e("gender", "no selected");
            rd_male.setError(getString(R.string.error_gender));
            rd_male.requestFocus();
        }
        return checkFlag;
    }

    private void PrepareRegister() {

        firstName = et_firstName.getText().toString();
        lastName = et_lastName.getText().toString();
        mobileNo = et_mobileNo.getText().toString();
        email = et_email.getText().toString();
        age = et_age.getText().toString();
        height = et_height.getText().toString();
        weight = et_weight.getText().toString();
        password = et_password.getText().toString();
        Log.e("gender", gender);
        new RegisterAsyncTask().execute();

    }

    private boolean CheckValidationFirst() {
        boolean checkFlag = true;

        if (et_password.getText().length() < 4) {
            et_password.setError(getString(R.string.error_password));
            et_password.requestFocus();
            checkFlag = false;
        } else {

            if (!et_confirm_password.getText().toString()
                    .equals(et_password.getText().toString())) {
                checkFlag = false;
                et_confirm_password.setError(getString(R.string.error_confirm_password));
                et_confirm_password.requestFocus();
            }
        }

        int validMail = Utility.isValidEmail(et_email.getText().toString());

        if (validMail != 1) {
            checkFlag = false;
            et_email.requestFocus();
            if (validMail == 2)
                et_email.setError(getString(R.string.error_no_email));
            else if (validMail == 3)
                et_email.setError(getString(R.string.error_email));
        }

        if (et_mobileNo.getText().length() != 11 ||
                !et_mobileNo.getText().toString().startsWith("01")) {
            if (et_mobileNo.getText().length() == 0) {

                et_mobileNo.setError(getString(R.string.no_mobile));

            } else {
                et_mobileNo.setError(getString(R.string.error_mobile));
            }
            et_mobileNo.requestFocus();
            checkFlag = false;
        }

        if (et_lastName.getText().length() < 1) {
            et_lastName.setError(getString(R.string.error_last_name));
            et_lastName.requestFocus();
            checkFlag = false;
        }

        if (et_firstName.getText().length() < 1) {
            et_firstName.setError(getString(R.string.error_first_name));
            et_firstName.requestFocus();
            checkFlag = false;
        }

        return checkFlag;

    }

    private void Register() {


        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.Register_URL);

        if (httpClient != null && httpClient != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("first_name", firstName));
            params.add(new BasicNameValuePair("last_name", lastName));
            params.add(new BasicNameValuePair("phone", mobileNo));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("age", age));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("weight", weight));
            params.add(new BasicNameValuePair("height", height));
            params.add(new BasicNameValuePair("exercise_type",
                    String.valueOf(exercise_type)));
            Log.e("exercise", exercise_type + "");

            try {
                Log.e("param", "" + params);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("register_status", "" + status);

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.e("register_json", data);
                    JSONObject returnData = new JSONObject(data);
                    //result = returnData.getInt("response");
                    //if (result == 1)
                    user_id = returnData.getInt("user_id");
                    calories = returnData.getDouble("calories");
                    Log.e("user_id", "" + user_id);
                    StaticVarClass.verification_code = returnData.getString("verification_code");
                    result = 1;

                } else if (status == 403) {

                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.e("register_json", data);
                    JSONObject returnData = new JSONObject(data);
                    result = returnData.getInt("response");

                } else if (status == 400) {
                    result = -1;
                }

            } catch (Exception e) {

                Log.e("Register_error", "" + e);
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == et_firstName)
            et_firstName.setFocusableInTouchMode(true);
        else if (v == et_lastName)
            et_lastName.setFocusableInTouchMode(true);
        else if (v == et_mobileNo)
            et_mobileNo.setFocusableInTouchMode(true);
        else if (v == et_email)
            et_email.setFocusableInTouchMode(true);
        else if (v == et_password)
            et_password.setFocusableInTouchMode(true);
        else if (v == et_confirm_password)
            et_confirm_password.setFocusableInTouchMode(true);
        else if (v == et_age)
            et_age.setFocusableInTouchMode(true);
        else if (v == et_height)
            et_height.setFocusableInTouchMode(true);
        else if (v == et_weight)
            et_weight.setFocusableInTouchMode(true);
        // else
        //   Utility.HideKeyboard(this, getCurrentFocus());


        return false;
    }

    public class RegisterAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {

            // call register method
            Register();
            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setMessage(getString(R.string.wait_message));
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (result == 1) {

                // save user data
                UserData userData = new UserData(RegisterActivity.this);
                userData.setUserData(user_id, firstName, lastName, mobileNo, email, password,
                        gender, age, height, weight, exercise_type, calories);

                userData.SetVerificationStatus(0);

                Toast.makeText(RegisterActivity.this, getString(R.string.register_success)
                        , Toast.LENGTH_SHORT).show();

                // call intent
                Intent confirmIntent = new Intent(RegisterActivity.this,
                        ConfirmRegisterActivity.class);
                startActivity(confirmIntent);

                finish();
                // change verify status (not verified)


            } else if (result == 0) {
                Utility.ViewDialog(RegisterActivity.this,
                        getString(R.string.error_mail));
            } else {
                Utility.ViewDialog(RegisterActivity.this,
                        getString(R.string.fail));
            }
        }
    }
}
