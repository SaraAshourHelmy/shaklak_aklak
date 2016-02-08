package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import media_sci.com.models.UserData;
import media_sci.com.utility.GetDataLogin;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

public class LoginActivity extends Activity implements View.OnClickListener,
        View.OnTouchListener {

    private TextView tv_email, tv_password;
    private EditText et_email, et_password;
    private Button btn_login, btn_forgetPassword, btn_register;
    private ImageView img_media_logo, img_shaklak_logo;
    private boolean checkFlag = false, reVerify_flag = false;
    private int error_no = -1;
    private boolean connect_flag = true;
    private int type = 0;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetupTools();

        view = getWindow().getDecorView().getRootView();
        view.setOnTouchListener(this);
    }

    private void SetupTools() {

        tv_email = (TextView) findViewById(R.id.tv_login_email);
        tv_password = (TextView) findViewById(R.id.tv_login_password);
        et_email = (EditText) findViewById(R.id.et_login_email);
        et_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgetPassword = (Button) findViewById(R.id.btn_login_forgetPassword);
        btn_register = (Button) findViewById(R.id.btn_login_register);
        img_media_logo = (ImageView) findViewById(R.id.img_media_logo);
        img_shaklak_logo = (ImageView) findViewById(R.id.img_shaklak_logo);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forgetPassword.setOnClickListener(this);
        SetFont();
    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(this);
        tv_email.setTypeface(typeface);
        tv_password.setTypeface(typeface);
        et_email.setTypeface(typeface);
        et_password.setTypeface(typeface);
        btn_login.setTypeface(typeface, Typeface.BOLD);
        btn_forgetPassword.setTypeface(typeface, Typeface.BOLD);
        btn_register.setTypeface(typeface, Typeface.BOLD);
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public void onClick(View v) {

        if (v == btn_login) {
            if (Utility.HaveNetworkConnection(this)) {
                if (CheckDataValidation()) {

                    type = 1;
                    new LoginAsyncTask().execute();
                }
            } else {

                Utility.ViewDialog(this, getString(R.string.no_internet));
            }

        } else if (v == btn_register) {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
            finish();

        } else if (v == btn_forgetPassword) {

            Intent forgetIntent = new Intent(this, ForgetPassword.class);
            startActivity(forgetIntent);
            finish();
        }

    }

    private boolean CheckDataValidation() {
        boolean checkFlag = true;
        int validMail = Utility.isValidEmail(et_email.getText().toString());

        if (validMail != 1) {
            checkFlag = false;
            et_email.requestFocus();
            if (validMail == 2)
                et_email.setError("Please enter Email");
            else if (validMail == 3)
                et_email.setError("Email is wrong");
        }
        if (et_password.getText().length() < 1) {
            checkFlag = false;
            et_password.setError("Password Enter Password");
        }

        return checkFlag;

    }

    private void Login() {

        // 401 email  or password
        // response 0 if mail not found and 1 if password is wrong

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.loginURL);
        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",
                    et_email.getText().toString()));
            params.add(new BasicNameValuePair("password",
                    et_password.getText().toString()));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.e("right_data", data);
                    JSONObject jsonObject = new JSONObject(data);
                    int user_id = jsonObject.getInt("id");
                    Log.e("user_id", user_id + "");
                    String first_name = jsonObject.getString("first_name");
                    String last_name = jsonObject.getString("last_name");
                    String phone = jsonObject.getString("phone");
                    String gender_txt = jsonObject.getString("gender");
                    String age = jsonObject.getString("age");
                    String height = jsonObject.getString("height");
                    String weight = jsonObject.getString("weight");
                    StaticVarClass.verify_status = jsonObject.getInt("is_verified");

                    String gender = (gender_txt.equals("male")) ? "0" : "1";
                    int exercise_type = jsonObject.getInt("exercise_type");
                    double calories = jsonObject.getDouble("calories");
                    Log.e("calories", calories + "");
                    UserData userData = new UserData(LoginActivity.this);
                    userData.setUserData(user_id, first_name, last_name, phone, et_email.getText().toString(),
                            et_password.getText().toString(), gender, age, height, weight
                            , exercise_type, calories);
                    checkFlag = true;
                } else if (status == 401) {

                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.e("wrong_data", data);
                    JSONObject jsonObject = new JSONObject(data);
                    String response_code = jsonObject.getString("response");
                    if (response_code.equals("0")) {
                        // show message invalid email
                        error_no = 0;

                    } else if (response_code.equals("1")) {
                        // show message wrong password
                        error_no = 1;

                    } else if (response_code.equals("2")) {
                        // already login
                        error_no = 2;

                    }

                }

            } catch (ConnectTimeoutException e) {
                connect_flag = false;

            } catch (Exception e) {
                Log.e("login_error", "" + e);
            }
        }
    }

    private void Reverify() {

        UserData userData = new UserData(LoginActivity.this);
        String user_id = String.valueOf(userData.GetUserID());
        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.ReVerifyUser);
        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", user_id));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {

                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject returnData = new JSONObject(data);
                    StaticVarClass.verification_code =
                            returnData.getString("verification_code");
                    Log.e("reVerify_code", StaticVarClass.verification_code);

                    reVerify_flag = true;
                }

            } catch (Exception e) {
                Log.e("re_verify_error", e + "");
            }
        }
    }

    private void PostWebservice() {
        if (type == 1) {
            // after login webservice
            PostLogin();
        } else if (type == 2) {
            PostVerify();
        }

    }

    private void PostLogin() {
        if (connect_flag) {
            if (checkFlag) {

                if (StaticVarClass.verify_status == 0) {
                    // view dialog to verify
                    SetVerifiedDialog();
                } else {

                    /*
                    Intent service_intent = new Intent(LoginActivity.this, DataService.class);
                    service_intent.putExtra("type", 2);
                    startService(service_intent);*/

                    String date = Utility.GetStringDateNow();
                    GetDataLogin dataLogin = new GetDataLogin(LoginActivity.this,
                            date, 0, 1);

                    // go to main activity after download user data

                }
            } else {

                if (error_no == 0) {
                    Utility.ViewDialog(LoginActivity.this,
                            getString(R.string.wrong_email));
                } else if (error_no == 1) {
                    Utility.ViewDialog(LoginActivity.this,
                            getString(R.string.wrong_password));
                } else if (error_no == 2) {
                    Utility.ViewDialog(LoginActivity.this,
                            getString(R.string.loggedIn_user));
                }
            }
        } else {
            Utility.ViewDialog(LoginActivity.this,
                    getString(R.string.error_connection));
        }
    }

    private void PostVerify() {

        if (reVerify_flag) {

            Intent intent = new Intent(LoginActivity.this, ConfirmRegisterActivity.class);
            startActivity(intent);
            finish();
        } else {

            Utility.ViewDialog(LoginActivity.this, getString(R.string.fail));
        }
    }

    private void SetVerifiedDialog() {

        final Dialog dialog = new Dialog(LoginActivity.this,
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);

        dialog.setContentView(R.layout.dialog_not_verify);
        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_verify_msg);
        tv_msg.setTypeface(Utility.GetFont(LoginActivity.this));

        Button btn_verify = (Button) dialog.findViewById(R.id.btn_dialog_verify);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_dialog_cancel);

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // call re verify webservice
                type = 2;
                new LoginAsyncTask().execute();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        if (v != et_email || v != et_password)
            Utility.HideKeyboard(this, getCurrentFocus());
        return false;
    }

    public class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {

            if (type == 1)
                Login();
            else if (type == 2)
                Reverify();
            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage(getResources().getString(R.string.wait_message));
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            PostWebservice();

        }
    }
}
