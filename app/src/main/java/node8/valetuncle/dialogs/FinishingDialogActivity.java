package node8.valetuncle.dialogs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import node8.valetuncle.MapsActivity;
import node8.valetuncle.R;
import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.TransactionAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.CancelResponse;
import node8.valetuncle.core.models.Transaction;
import node8.valetuncle.core.models.TransactionDetail;
import node8.valetuncle.core.models.User;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FinishingDialogActivity extends AppCompatActivity {

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;

    TransactionAPI transactionAPIWithToken;
    AuthenticationAPI authenticationAPIWithToken;
    private Realm myRealm,userRealm;
    private Bundle b;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishing_dialog);
        ButterKnife.bind(this);

        b = getIntent().getExtras();

        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("Transaction.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );


        userRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("User.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorMainValet), android.graphics.PorterDuff.Mode.SRC_ATOP);

        transactionAPIWithToken = APIService.createService(TransactionAPI.class, UserData.getAuthToken());

        authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class,UserData.getAuthToken());

        String _state = b.getString("state");

        if(_state.equals("finish")){
            finishTrans();
        }else{
            cancelTrans();
        }




    }

    public void updateUser(){
//        User user = userRealm.where(User.class).findFirst();

        int verCode = 0;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            verCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        User user = new User(UserData.getObjectId());
        Logger.e("update user" + user.getObjectid());
//        userRealm.beginTransaction();

        user.setVersion(String.valueOf(verCode));
        user.setCurpage("0");
//        userRealm.copyToRealmOrUpdate(user);
//        userRealm.commitTransaction();
        Call<User> call = authenticationAPIWithToken.update(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){

                    Logger.e("update user finsh");

                    finish();
                    startActivity(new Intent(FinishingDialogActivity.this,MapsActivity.class));

                }else{
                    try {
                        Logger.e("error user update" +response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Logger.e(t.getMessage());

            }
        });
    }

    public void cancelTrans(){
        Logger.e("cancel trans id" + UserData.getTransId());
        Call<CancelResponse> callFinish = transactionAPIWithToken.CancelTrans(UserData.getUsername(),Integer.parseInt(UserData.getTransId()));
        callFinish.enqueue(new Callback<CancelResponse>() {
            @Override
            public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                if(response.isSuccessful()){

                    RealmResults<Transaction> transaction = myRealm.where(Transaction.class).findAll();

                    myRealm.beginTransaction();
                    transaction.deleteAllFromRealm();
                    myRealm.commitTransaction();
                    updateUser();

                }else{
                    try {
                        Logger.e("error cancel "+ response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelResponse> call, Throwable t) {
                Logger.e("error finishh" + t.getMessage());

            }
        });

    }

    public void finishTrans(){
        Call<TransactionDetail> callFinish = transactionAPIWithToken.FinishTrans(UserData.getUsername(),Integer.parseInt(UserData.getTransId()));
        callFinish.enqueue(new Callback<TransactionDetail>() {
            @Override
            public void onResponse(Call<TransactionDetail> call, Response<TransactionDetail> response) {
                if(response.isSuccessful()){

                    RealmResults<Transaction> transaction = myRealm.where(Transaction.class).findAll();

                    myRealm.beginTransaction();
                    transaction.deleteAllFromRealm();
                    myRealm.commitTransaction();
                    Logger.e("sukses finish");


                    updateUser();

                }else{
                    try {
                        Logger.e("error finish "+ response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionDetail> call, Throwable t) {
                Logger.e("error finishh" + t.getMessage());

            }
        });
    }
}
