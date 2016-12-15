package node8.valetuncle.dialogs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import node8.valetuncle.MapsActivity;
import node8.valetuncle.R;
import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.TransactionAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.Transaction;
import node8.valetuncle.core.models.User;
import node8.valetuncle.helpers.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ConfirmDialogActivity extends AppCompatActivity {

    TransactionAPI transactionAPIWithToken;
    AuthenticationAPI authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class, UserData.getAuthToken());

    public static String FACEBOOK_URL = "https://www.facebook.com/ValetUncle/";
    public static String FACEBOOK_PAGE_ID = "ValetUncle";


    @BindView(R.id.feeTV)
    TextView feeTV;
    @BindView(R.id.dateTV)
    TextView dateTV;
    @BindView(R.id.addressTV)
    TextView addressTV;

    @OnClick(R.id.arrivedBtn) void arrived(){
        Logger.e("trans Id " + UserData.getTransId() + UserData.getUsername());

        finish();
        Intent intent = new Intent(ConfirmDialogActivity.this,FinishingDialogActivity.class);
        intent.putExtra("state","finish");
        startActivity(intent);

//        Call<TransactionDetail> callFinish = transactionAPIWithToken.FinishTrans(UserData.getUsername(),Integer.parseInt(UserData.getTransId()));
//        callFinish.enqueue(new Callback<TransactionDetail>() {
//            @Override
//            public void onResponse(Call<TransactionDetail> call, Response<TransactionDetail> response) {
//                if(response.isSuccessful()){
//
//
//                    finish();
//                    startActivity(new Intent(ConfirmDialogActivity.this,FinishingDialogActivity.class));
//                }else{
//                    try {
//                        Logger.e("error finish "+ response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TransactionDetail> call, Throwable t) {
//                Logger.e("error finishh" + t.getMessage());
//
//            }
//        });

    }

    @OnClick(R.id.cancelBtn) void cancel(){



        finish();
        Intent intent = new Intent(ConfirmDialogActivity.this,FinishingDialogActivity.class);
        intent.putExtra("state","cancel");
        startActivity(intent);
    }


    @OnClick(R.id.shareBtn) void share(){
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);

    }


    @OnClick(R.id.reviewBtn) void review(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                ("market://details?id=node8.valetuncle")));

    }

    @OnClick(R.id.reportBtn) void report(){
        startActivity(new Intent(ConfirmDialogActivity.this, ReportDialogActivity.class));

    }

    private Realm myRealm,userRealm;
    private int transId;
    private Transaction transactionDetail;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_dialog);
        ButterKnife.bind(this);


        transactionAPIWithToken = APIService.createService(TransactionAPI.class, UserData.getAuthToken());
        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("Transaction.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        getUser();
        getTrans();


    }



    public void getUser(){

        userRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("User.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        User user = userRealm.where(User.class).findFirst();

        transId = user.getTransactionid();





    }
    public void getTrans(){
        transactionDetail = myRealm.where(Transaction.class).findFirst();
        if(transactionDetail!=null) {
            feeTV.setText("$" + transactionDetail.getFee());
            addressTV.setText(transactionDetail.getPickupaddress());
            dateTV.setText(Utils.FormatDate(transactionDetail.getCreate()));
//            try {
//                dateTV.setText(Utils.FormatDate(transactionDetail.getCreate()));
//            }catch (IllegalArgumentException e){
//                dateTV.setText(Utils.FormatDate2(transactionDetail.getCreate()));
//
//            }
        }else{

        }

    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Utils.Toast(getApplicationContext(),message);
            update();
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(i);
            finish();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        ConfirmDialogActivity.this.getApplicationContext().registerReceiver(broadcastReceiver, new IntentFilter("back"));
    }

    @Override
    public void onPause() {
        super.onPause();
        ConfirmDialogActivity.this.getApplicationContext().unregisterReceiver(broadcastReceiver);
    }

    public void update(){
        User user = new User(UserData.getObjectId());
        user.setCurpage("0");

        Call<User> call = authenticationAPIWithToken.update(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    Log.v("enter update","ENTER");
                }else{
                    try {
                        Log.v("enter update","failed "+response.code()+" "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Utils.Toast(ConfirmDialogActivity.this,getString(R.string.InternetConnection));

                Logger.e(t.getMessage());

            }
        });

    }
}
