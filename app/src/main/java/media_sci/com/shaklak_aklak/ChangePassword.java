package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.app.ProgressDialog;
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
public class ChangePassword extends Activity implements View.OnClickListener {

    private static String changePasswordURL = "";
    private TextView tv_password, tv_confirm_password;
    private EditText et_new_password, et_confirm_password;
    private Button btn_save_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SetupTools();
    }

    private void SetupTools() {

        tv_confirm_password = (TextView) findViewById(R.id.tv_confirm_password);
        tv_password = (TextView) findViewById(R.id.tv_new_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        btn_save_password = (Button) findViewById(R.id.btn_save_Password);

        btn_save_password.setOnClickListener(this);
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
        HttpPost httpPost = Utility.SetHttpPost(changePasswordURL);
        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("password",
                    et_new_password.getText().toString()));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {

                    // check json result
                }
            } catch (Exception e) {
                Log.e("changePassword error", "" + e);
            }
        }
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

        }
    }
}
