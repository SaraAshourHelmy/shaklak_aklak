package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Bassem on 12/6/2015.
 */
public class IntroActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rltv_video;
    private ImageView img_play_video, img_facebook, img_instagram;
    private TextView tv_title, tv_description;
    private Button btn_skip, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        SetupTools();
        //Bassem
    }

    private void SetupTools() {

        rltv_video = (RelativeLayout) findViewById(R.id.rltv_intro_video);
        img_play_video = (ImageView) findViewById(R.id.img_play_video);
        img_facebook = (ImageView) findViewById(R.id.img_intro_facebook);
        img_instagram = (ImageView) findViewById(R.id.img_intro_instagram);
        tv_title = (TextView) findViewById(R.id.tv_intro_title);
        tv_description = (TextView) findViewById(R.id.tv_intro_description);
        btn_skip = (Button) findViewById(R.id.btn_intro_skip);
        btn_register = (Button) findViewById(R.id.btn_intro_register);

        img_play_video.setOnClickListener(this);
        img_facebook.setOnClickListener(this);
        img_instagram.setOnClickListener(this);
        btn_skip.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == img_play_video) {

            // intent to video player
        } else if (v == img_facebook) {
            ShareSocialMedia(0);

        } else if (v == img_instagram) {
            ShareSocialMedia(1);
        } else if (v == btn_skip) {
            // intent to home
        } else if (v == btn_register) {
            // intent to register
        }
    }

    private void ShareSocialMedia(int option) {
        if (option == 0) {
            // share with facebook
        } else if (option == 1) {
            // share with instagram
        }
    }
}
