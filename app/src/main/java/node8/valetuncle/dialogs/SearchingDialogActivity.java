package node8.valetuncle.dialogs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import node8.valetuncle.R;
import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.TransactionAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.Transaction;
import node8.valetuncle.core.models.User;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchingDialogActivity extends AppCompatActivity {

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;

    AuthenticationAPI authenticationAPIWithToken;
    TransactionAPI transactionAPIWithToken;
    private Realm myRealm;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_dialog);
        ButterKnife.bind(this);


        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("Transaction.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorMainValet), android.graphics.PorterDuff.Mode.SRC_ATOP);


        transactionAPIWithToken = APIService.createService(TransactionAPI.class, UserData.getAuthToken());
        authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class,UserData.getAuthToken());


        String _promoCode = UserData.getPromoCode();
        String _remark = UserData.getRemark();
        String _pickup = UserData.getPickup();
        String _pickupAddress = UserData.getPickupAddress();
        String _actualLocation = UserData.getActualLocation();
        String _username = UserData.getUsername();

        Logger.e("insert trans " + _username + " "+ _pickupAddress);

        Call<Transaction> callInsert = transactionAPIWithToken.InsertTrans(UserData.getUsername(),_pickup,_remark,_pickupAddress,_actualLocation,_promoCode);
        callInsert.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if(response.isSuccessful()){
                    Transaction transaction = response.body();

                    Logger.e("sukses trans " + transaction.getTransactionid());

                    String _create = transaction.getCreate();

                    String _createDate = _create.substring(0,19) + "Z";

                    Logger.e("trans "+ _create + " == "+ _createDate);


                    UserData.setTransId(String.valueOf(transaction.getTransactionid()));
                    UserData.flushSettings();

                    myRealm.beginTransaction();
                    transaction.setCreate(_createDate);
                    myRealm.copyToRealmOrUpdate(transaction);
                    myRealm.commitTransaction();

                    final User user = new User(UserData.getObjectId());
                    user.setCurpage("2");
                    Call<User> callUpdate = authenticationAPIWithToken.update(user);

                    callUpdate.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if(response.isSuccessful()){

                                finish();
                                startActivity(new Intent(SearchingDialogActivity.this,ConfirmDialogActivity.class));

                            }else{
                                try {
                                    Logger.e("failure update user" + response.errorBody().string());
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


                }else{

                    try {
                        Logger.e("failure insert trans" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

                Logger.e(t.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
