package media_sci.com.utility;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

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

import media_sci.com.models.LastUpdate;
import media_sci.com.shaklak_aklak.LoginActivity;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.shaklak_aklak.Splash;

public class GetData {

    static boolean category_flag = false, restaurant_flag = false,
            unit_flag = false, ingredient_flag = false,
            ingredient_unit_flag = false;

    Context context;
    int type;

    public GetData(Context context, int type) {

        this.context = context;
        this.type = type;

        if (type == 1) {
            if (Utility.HaveNetworkConnection(context))
                new DataAsyncTask().execute();
            else {
                Utility.ViewDialog(context, context.getString(R.string.no_internet));
            }
        } else {
            new DataAsyncTask().execute();
        }
    }

    private void GetCategory() {
        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.CategoryURL);
        String last_update;
        int lastUpdate_id;
        LastUpdate lastUpdate = LastUpdate.GetLastUpdate(context, "category");
        if (lastUpdate != null) {
            last_update = lastUpdate.last_update;
            lastUpdate_id = lastUpdate.lastUpdate_id;
        } else {
            last_update = "0";
            lastUpdate_id = 1;
        }
        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("lastupdate", last_update));
            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("category_status", "" + status);
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String category = EntityUtils.toString(entity);

                JSONObject CategoryJson = new JSONObject(category);
                last_update = CategoryJson.getString("lastupdate");
                lastUpdate = new LastUpdate(lastUpdate_id, "category", last_update);
                LastUpdate.InsertLastUpdate(lastUpdate, context);
                // parse update and delete category
                ParseData.ParseCategory(CategoryJson, context);
                category_flag = true;
            }
        } catch (Exception e) {
            Log.e("category error", "" + e);
        }

    }

    private void GetRestaurant() {
        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.RestaurantURL);

        String last_update;
        int lastUpdate_id;
        LastUpdate lastUpdate = LastUpdate.GetLastUpdate(context, "restaurant");
        if (lastUpdate != null) {
            last_update = lastUpdate.last_update;
            lastUpdate_id = lastUpdate.lastUpdate_id;
        } else {
            last_update = "0";
            lastUpdate_id = 2;
        }
        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("lastupdate", last_update));
            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("restaurant_status", "" + status);
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String restaurant = EntityUtils.toString(entity);

                JSONObject RestaurantJson = new JSONObject(restaurant);
                last_update = RestaurantJson.getString("lastupdate");
                lastUpdate = new LastUpdate(lastUpdate_id, "restaurant", last_update);
                LastUpdate.InsertLastUpdate(lastUpdate, context);

                // parse update and delete restaurant
                ParseData.ParseRestaurant(RestaurantJson, context);
                restaurant_flag = true;
            }
        } catch (Exception e) {
            Log.e("restaurant_error", "" + e);
        }

    }

    private void GetUnit() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.UnitURL);

        String last_update;
        int lastUpdate_id;
        LastUpdate lastUpdate = LastUpdate.GetLastUpdate(context, "unit");
        if (lastUpdate != null) {
            last_update = lastUpdate.last_update;
            lastUpdate_id = lastUpdate.lastUpdate_id;
        } else {
            last_update = "0";
            lastUpdate_id = 3;
        }
        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("lastupdate", last_update));
            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("unit_status", "" + status);
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String unit = EntityUtils.toString(entity);

                JSONObject UnitJson = new JSONObject(unit);
                last_update = UnitJson.getString("lastupdate");
                lastUpdate = new LastUpdate(lastUpdate_id, "unit", last_update);
                LastUpdate.InsertLastUpdate(lastUpdate, context);

                // parse update and delete unit
                ParseData.ParseUnit(UnitJson, context);
                unit_flag = true;
            }
        } catch (Exception e) {
            Log.e("unit_error", "" + e);
        }

    }

    private void GetIngredient() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.IngredientURL);

        String last_update;
        int lastUpdate_id;
        LastUpdate lastUpdate = LastUpdate.GetLastUpdate(context, "ingredient");
        if (lastUpdate != null) {
            last_update = lastUpdate.last_update;
            lastUpdate_id = lastUpdate.lastUpdate_id;
        } else {
            last_update = "0";
            lastUpdate_id = 4;
        }
        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("lastupdate", last_update));
            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("ingredient_status", "" + status);
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String ingredient = EntityUtils.toString(entity);
                Log.e("ingredient_json", ingredient);
                JSONObject IngredientJson = new JSONObject(ingredient);
                last_update = IngredientJson.getString("lastupdate");
                lastUpdate = new LastUpdate(lastUpdate_id, "ingredient", last_update);
                LastUpdate.InsertLastUpdate(lastUpdate, context);

                // parse update and delete ingredient
                ParseData.ParseIngredient(IngredientJson, context);
                ingredient_flag = true;
            }
        } catch (Exception e) {
            Log.e("ingredient_error", "" + e);
        }
    }

    private void GetIngredientUnit() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.IngredientUnitURL);

        String last_update;
        int lastUpdate_id;
        LastUpdate lastUpdate = LastUpdate.GetLastUpdate(context, "ingredient_unit");
        if (lastUpdate != null) {
            last_update = lastUpdate.last_update;
            lastUpdate_id = lastUpdate.lastUpdate_id;
        } else {
            last_update = "0";
            lastUpdate_id = 5;
        }
        try {

            List<NameValuePair> data = new ArrayList<NameValuePair>();

            data.add(new BasicNameValuePair("lastupdate", last_update));
            httpPost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            Log.e("ingredientUnit_status", "" + status);
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String ingredient_unit = EntityUtils.toString(entity);

                JSONObject Ingredient_Unit_Json = new JSONObject(ingredient_unit);
                last_update = Ingredient_Unit_Json.getString("lastupdate");
                lastUpdate = new LastUpdate(lastUpdate_id
                        , "ingredient_unit", last_update);
                LastUpdate.InsertLastUpdate(lastUpdate, context);

                // parse update and delete ingredient
                ParseData.ParseIngredientUnit(Ingredient_Unit_Json, context);
                ingredient_unit_flag = true;
            }
        } catch (Exception e) {
            Log.e("ingre_unit_error", "" + e);
        }

    }

    public class DataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (type == 1) {
                if (!category_flag)
                    GetCategory();
                if (!restaurant_flag)
                    GetRestaurant();
                if (!ingredient_flag)
                    GetIngredient();
                if (!unit_flag)
                    GetUnit();
                if (!ingredient_unit_flag)
                    GetIngredientUnit();

            } else if (type == 2) {

                GetCategory();
                GetRestaurant();
                GetIngredient();
                GetUnit();
                GetIngredientUnit();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (type == 1) {
                Splash.tv_try_again.setVisibility(View.GONE);
                Splash.progress_download.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (type == 1) {

                if (category_flag && restaurant_flag && unit_flag &&
                        ingredient_flag && ingredient_unit_flag) {

                    Splash.try_again = 0;
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {

                    Utility.ViewDialog(context, context.getString(R.string.error_connection));
                    Splash.tv_try_again.setVisibility(View.VISIBLE);
                    Splash.progress_download.setVisibility(View.GONE);
                    Splash.try_again = 1;

                }

            } else {
                Log.e("service_Data", "done");
            }
        }
    }
}
