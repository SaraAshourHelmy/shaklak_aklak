package media_sci.com.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import media_sci.com.models.UserData;
import media_sci.com.shaklak_aklak.MainActivity;
import media_sci.com.shaklak_aklak.R;

public class GetDataLogin {

    private Context context;
    private String date;
    private int is_today;
    private int type;
    private boolean meal_flag = false, fav_flag = false, custom_meal_flag = false;

    public GetDataLogin(Context context, String date, int is_today
            , int type) {

        this.context = context;
        this.date = date;
        this.is_today = is_today;
        this.type = type;
        if (type != 2)
            new DataLoginAsyncTask().execute();
        else {

            GetMyMeals();
        }


    }

    private void GetMyMeals() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.GetMeals);
        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();
            String user_id = String.valueOf(new UserData(context).GetUserID());
            data.add(new BasicNameValuePair("user_id", user_id));
            data.add(new BasicNameValuePair("date", date));
            data.add(new BasicNameValuePair("is_today", String.valueOf(is_today)));

            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("get_myMeal_status", "" + status);

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String my_meal = EntityUtils.toString(entity);
                Log.e("get_myMeals", my_meal);

                JSONObject myMealJson = new JSONObject(my_meal);
                //
                if (type == 1 || type == 3) {
                    Log.e("type", type + "");
                    //UserMeal.ClearUserMeals(context);
                }

                // parse update and delete unit
                ParseData.ParseMyMeal(myMealJson, context);
                meal_flag = true;
            }
        } catch (Exception e) {
            Log.e("get_myMeal_error", "" + e);
        }

    }

    private void GetUserFavourite() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.GetUserFavourite);

        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();

            String user_id = String.valueOf(new UserData(context).GetUserID());
            data.add(new BasicNameValuePair("user_id", user_id));

            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("get_fav_status", "" + status);

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String fav = EntityUtils.toString(entity);
                Log.e("all_fav", fav);

                JSONObject favJson = new JSONObject(fav);

                // parse update and delete unit
                ParseData.ParseFavourite(favJson, context);
                fav_flag = true;
            }
        } catch (Exception e) {
            Log.e("get_fav_error", "" + e);
        }

    }

    private void GetCustomMeals() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.GetCustomMeal);
        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();
            String user_id = String.valueOf(new UserData(context).GetUserID());
            data.add(new BasicNameValuePair("user_id", user_id));

            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("get_customMeal_status", "" + status);

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String custom_meal = EntityUtils.toString(entity);
                Log.e("get_customMeal", custom_meal);

                JSONObject customMealJson = new JSONObject(custom_meal);

                // parse update and delete unit
                ParseData.ParseCustomMeals(customMealJson, context);
                custom_meal_flag = true;
            }
        } catch (Exception e) {
            Log.e("get_customMeal_error", "" + e);
        }

    }

    public class DataLoginAsyncTask extends AsyncTask<Void, Void, Void> {


        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {

            if (type == 1 || type == 3) {

                GetCustomMeals();
                GetUserFavourite();

            }

            GetMyMeals();

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (type != 3) {
                dialog = new ProgressDialog(context);
                dialog.setMessage(context.getString(R.string.wait_message));
                dialog.setCancelable(false);
                dialog.show();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (type != 3)
                dialog.dismiss();

            if (type == 1) {

                if (meal_flag && fav_flag && custom_meal_flag) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    UserData userData = new UserData(context);
                    userData.ClearUserData();

                    Utility.ViewDialog(context, context.getString(R.string.error_connection));
                }
            }
        }
    }

}
