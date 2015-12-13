package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/19/2015.
 */
public class MenuActivity extends Activity {

    View actionbar;
    ImageView img_search, img_contactUs, img_facebook, img_twitter, img_instagram, img_stamp;
    TextView tv_search, tv_contactUs, tv_followUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SetupTools();

    }

    private void SetupTools() {

        actionbar = (View) findViewById(R.id.menu_actionbar);
        img_search = (ImageView) findViewById(R.id.img_search_menu);
        img_contactUs = (ImageView) findViewById(R.id.img_contactUs_menu);
        img_facebook = (ImageView) findViewById(R.id.img_menu_facebook);
        img_twitter = (ImageView) findViewById(R.id.img_menu_twitter);
        img_instagram = (ImageView) findViewById(R.id.img_menu_instagram);
        img_stamp = (ImageView) findViewById(R.id.img_menu_stamp);
        tv_search = (TextView) findViewById(R.id.tv_search_menu);
        tv_contactUs = (TextView) findViewById(R.id.tv_contactUs_menu);
        tv_followUs = (TextView) findViewById(R.id.tv_followUs_menu);
        Utility.ActionBarSetting(actionbar, 2, "", this);
    }
}
