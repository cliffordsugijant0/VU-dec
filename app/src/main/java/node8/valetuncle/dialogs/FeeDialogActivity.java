package node8.valetuncle.dialogs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import node8.valetuncle.R;
import node8.valetuncle.core.APIService;
import node8.valetuncle.core.TransactionAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.Fee;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FeeDialogActivity extends AppCompatActivity {


    TransactionAPI transactionAPIWithToken;


    @BindView(R.id.feeTV)
    TextView feeTV;

    @OnClick(R.id.noBtn) void cancel(){
        finish();
        getFeeDetail();

    }

    @OnClick(R.id.yesBtn) void confirm(){
        finish();
        startActivity(new Intent(this,SearchingDialogActivity.class));

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_dialog);
        ButterKnife.bind(this);

        feeTV.setText(UserData.getFeeMsg());

        transactionAPIWithToken = APIService.createService(TransactionAPI.class,UserData.getAuthToken());
    }
    public void getFeeDetail(){

        Call<Fee> callFee = transactionAPIWithToken.transFee(UserData.getUsername());
        callFee.enqueue(new Callback<Fee>() {
            @Override
            public void onResponse(Call<Fee> call, Response<Fee> response) {

                if(response.isSuccessful()){

                    Fee fee = response.body();
                    UserData.setFee(String.valueOf(fee.getFee()));
                    UserData.setFeeMsg(fee.getMessage());

                }else{

                }
            }

            @Override
            public void onFailure(Call<Fee> call, Throwable t) {

            }
        });

    }

}
