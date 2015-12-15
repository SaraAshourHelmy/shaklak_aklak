package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/6/2015.
 */
public class RegisterActivity extends Activity implements View.OnClickListener, View.OnTouchListener {

    static String Register_URL = "http://192.168.1.227/shaklk/public/api/users/register";
    TextView tv_firstName, tv_lastName, tv_mobileNo, tv_email;
    TextView tv_gender, tv_age, tv_height, tv_weight, tv_password;
    EditText et_firstName, et_lastName, et_mobileNo, et_email;
    EditText et_age, et_height, et_weight, et_password;
    RadioGroup rdg_genders;
    RadioButton rd_male, rd_female;
    Button btn_sign_in;
    String firstName, lastName, mobileNo, email, age, height, weight, gender, password;
    int result;
    String responseMessage = "";

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

        et_firstName = (EditText) findViewById(R.id.et_firstName);
        et_lastName = (EditText) findViewById(R.id.et_lastName);
        et_mobileNo = (EditText) findViewById(R.id.et_mobileNo);
        et_email = (EditText) findViewById(R.id.et_email);
        et_age = (EditText) findViewById(R.id.et_age);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_password = (EditText) findViewById(R.id.et_password);

        rdg_genders = (RadioGroup) findViewById(R.id.rgp_gender);
        rd_male = (RadioButton) findViewById(R.id.rb_male);
        rd_female = (RadioButton) findViewById(R.id.rb_female);
        btn_sign_in = (Button) findViewById(R.id.btn_signIn);

        rd_male.setButtonDrawable(R.color.white);
        rd_female.setButtonDrawable(R.color.white);

        rdg_genders.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_male) {
                    gender = "0";
                    rd_male.setButtonDrawable(R.drawable.checked);
                    rd_female.setButtonDrawable(R.color.white);

                } else if (checkedId == R.id.rb_female) {

                    rd_male.setButtonDrawable(R.color.white);
                    rd_female.setButtonDrawable(R.drawable.checked);
                    gender = "1";
                }
            }
        });

        btn_sign_in.setOnClickListener(this);
        //SetFocusInTouch();
    }

    private void SetFocusInTouch() {

        et_firstName.setOnTouchListener(this);
        et_lastName.setOnTouchListener(this);
        et_mobileNo.setOnTouchListener(this);
        et_email.setOnTouchListener(this);
        et_password.setOnTouchListener(this);
        et_age.setOnTouchListener(this);
        et_height.setOnTouchListener(this);
        et_weight.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_sign_in) {
            if (CheckDataValidation()) {
                PrepareRegister();
            }
        }
    }

    private boolean CheckDataValidation() {

        boolean checkFlag = true;

        if (et_weight.getText().length() < 1 || et_weight.getText().length() > 3) {
            et_weight.setError("Please enter Real wight");
            et_weight.requestFocus(); // this line make real focus
            checkFlag = false;
        }
        if (et_height.getText().length() < 1 || et_height.getText().length() > 3) {
            et_height.setError("Please enter Real height");
            et_height.requestFocus();
            checkFlag = false;
        }
        if (et_age.getText().length() < 1 || et_age.getText().length() > 2) {
            et_age.setError("Please enter Real Age");
            // change background to red rectangle or set message
            et_age.requestFocus();
            checkFlag = false;
        }

        if (rdg_genders.getCheckedRadioButtonId() <= 0) {
            checkFlag = false;
            Log.e("gender", "no selected");
            rd_male.setError("Select Item");
            rd_male.requestFocus();
        }
        if (et_password.getText().length() < 1) {
            et_password.setError("Please Enter Password");
            et_password.requestFocus();
            checkFlag = false;
        }
        int validMail = Utility.isValidEmail(et_email.getText().toString());

        if (validMail != 1) {
            checkFlag = false;
            et_email.requestFocus();
            if (validMail == 2)
                et_email.setError("Please enter Email");
            else if (validMail == 3)
                et_email.setError("Email is wrong");
        }
        if (et_mobileNo.getText().length() != 11) {
            et_mobileNo.setError("Mobile number is wrong");
            et_mobileNo.requestFocus();
            checkFlag = false;
        }

        if (et_lastName.getText().length() < 1) {
            et_lastName.setError("Please Enter Last Name");
            et_lastName.requestFocus();
            checkFlag = false;
        }
        if (et_firstName.getText().length() < 1) {
            et_firstName.setError("Please Enter First Name");
            et_firstName.requestFocus();
            checkFlag = false;
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


    private void Register() {


        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(Register_URL);

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
                    result = returnData.getInt("response");
                    responseMessage = returnData.getString("message");
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
        else if (v == et_age)
            et_age.setFocusableInTouchMode(true);
        else if (v == et_height)
            et_height.setFocusableInTouchMode(true);
        else if (v == et_weight)
            et_weight.setFocusableInTouchMode(true);
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
            dialog.setMessage("Please waiat ...");
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
                userData.setUserData(firstName, lastName, mobileNo, email, password,
                        gender, age, height, weight);

                Toast.makeText(RegisterActivity.this, "Register Success"
                        , Toast.LENGTH_SHORT).show();

                // call intent
                finish();
            } else if (result == 0) {
                Toast.makeText(RegisterActivity.this, responseMessage
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }

}
