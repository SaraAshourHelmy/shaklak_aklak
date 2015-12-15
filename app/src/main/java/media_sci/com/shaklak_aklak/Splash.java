package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.os.Bundle;

import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/18/2015.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        SetupTools();
    }

    private void SetupTools() {

        Utility.CreateDb(this);
        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        }, 5000);*/

       // GetData getData = new GetData(this);


    }
}
