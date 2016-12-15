package node8.valetuncle;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;

import java.io.IOException;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;
import node8.valetuncle.core.models.BaseResponse;
import node8.valetuncle.core.models.User;
import node8.valetuncle.dialogs.FeeDialogActivity;
import node8.valetuncle.helpers.M;
import node8.valetuncle.helpers.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DispatchActivity extends AppCompatActivity {

    @BindView(R.id.imageView8)
    ImageView motionImage;
    AuthenticationAPI authenticationAPIWithoutToken = APIService.createService(AuthenticationAPI.class,null);

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        ButterKnife.bind(this);

        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

//Setup anim with desired properties
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(7500); //Put desired duration per anim cycle here, in milliseconds

        motionImage.setAnimation(anim);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity

//                runDispatch();
                if(UserData.getUsername()!=null) getStatus();
//                if(UserData.getUsername()!=null) new GetVersionCode().execute();
                else startActivity(new Intent(DispatchActivity.this, LoginRegisterActivity.class));
                // close this activity

            }
        }, 2250);

    }

    private void runDispatch(){
//        finish();
//        startActivity(new Intent(DispatchActivity.this,LoginRegisterActivity.class));
        Intent intent;
        if(TextUtils.isEmpty(UserData.getLoggedIn())) {
            intent = new Intent(DispatchActivity.this, LoginRegisterActivity.class);
        }else{
            intent = new Intent(DispatchActivity.this, MapsActivity.class);
        }
        finish();
        startActivity(intent);
    }

    public void getStatus(){

        Call<User> call = authenticationAPIWithoutToken.status(UserData.getUsername());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                Log.v("get status",response.body().getStatuslogin()+" ");
                if(response.isSuccessful()){
                    User userResponse = response.body();
                    finish();
                    Log.v("status-login",userResponse.getStatuslogin()+"");
                    if(userResponse.getStatuslogin()==1)
                        startActivity(new Intent(DispatchActivity.this, MapsActivity.class));
                    else {
//                        if(userResponse.getCurpage().equals("5")){
//                            Utils.Toast(DispatchActivity.this,getString(R.string.maintenance_message));
                        startActivity(new Intent(DispatchActivity.this, LoginRegisterActivity.class));
//                        }
                    }

                }else{
                    Log.v("status-login","failed "+response.code()+" "+response.errorBody());
//                    startActivity(new Intent(DispatchActivity.this, LoginRegisterActivity.class));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Utils.Toast(DispatchActivity.this,getString(R.string.InternetConnection));
                Logger.e(t.getMessage());
            }
        });
    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {
        String currentVersion;

        public GetVersionCode() {
            try {
                this.currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    M.updateApp(DispatchActivity.this);
                }
                else
                    getStatus();
            }
            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        }
    }
}
