package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import media_sci.com.utility.Utility;

public class IntroActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rltv_video;
    private ImageView img_play_video, img_facebook, img_instagram;
    private TextView tv_title, tv_description;
    private Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        SetupTools();
        SetFont();
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

    private void SetFont() {
        Typeface typeface = Utility.GetFont(this);
        tv_title.setTypeface(typeface);
        tv_description.setTypeface(typeface);
        btn_skip.setTypeface(typeface);
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

            FacebookShare(link);

        } else if (option == 1) {

            String twitter =
                    "https://twitter.com/intent/tweet?text=" + link;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
            startActivity(i);
        }
    }

    private void FacebookShare(String link) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u="
                    + link;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }
}

