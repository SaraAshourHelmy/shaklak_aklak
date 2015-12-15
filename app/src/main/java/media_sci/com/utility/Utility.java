package media_sci.com.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import media_sci.com.shaklak_aklak.MainActivity;
import media_sci.com.shaklak_aklak.R;

public class Utility {

    private static int TimeOut = 20;
    private static SharedPreferences shared_db;


    public static void ActionBarSetting(View view, String title, int type
            , String url) {


        TextView tv_action_title = (TextView) view.findViewById(R.id.tv_action_title);
        ImageView img_action_icon = (ImageView) view.findViewById(R.id.img_action_icon);

        tv_action_title.setText(title);
        if (type == 0) {
            // actionbar without icons
        } else if (type == 1) {

            // set icon image search icon
            img_action_icon.setImageResource(R.drawable.search);
        } else if (type == 2) {
            // set icon image category icon;
           // CustomImageLoader.getInstance().loadImage(url
                  //  , img_action_icon, null);
        }

        img_action_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public static SQLiteDatabase ReadDatabase(Context con) {

        if (con == null) {
            Log.e("Db Context", "null");
            return null;
        } else {
            SQLiteDatabase db = con.openOrCreateDatabase("shaklak_aklak.sqlite"
                    , con.MODE_PRIVATE, null);
            return db;
        }

    }

    public static void CreateDb(Context context) {

        shared_db = context.getSharedPreferences("check_db", context.MODE_PRIVATE);
        boolean check = shared_db.getBoolean("check", false);
        Log.e("check", "" + check);
        if (!check) {

            DatabaseSetting(context);
        } else {
            Log.e("DB", "Already exist");
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }


    }

    private static void DatabaseSetting(Context context) {

        SQLiteDatabase db = context.openOrCreateDatabase("test.sqlite"
                , context.MODE_PRIVATE, null);

        InputStream is = context.getResources().openRawResource(
                R.raw.shaklak_aklak);
        try {
            byte[] b = new byte[is.available()];
            is.read(b);
            FileOutputStream fos = new FileOutputStream("/data/data/" +
                    context.getPackageName() + "/databases/shaklak_aklak.sqlite");
            fos.write(b);
            fos.close();

            shared_db = context.getSharedPreferences("check_db"
                    , context.MODE_PRIVATE);
            SharedPreferences.Editor edit = shared_db.edit();
            edit.putBoolean("check", true);
            edit.commit();
            GetData getData = new GetData(context);

        } catch (Exception e) {
            Log.e("create DBf error :", "" + e);
        }
    }

    public static HttpGet SetHttpGet(String url) {

        HttpGet Get = null;
        try {
            Get = new HttpGet(url);
            //HttpClient httpClient = SetTimeOut();

            //response = httpClient.execute(Get);


        } catch (Exception e) {
            Log.e("Http_Get Error :", "" + e);

        }
        return Get;

    }

    public static int isValidEmail(String mail) {
        // true case
        if (!TextUtils.isEmpty(mail) && android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
            return 1;
            // empty case
        else if (TextUtils.isEmpty(mail))
            return 2;
            // not match mail format  case
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
            return 3;
        else
            return 0;
    }

    public static HttpClient SetTimeOut() {

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, TimeOut * 1000);
        HttpConnectionParams.setSoTimeout(params, TimeOut * 1000);
        HttpClient httpclient = new DefaultHttpClient(params);
        return httpclient;
    }

    public static HttpPost SetHttpPost(String url) {

        HttpPost httppost = null;
        try {
            httppost = new HttpPost(url);

        } catch (Exception e) {
            Log.e("Http_Post Error :", "" + e);

        }
        return httppost;
    }

    public static boolean HaveNetworkConnection(Context context) {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    HaveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    HaveConnectedMobile = true;
        }
        return HaveConnectedWifi || HaveConnectedMobile;
    }

    public static void printHashKey(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo
                    (context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                // String something = new
                // String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }


}
