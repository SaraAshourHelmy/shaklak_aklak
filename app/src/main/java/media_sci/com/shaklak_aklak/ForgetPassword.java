package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
public class ForgetPassword extends Activity implements View.OnClickListener {

    private TextView tv_forgetPassword, tv_email;
    private EditText et_email;
    private Button btn_changePassword;
    private int user_id = -1;
    private String code = "";
    private boolean forget_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        SetupTools();
    }

    private void SetupTools() {

        tv_forgetPassword = (TextView) findViewById(R.id.tv_forgetPassword);
        tv_email = (TextView) findViewById(R.id.tv_forgetPassword_email);
        et_email = (EditText) findViewById(R.id.et_forgetPassword_email);
        btn_changePassword = (Button) findViewById(R.id.btn_change_password);
        btn_changePassword.setOnClickListener(this);

        SetFont();

    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(this);
        tv_forgetPassword.setTypeface(typeface);
        tv_email.setTypeface(typeface);
        et_email.setTypeface(typeface);
        btn_changePassword.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_changePassword) {

            if (CheckDataValidation()) {
                // change password
                new ForgetPasswordAsyncTask().execute();
            }
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
                if (status == 200) {

                    // check json result
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject returnData = new JSONObject(data);
                    user_id = returnData.getInt("user_id");
                    code = returnData.getString("verification_code");

                    Log.e("forget_code", code);
                    forget_flag = true;
                }
            } catch (Exception e) {
                Log.e("forgetPassword error", "" + e);
            }
        }
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
            dialog.setMessage(getResources().getString(R.string.wait_message));
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
                Utility.ViewDialog(ForgetPassword.this, getString(R.string.fail));
            }
        }
    }


}
