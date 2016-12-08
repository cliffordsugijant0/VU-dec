package node8.valetuncle.helpers;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import node8.valetuncle.R;


/**
 * Created by stevsut on 9/24/2015.
 */
public class M {
    private static SharedPreferences mSharedPreferences;
    static ProgressDialog pDialog;
    private static NotificationManager manager;

    /**
     * method to show the progress dialog
     * @param mContext
     */
    public static void showDialog(Context mContext,Boolean cancel) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.please_wait));
        pDialog.setIndeterminate(true);

        pDialog.setCancelable(cancel);
        pDialog.show();
    }

    /**
     * method to hide the progress dialog
     */
    public static void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    /**
     * method to check if there is a connection
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
