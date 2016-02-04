package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import media_sci.com.service.DataService;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

public class ChangePassword extends Activity implements View.OnClickListener
        , View.OnTouchListener {

    private TextView tv_password, tv_confirm_password, tv_title;
    private EditText et_new_password, et_confirm_password;
    private Button btn_save_password;
    private int user_id = -1;
    private boolean check_flag = false;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SetupTools();

        view = getWindow().getDecorView().getRootView();
        view.setOnTouchListener(this);

    }

    private void SetupTools() {

        tv_confirm_password = (TextView) findViewById(R.id.tv_confirm_password);
        tv_password = (TextView) findViewById(R.id.tv_new_password);
        tv_title = (TextView) findViewById(R.id.tv_title_changePassword);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        btn_save_password = (Button) findViewById(R.id.btn_save_Password);

        btn_save_password.setOnClickListener(this);
        SetFont();

        Bundle b = getIntent().getExtras();
        if (b.containsKey("user_id")) {
            user_id = b.getInt("user_id");
        }
    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(this);
        tv_confirm_password.setTypeface(typeface);
        tv_password.setTypeface(typeface);
        tv_title.setTypeface(typeface);
        et_confirm_password.setTypeface(typeface);
        et_new_password.setTypeface(typeface);
        btn_save_password.setTypeface(typeface, Typeface.BOLD);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_save_password) {
            if (CheckDataValidation()) {

                // change password
                new ChangePasswordAsyncTask().execute();
            }
        }

    }

    private boolean CheckDataValidation() {

        boolean checkFlag = true;
        if (et_new_password.getText().length() < 1) {

            checkFlag = false;
            et_new_password.setError("Please Enter Password");
            et_new_password.requestFocus();

        } else {

            if (!et_confirm_password.getText().toString()
                    .equals(et_new_password.getText().toString())) {
                checkFlag = false;
                et_confirm_password.setError("Password Not Match");
                et_confirm_password.requestFocus();
            }
        }

        return checkFlag;
    }

    private void ChangePassword() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.ChangePassword_URL);
        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("user_id",
                    String.valueOf(user_id)));
            params.add(new BasicNameValuePair("password",
                    et_new_password.getText().toString()));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {

                    // check json result
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.e("right_data", data);
                    JSONObject jsonObject = new JSONObject(data);

                    String first_name = jsonObject.getString("first_name");
                    String last_name = jsonObject.getString("last_name");
                    String phone = jsonObject.getString("phone");
                    String gender_txt = jsonObject.getString("gender");
                    String age = jsonObject.getString("age");
                    String height = jsonObject.getString("height");
                    String weight = jsonObject.getString("weight");
                    String email = jsonObject.getString("email");
                    StaticVarClass.verify_status = jsonObject.getInt("is_verified");

                    String gender = (gender_txt.equals("male")) ? "0" : "1";
                    int exercise_type = jsonObject.getInt("exercise_type");
                    double calories = jsonObject.getDouble("calories");

                    UserData userData = new UserData(ChangePassword.this);
                    userData.setUserData(user_id, first_name, last_name, phone, email,
                            et_new_password.getText().toString(), gender, age, height, weight
                            , exercise_type, calories);
                    check_flag = true;
                }
            } catch (Exception e) {
                Log.e("changePassword error", "" + e);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Utility.HideKeyboard(this, getCurrentFocus());

        return false;
    }

    public class ChangePasswordAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {

            ChangePassword();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChangePassword.this);
            dialog.setMessage(getResources().getString(R.string.wait_message));
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (check_flag) {

                Intent service_intent = new Intent(ChangePassword.this, DataService.class);
                service_intent.putExtra("type", 2);
                startService(service_intent);

                Intent intent = new Intent(ChangePassword.this, MainActivity.class);
                startActivity(intent);

                finish();
            } else {
                Utility.ViewDialog(ChangePassword.this, getString(R.string.fail));
            }

        }
    }
}
