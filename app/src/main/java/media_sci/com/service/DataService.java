package media_sci.com.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import media_sci.com.models.UserData;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.GetData;
import media_sci.com.utility.GetDataLogin;
import media_sci.com.utility.Utility;


public class DataService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {


            Bundle bundle = intent.getExtras();
            int type = bundle.getInt("type");

            Log.e("====", "StartCommand / " + type);

            if (type == 1) {

                Log.e("service_type", "1");
                GetLastUpdateData();


            } else if (type == 2) {

                Log.e("service_type", "2");
                String date = Utility.GetStringDateNow();
                GetDataLogin dataLogin = new GetDataLogin(getApplicationContext(),
                        date, 0, 1);
            }
        } catch (Exception e) {
            Log.e("service_error", e + "");
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void GetLastUpdateData() {

        GetData getData = new GetData(getApplicationContext(), 2);
        UserData userData = new UserData(getApplicationContext());

        if (userData.GetUserID() != -1) {

            FavAndMeal favAndMeal = new FavAndMeal(getApplicationContext(), 5);

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("====", "IBind");
        return null;
    }
}
