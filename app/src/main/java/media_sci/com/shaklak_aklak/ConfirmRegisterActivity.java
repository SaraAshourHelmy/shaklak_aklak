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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import media_sci.com.models.UserData;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/21/2015.
 */
public class ConfirmRegisterActivity extends Activity implements
        View.OnClickListener, View.OnTouchListener {

    private TextView tv_thank, tv_confirmMsg, tv_register_code;
    private EditText et_register_code;
    private Button btn_verify_mail;
    private boolean check_flag = false;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_register);
        SetupTools();

        view = getWindow().getDecorView().getRootView();
        view.setOnTouchListener(this);

    }

    private void SetupTools() {
        tv_thank = (TextView) findViewById(R.id.tv_confirm_thnx);
        tv_confirmMsg = (TextView) findViewById(R.id.tv_confirm_msg);
        tv_register_code = (TextView) findViewById(R.id.tv_register_code);
        et_register_code = (EditText) findViewById(R.id.et_register_code);
        btn_verify_mail = (Button) findViewById(R.id.btn_verify_mail);

        btn_verify_mail.setOnClickListener(this);
        SetFont();
    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(this);
        tv_thank.setTypeface(typeface);
        tv_confirmMsg.setTypeface(typeface);
        tv_register_code.setTypeface(typeface);
        et_register_code.setTypeface(typeface);
        btn_verify_mail.setTypeface(typeface, Typeface.BOLD);

    }

    @Override
    public void onClick(View v) {

        if (v == btn_verify_mail) {

            if (et_register_code.getText().toString().length() > 0) {
                CheckCode();
            } else {
                et_register_code.setError("Please Enter Code");
            }
        }
    }

    private void CheckCode() {

        if (StaticVarClass.verification_code
                .equals(et_register_code.getText().toString())) {
            if (Utility.HaveNetworkConnection(this))
                new VerifyTask().execute();
            else {
                Utility.ViewDialog(this, getString(R.string.no_internet));
            }

        } else {

            Utility.ViewDialog(this, getString(R.string.wrong_code));
        }
    }

    private void VerifyUser() {
        UserData userData = new UserData(ConfirmRegisterActivity.this);
        String user_id = String.valueOf(userData.GetUserID());
        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.VerifyUser);
        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", user_id));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    check_flag = true;
                }

            } catch (Exception e) {
                Log.e("verify_error", e + "");
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Utility.HideKeyboard(this, getCurrentFocus());
        return false;
    }

    public class VerifyTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {

            VerifyUser();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ConfirmRegisterActivity.this);
            dialog.setMessage(getString(R.string.wait_message));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            // if success
            if (check_flag) {
                UserData userData = new UserData(ConfirmRegisterActivity.this);
                userData.SetVerificationStatus(1);

                Intent intent = new Intent(ConfirmRegisterActivity.this
                        , IntroActivity.class);
                startActivity(intent);
                finish();
            } else {

                Utility.ViewDialog(ConfirmRegisterActivity.this
                        , getString(R.string.fail));
            }
        }
    }
}
