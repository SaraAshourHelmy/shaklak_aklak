package media_sci.com.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.Utility;

public class InternetReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (Utility.HaveNetworkConnection(context)) {
                Log.e("state", "on");
                FavAndMeal favAndMeal = new FavAndMeal(context, 7);
            } else {
                Log.e("state", "off");
            }
        } catch (Exception e) {

            Log.e("receiver_error", e + "");
        }
    }
}
