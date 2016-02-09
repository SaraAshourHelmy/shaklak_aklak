package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import media_sci.com.utility.Utility;


public class ConfirmForgetActivity extends Activity implements
        View.OnClickListener, View.OnTouchListener {

    private TextView tv_forget_code;
    private EditText et_forget_code;
    private Button btn_verify_code;
    private int user_id = -1;
    private String code = "";
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_forget);
        SetupTools();

        view = getWindow().getDecorView().getRootView();
        view.setOnTouchListener(this);

    }

    private void SetupTools() {
        tv_forget_code = (TextView) findViewById(R.id.tv_forget_code);
        et_forget_code = (EditText) findViewById(R.id.et_forget_code);
        btn_verify_code = (Button) findViewById(R.id.btn_verify_forgetCode);

        btn_verify_code.setOnClickListener(this);
        SetFont();

        Bundle b = getIntent().getExtras();
        if (b.containsKey("user_id") && b.containsKey("code")) {
            user_id = b.getInt("user_id");
            code = b.getString("code");
        }
    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(this);
        tv_forget_code.setTypeface(typeface);
        et_forget_code.setTypeface(typeface);
        btn_verify_code.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_verify_code) {

            CheckCode();
        }
    }

    private void CheckCode() {

        if (et_forget_code.getText().toString().length() > 0) {
            if (et_forget_code.getText().toString().equals(code)) {

                Intent intent = new Intent(ConfirmForgetActivity.this
                        , ChangePassword.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();

            } else {
                Utility.ViewDialog(ConfirmForgetActivity.this,
                        getString(R.string.wrong_code));

            }
        } else {
            et_forget_code.setError("Please Enter Code");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Utility.HideKeyboard(this, getCurrentFocus());
        return false;
    }
}
