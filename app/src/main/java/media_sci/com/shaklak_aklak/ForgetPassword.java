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

import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/13/2015.
 */

public class ForgetPassword extends Activity implements View.OnClickListener
        , View.OnTouchListener {

    private TextView tv_forgetPassword, tv_email;
    private EditText et_email;
    private Button btn_changePassword, btn_back_login;
    private int user_id = -1;
    private String code = "";
    private boolean forget_flag = false;
    private boolean correct_mail = true;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        SetupTools();

        view = getWindow().getDecorView().getRootView();
        view.setOnTouchListener(this);
    }

    private void SetupTools() {

        tv_forgetPassword = (TextView) findViewById(R.id.tv_forgetPassword);
        tv_email = (TextView) findViewById(R.id.tv_forgetPassword_email);
        et_email = (EditText) findViewById(R.id.et_forgetPassword_email);
        btn_changePassword = (Button) findViewById(R.id.btn_change_password);
        btn_back_login = (Button) findViewById(R.id.btn_change_back_login);
        btn_changePassword.setOnClickListener(this);
        btn_back_login.setOnClickListener(this);

        SetFont();

    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(this);
        tv_forgetPassword.setTypeface(typeface);
        tv_email.setTypeface(typeface);
        et_email.setTypeface(typeface);
        btn_changePassword.setTypeface(typeface, Typeface.BOLD);
        btn_back_login.setTypeface(typeface, Typeface.BOLD);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_changePassword) {

            if (CheckDataValidation()) {
                // change password
                new ForgetPasswordAsyncTask().execute();
            }
        } else if (v == btn_back_login) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
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
                et_email.setError(getString(R.string.error_no_email));
            else if (validMail == 3)
                et_email.setError(getString(R.string.error_email));
        }

        return checkFlag;

    }

    private void ForgetPassword() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.ForgetPassword_URL);
        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",
                    et_email.getText().toString()));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("forget_status", status + "");
                if (status == 200) {

                    // check json result
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject returnData = new JSONObject(data);
                    user_id = returnData.getInt("user_id");
                    code = returnData.getString("verification_code");

                    Log.e("forget_code", code);
                    forget_flag = true;
                } else if (status == 403 /*or 400 */) {
                    correct_mail = false;
                }
            } catch (Exception e) {
                Log.e("forgetPassword error", "" + e);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Utility.HideKeyboard(this, getCurrentFocus());
        return false;
    }

    public class ForgetPasswordAsyncTask extends AsyncTask
            <Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {

            ForgetPassword();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ForgetPassword.this);
            dialog.setMessage(getString(R.string.wait_message));
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (forget_flag) {
                Intent intent = new Intent(ForgetPassword.this, ConfirmForgetActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("code", code);
                startActivity(intent);
                finish();
            } else {
                if (!correct_mail) {
                    Utility.ViewDialog(ForgetPassword.this, getString
                            (R.string.wrong_email));
                } else
                    Utility.ViewDialog(ForgetPassword.this, getString(R.string.fail));
            }
        }
    }

}
