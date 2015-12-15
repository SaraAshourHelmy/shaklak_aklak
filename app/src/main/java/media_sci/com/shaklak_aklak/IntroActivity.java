package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
    private Button btn_skip;

    {
        // share with instagram
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        SetupTools();
        //Bassem22222tgtdd
    }

    private void SetupTools() {

        // Utility.printHashKey(this);
        rltv_video = (RelativeLayout) findViewById(R.id.rltv_intro_video);
        img_play_video = (ImageView) findViewById(R.id.img_play_video);
        img_facebook = (ImageView) findViewById(R.id.img_intro_facebook);
        img_instagram = (ImageView) findViewById(R.id.img_intro_instagram);
        tv_title = (TextView) findViewById(R.id.tv_intro_title);
        tv_description = (TextView) findViewById(R.id.tv_intro_description);
        btn_skip = (Button) findViewById(R.id.btn_intro_skip);


        img_play_video.setOnClickListener(this);
        img_facebook.setOnClickListener(this);
        img_instagram.setOnClickListener(this);
        btn_skip.setOnClickListener(this);

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
        }
    }

    private void ShareSocialMedia(int option) {
        String link = "http://media-sci.com/";
        if (option == 0) {

            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u="
                    + link;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent.createChooser(intent, "Share With Facebook"));

        } else if (option == 1) {

            // String video_link = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

        }
    }
}

