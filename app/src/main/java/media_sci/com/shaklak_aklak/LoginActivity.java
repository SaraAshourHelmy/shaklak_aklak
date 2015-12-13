package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/13/2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    static String loginURL = "";
    private TextView tv_email, tv_password;
    private EditText et_email, et_password;
    private Button btn_login, btn_forgetPassword, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetupTools();
    }

    private void SetupTools() {

        tv_email = (TextView) findViewById(R.id.tv_login_email);
        tv_password = (TextView) findViewById(R.id.tv_login_password);
        et_email = (EditText) findViewById(R.id.et_login_email);
        et_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgetPassword = (Button) findViewById(R.id.btn_login_forgetPassword);
        btn_register = (Button) findViewById(R.id.btn_login_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_login) {
            if (CheckDataValidation()) {
                // login
                new LoginAsyncTask().execute();
            }
        } else if (v == btn_register) {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);

        } else if (v == btn_forgetPassword) {

            Intent forgetIntent = new Intent(this, ForgetPassword.class);
            startActivity(forgetIntent);
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
            et_password.setError("Please Enter Password");
        }

        return checkFlag;

    }

    private void Login() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(loginURL);
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

                }
            } catch (Exception e) {
                Log.e("login_error", "" + e);
            }
        }
    }

    public class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {
            Login();
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
        }
    }


}
