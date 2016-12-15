package node8.valetuncle.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import node8.valetuncle.BuildConfig;
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

    public static void updateApp(final Activity act) {
        final String appPackageName = BuildConfig.APPLICATION_ID;
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder
                .setTitle(act.getString(R.string.dialog_title_update_app))
                .setMessage(act.getString(R.string.dialog_google_credentials_message))
                .setNegativeButton(act.getString(R.string.Cancel), null)
                .setPositiveButton(act.getString(R.string.GotIt), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (ActivityNotFoundException anfe) {
                            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
