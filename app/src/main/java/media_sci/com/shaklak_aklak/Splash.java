package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import media_sci.com.utility.Utility;

public class Splash extends Activity implements View.OnClickListener {

    public static TextView tv_try_again;
    public static ProgressBar progress_download;

    public static int try_again = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        SetupTools();
    }

    private void SetupTools() {

        tv_try_again = (TextView) findViewById(R.id.tv_try_again);
        progress_download = (ProgressBar) findViewById(R.id.progress_download);

        if (try_again == 0) {
            Utility.CreateDb(this, 0);
        }
        else if (try_again == 1) {
            Utility.CreateDb(this, 1);
            progress_download.setVisibility(View.GONE);
            tv_try_again.setVisibility(View.VISIBLE);
        }
        tv_try_again.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == tv_try_again) {
            Utility.CreateDb(this, 1);
        }
    }
}
