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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import media_sci.com.models.Ingredients;
import media_sci.com.models.UserData;
import media_sci.com.models.UserFavourite;
import media_sci.com.models.UserMeal;
import media_sci.com.shaklak_aklak.LoginActivity;
import media_sci.com.shaklak_aklak.R;

public class FavAndMeal {

    Context context;
    int type;
    boolean logout_flag = false;
    boolean check_flag = false;
    boolean stored_flag = true;


    public FavAndMeal(Context context, int type) {

        this.context = context;
        this.type = type;

        if (type == 6) {
            if (Utility.HaveNetworkConnection(context)) {
                new FavMealAsyncTask().execute();
            } else {

                Utility.ViewDialog(context, context.getString(R.string.no_internet));
            }
        } else {
            new FavMealAsyncTask().execute();
        }
    }

    private void SetStoredData() {

        if (StaticVarClass.lst_custom_ingredients.size() > 0)
            SetCustomMeal();

        if (StaticVarClass.lst_removed_favourite.size() > 0)
            RemoveFavourite();

        if (StaticVarClass.lst_meals.size() > 0)
            SetMeal();

        if (StaticVarClass.lst_favourites_user.size() > 0)
            SetFavourite();

        if (StaticVarClass.UserCalories != -1)
            SetCalories();
    }

    private void SetCustomMeal() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.SetCustomMeal);
        if (httpClient != null && httpPost != null) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String user_id = String.valueOf(new UserData(context).GetUserID());
            Log.e("user_id", user_id);
            params.add(new BasicNameValuePair("user_id", user_id));
            Log.e("meals", GetCustomMealJson());
            params.add(new BasicNameValuePair("meals", GetCustomMealJson()));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("customMeal_status", status + "");
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String meals = EntityUtils.toString(entity);
                    Log.e("customMeals_Json", meals);
                    StaticVarClass.lst_custom_ingredients.clear();

                } else {
                    stored_flag = false;
                }
            } catch (Exception e) {
                Log.e("customMeal_error", e + "");
                stored_flag = false;
            }
        }
    }

    private void RemoveFavourite() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.FavURL);
        if (httpClient != null && httpPost != null) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //String.valueOf(new UserData(context).GetUserID()))
            String user_id = String.valueOf(new UserData(context).GetUserID());
            params.add(new BasicNameValuePair("user_id", user_id));
            Log.e("user_id", user_id);
            params.add(new BasicNameValuePair("is_favorite", "0"));
            params.add(new BasicNameValuePair("fav", GetRemoveFavJson()));
            Log.e("remove_fav_json", GetRemoveFavJson());
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("remove_fav_status", status + "");
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String remove_fav = EntityUtils.toString(entity);
                    Log.e("remove_fav", remove_fav);
                    StaticVarClass.lst_removed_favourite.clear();
                } else {
                    stored_flag = false;
                }

            } catch (Exception e) {

                Log.e("remove_fav_error", e + "");
                stored_flag = false;

            }
        }
    }

    private void SetMeal() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.MealURL);
        if (httpClient != null && httpPost != null) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String user_id = String.valueOf(new UserData(context).GetUserID());
            Log.e("user_id", user_id);
            params.add(new BasicNameValuePair("user_id", user_id));
            Log.e("meals", GetMealJson());
            params.add(new BasicNameValuePair("meals", GetMealJson()));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("meal_status", status + "");
                if (status == 200) {

                    HttpEntity entity = response.getEntity();
                    String meals = EntityUtils.toString(entity);
                    Log.e("set_meals", meals);
                    StaticVarClass.lst_meals.clear();
                    check_flag = true;

                } else {
                    stored_flag = false;
                }

            } catch (Exception e) {

                Log.e("setMeals_error", e + "");
                stored_flag = false;

            }
        }
    }

    private void SetFavourite() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.FavURL);
        if (httpClient != null && httpPost != null) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //String.valueOf(new UserData(context).GetUserID()))
            String user_id = String.valueOf(new UserData(context).GetUserID());
            params.add(new BasicNameValuePair("user_id", user_id));
            Log.e("user_id", user_id);
            params.add(new BasicNameValuePair("is_favorite", "1"));
            params.add(new BasicNameValuePair("fav", GetFavJson()));
            Log.e("fav_json", GetFavJson());
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("status", status + "");
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String fav = EntityUtils.toString(entity);
                    Log.e("set_fav", fav);
                    StaticVarClass.lst_favourites_user.clear();
                    check_flag = true;
                } else {
                    stored_flag = false;
                }

            } catch (Exception e) {

                Log.e("fav_error", e + "");
                stored_flag = false;

            }
        }
    }

    private void SetCalories() {
        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.ChangeCalories_URL);
        UserData userData = new UserData(context);
        String user_id = String.valueOf(userData.GetUserID());

        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("user_id",
                    String.valueOf(user_id)));
            params.add(new BasicNameValuePair("calories",
                    String.valueOf(StaticVarClass.UserCalories)));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("calories_status", status + "");

                if (status == 200) {

                    check_flag = true;
                    StaticVarClass.UserCalories = -1;
                }
            } catch (Exception e) {
                Log.e("calories_error", "" + e);
            }
        }
    }

    private String GetCustomMealJson() {

        ArrayList<Ingredients> lst_meal = StaticVarClass.lst_custom_ingredients;
        //JSONObject meal = new JSONObject();
        JSONArray meals = new JSONArray();
        Ingredients ingredients;
        JSONObject everyMeal;
        try {

            for (int i = 0; i < lst_meal.size(); i++) {
                everyMeal = new JSONObject();
                ingredients = lst_meal.get(i);
                everyMeal.put("id", ingredients.getCustomID());
                everyMeal.put("counter_id", ingredients.getCounter_id());
                everyMeal.put("item_name", ingredients.getItem_name_en());
                everyMeal.put("water", ingredients.getWater());
                everyMeal.put("energy", ingredients.getEnergy());
                everyMeal.put("protein", ingredients.getProtein());
                everyMeal.put("fat", ingredients.getFat());
                everyMeal.put("sat_fat", ingredients.getSat_fat());
                everyMeal.put("cholest", ingredients.getCholest());
                everyMeal.put("carbo_tot", ingredients.getCarbo_tot());
                everyMeal.put("sugars", ingredients.getSugars());
                everyMeal.put("carbo_fiber", ingredients.getCarbo_fiber());
                everyMeal.put("ash", ingredients.getAsh());
                everyMeal.put("calcium", ingredients.getCalcium());
                everyMeal.put("phosphorus", ingredients.getPhosphorus());
                everyMeal.put("iron", ingredients.getIron());
                everyMeal.put("sodium", ingredients.getSodium());
                everyMeal.put("potassium", ingredients.getPotassium());
                everyMeal.put("vit_a", ingredients.getVit_a());
                everyMeal.put("thiamine_b1", ingredients.getThiamine_b1());
                everyMeal.put("riboflavin_b2", ingredients.getRiboflavin_b2());
                everyMeal.put("niacin_b3", ingredients.getNiacin_b3());
                everyMeal.put("ascorbic_acid", ingredients.getAscorbic_acid());
                everyMeal.put("ndb_no", ingredients.getNdb_no());
                everyMeal.put("type", ingredients.getType());
                everyMeal.put("unit_value", ingredients.getUnit_value());
                everyMeal.put("unit_name", ingredients.getUnit_name());

                meals.put(everyMeal);
            }

            //meal.put("meals", meals);
            // meal.put("user_id", new UserData(context).GetUserID());
            return meals.toString();

        } catch (Exception e) {
            Log.e("build_CustomMeal", e + "");
            return "";
        }

    }

    private String GetRemoveFavJson() {

        ArrayList<UserFavourite> lst_favourite = StaticVarClass.lst_removed_favourite;

        JSONArray fav = new JSONArray();
        JSONObject everyFav;
        try {

            for (int i = 0; i < lst_favourite.size(); i++) {
                everyFav = new JSONObject();
                everyFav.put("meal_id", lst_favourite.get(i).getMeal_id());
                everyFav.put("is_custom", lst_favourite.get(i).getIs_custom());
                fav.put(everyFav);
            }

        } catch (Exception e) {

            Log.e("build_fav_error", "" + e);
        }

        return fav.toString();
    }

    private String GetMealJson() {

        ArrayList<UserMeal> lst_meal = StaticVarClass.lst_meals;

        //JSONObject meal = new JSONObject();
        JSONArray meals = new JSONArray();
        JSONObject everyMeal;
        try {

            for (int i = 0; i < lst_meal.size(); i++) {

                everyMeal = new JSONObject();
                everyMeal.put("device_id", lst_meal.get(i).getId());
                everyMeal.put("meal_id", lst_meal.get(i).getIngredient_id());
                everyMeal.put("date", lst_meal.get(i).getDate());
                everyMeal.put("is_custom", lst_meal.get(i).getIs_custom());
                everyMeal.put("serving_number", lst_meal.get(i).getServing_no());
                everyMeal.put("serving_size", lst_meal.get(i).getServing_size());
                everyMeal.put("counter_id", lst_meal.get(i).getCounter_id());

                meals.put(everyMeal);
            }

            return meals.toString();

        } catch (Exception e) {
            Log.e("build_meal", e + "");
            return "";
        }

    }

    private String GetFavJson() {

        ArrayList<UserFavourite> lst_favourite = StaticVarClass.lst_favourites_user;
        JSONArray fav = new JSONArray();
        JSONObject everyFav;
        try {

            for (int i = 0; i < lst_favourite.size(); i++) {
                everyFav = new JSONObject();
                everyFav.put("meal_id", lst_favourite.get(i).getMeal_id());
                everyFav.put("is_custom", lst_favourite.get(i).getIs_custom());
                fav.put(everyFav);
            }

        } catch (Exception e) {

            Log.e("build_fav_error", "" + e);
        }

        return fav.toString();

    }

    private void GetCalories() {
        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.GetCalories_URL);
        UserData userData = new UserData(context);
        String user_id = String.valueOf(userData.GetUserID());

        if (httpClient != null && httpPost != null) {
            // Set Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("user_id",
                    String.valueOf(user_id)));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();
                Log.e("get_calories_status", status + "");

                if (status == 200) {

                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(data);
                    double calories = jsonObject.getDouble("calories");
                    Log.e("updated_caloreis", calories + "");
                    userData.UpdateCalories(calories);


                }
            } catch (Exception e) {
                Log.e("calories_error", "" + e);
            }
        }
    }

    private void Logout() {

        HttpClient httpClient = Utility.SetTimeOut();
        HttpPost httpPost = Utility.SetHttpPost(StaticVarClass.LogoutURL);
        if (httpClient != null && httpPost != null) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String user_id = String.valueOf(new UserData(context).GetUserID());
            Log.e("user_id", user_id);
            params.add(new BasicNameValuePair("user_id", user_id));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = httpClient.execute(httpPost);
                int status = response.getStatusLine().getStatusCode();

                Log.e("logout_status", status + "");
                if (status == 200) {

                    UserData userData = new UserData(context);
                    userData.ClearUserData();
                    Ingredients.ClearAllFavourite(context);
                    UserMeal.ClearUserMeals(context);
                    Ingredients.RemoveCustomMeal(context);


                    logout_flag = true;
                    Log.e("logout", "done");
                }

            } catch (Exception e) {

                Log.e("logout_error", e + "");

            }
        }
    }

    public class FavMealAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected Void doInBackground(Void... params) {

            if (type == 1) {
                if (StaticVarClass.lst_favourites_user.size() > 0)
                    SetFavourite();

            } else if (type == 2) {
                if (StaticVarClass.lst_meals.size() > 0)
                    SetMeal();
            } else if (type == 3) {
                if (StaticVarClass.lst_removed_favourite.size() > 0)
                    RemoveFavourite();
            } else if (type == 4) {

                if (StaticVarClass.lst_custom_ingredients.size() > 0)
                    SetCustomMeal();

            } else if (type == 5 || type == 6 || type == 7) {

                if (type == 5)
                    GetCalories();

                SetStoredData();
                // and if type=6 call logout webservice

                if (type == 6 && stored_flag)
                    Logout();

            } else if (type == 8) {

                SetCalories();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (type == 6 || type == 1 || type == 2 || type == 8) {
                dialog = new ProgressDialog(context);
                dialog.setMessage(context.getString(R.string.wait_message));
                dialog.setCancelable(false);
                dialog.show();
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (type == 6) {

                dialog.dismiss();
                if (logout_flag) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    //activity.finish();
                    ((Activity) context).finish();

                } else {
                    Utility.ViewDialog(context, context.getString(R.string.fail));
                }

            } else if (type == 5) {

                if (stored_flag) {

                    // get data every open screen if user login
                    String date = Utility.GetStringDateNow();
                    GetDataLogin dataLogin = new GetDataLogin(context,
                            date, 0, 3);
                }

            } else if (type == 2) {

                dialog.dismiss();
                //String msg = (check_flag) ? context.getString(R.string.success_meal)
                //  : context.getString(R.string.fail);
                String msg = context.getString(R.string.success_meal);
                Utility.ViewDialog(context, msg);
            } else if (type == 1) {

                dialog.dismiss();
                // String msg = (check_flag) ? context.getString(R.string.success_favourite)
                //   : context.getString(R.string.fail);

                String msg = context.getString(R.string.success_favourite);
                Utility.ViewDialog(context, msg);

            } else if (type == 8) {

                dialog.dismiss();
                ((Activity) context).finish();
            }
        }
    }

}
