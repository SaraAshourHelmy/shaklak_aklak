package media_sci.com.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import media_sci.com.shaklak_aklak.R;

/**
 * Created by Bassem on 11/19/2015.
 */
public class ImportantInfoDialog extends Dialog {

    Activity activity;
    private ImageView img_impInfo, img_close, img_share, img_print;
    private TextView tv_description;

    public ImportantInfoDialog(Activity activity, int theme) {
        super(activity, theme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_important_info);
        SetupTools();
    }

    private void SetupTools() {

        img_impInfo = (ImageView) findViewById(R.id.img_impInfo_dialog);
        img_close = (ImageView) findViewById(R.id.img_close_dialog);
        img_share = (ImageView) findViewById(R.id.img_share_dialog);
        img_print = (ImageView) findViewById(R.id.img_print_dialog);
        tv_description = (TextView) findViewById(R.id.tv_dec_impInfo_dialog);
    }
}
