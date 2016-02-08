package media_sci.com.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import java.text.DecimalFormat;
import java.util.Calendar;

import media_sci.com.models.UserData;
import media_sci.com.service.DataService;
import media_sci.com.shaklak_aklak.LoginActivity;
import media_sci.com.shaklak_aklak.MainActivity;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.shaklak_aklak.Splash;

public class Utility {

    private static int TimeOut = 20;
    private static SharedPreferences shared_db;

    public static void HideKeyboard(Context context, View v) {
        try {

            InputMethodManager imm =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            MainActivity.mTabHost.getTabWidget().setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.e("keyboard_error", e + "");
        }
    }

    public static String GetDecimalFormat(double number) {

        return new DecimalFormat("##.#").format(number);
    }

    public static String GetStringDateNow() {

        Calendar c = Calendar.getInstance();
        String date = StaticVarClass.dash_format.format(c.getTime());
        return date;
    }

    public static String GetMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService
                (context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        return macAddress;
    }

    public static void ActionBarSetting(View view, String title, int type
            , String url, Context context) {


        TextView tv_action_title = (TextView) view.findViewById(R.id.tv_action_title);
        ImageView img_action_icon = (ImageView) view.findViewById(R.id.img_action_icon);

        tv_action_title.setText(title);
        tv_action_title.setTypeface(Utility.GetFont(context), Typeface.BOLD);

        if (type == 0) {
            // actionbar without icons
        } else if (type == 1) {

            // set icon image search icon
            img_action_icon.setImageResource(R.drawable.search);
        } else if (type == 2) {
            // set icon image category icon;
            CustomImageLoader.getInstance().loadImage(url
                    , img_action_icon, null);

        } else if (type == 3) {
            // icon is edit calories icon
            img_action_icon.setImageResource(R.drawable.edit);
        } else if (type == 4) {
            img_action_icon.setImageResource(R.drawable.close);
        }

        img_action_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public static Typeface GetFont(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Kohinoor.ttc");

        return typeface;
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

    public static void CreateDb(Context context, int try_again) {

        shared_db = context.getSharedPreferences("check_db", context.MODE_PRIVATE);
        boolean check = shared_db.getBoolean("check", false);
        Log.e("check", "" + check);
        if (!check) {
            // first run check internet connection
            if (HaveNetworkConnection(context))
                DatabaseSetting(context);
            else {
                ViewDialog(context, context.getString(R.string.no_internet));
                Splash.tv_try_again.setVisibility(View.VISIBLE);
                Splash.progress_download.setVisibility(View.GONE);

            }

        } else {
            Log.e("DB", "Already exist");


            if (try_again == 1) {

                // this case if fail in first time but db created
                GetData getData = new GetData(context, 1);

            } else {
                // call service
                Intent service_intent = new Intent(context, DataService.class);
                service_intent.putExtra("type", 1);
                context.startService(service_intent);

                UserData userData = new UserData(context);

                if (userData.GetUserID() != -1) {
                    if (StaticVarClass.verify_status == 0) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);

                    } else if (StaticVarClass.verify_status == 1) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                }
            }
        }

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
            GetData getData = new GetData(context, 1);

        } catch (Exception e) {
            Log.e("create DBf error :", "" + e);
        }
    }

    public static void ViewDialog(Context context, String msg) {

        final Dialog dialog = new Dialog(context,
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dialog.setContentView(R.layout.dialog_msg);
        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        tv_msg.setText(msg);
        tv_msg.setTypeface(GetFont(context));

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
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

    public static void CheckKeyboardVisible(final View parentLayout) {

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener
                (new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();

                        parentLayout.getWindowVisibleDisplayFrame(r);

                        int screenHeight = parentLayout.getRootView().getHeight();
                        int keyboardHeight = screenHeight - (r.bottom);

                        if (screenHeight - keyboardHeight > 50) {
                            // Do some stuff here
                        }

                        // screenHeight = keyboardHeight;
                        if (keyboardHeight > 100) {
                            SetTabVisibility(2);
                            Log.e("keyboard", "on");
                        } else {
                            SetTabVisibility(1);
                            Log.e("keyboard", "off");
                        }
                    }
                });
    }

    public static void SetTabVisibility(int type) {
        if (type == 1)
            MainActivity.mTabHost.getTabWidget().setVisibility(View.VISIBLE);
        else
            MainActivity.mTabHost.getTabWidget().setVisibility(View.GONE);
    }

}

