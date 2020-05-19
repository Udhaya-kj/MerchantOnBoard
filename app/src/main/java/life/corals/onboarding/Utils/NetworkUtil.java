package life.corals.onboarding.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static boolean getConnectivityStatusString(Context context) {

        boolean status=false;
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status=true;

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status=true;

            }
        } else {

            status=false;
        }
       return  status;
    }

}
