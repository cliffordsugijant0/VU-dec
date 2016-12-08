package node8.valetuncle.dialogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import node8.valetuncle.R;
import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.Promo;
import node8.valetuncle.core.models.User;
import node8.valetuncle.helpers.M;
import node8.valetuncle.helpers.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoDialogActivity extends AppCompatActivity {

    @BindView(R.id.promoImage)
    ImageView promoImage;


    @OnClick(R.id.closeBtn) void close(){
//        updateUser();
        finish();
    }

    AuthenticationAPI authenticationAPIWithToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_dialog);
        ButterKnife.bind(this);

        authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class, UserData.getAuthToken());

        getPromo();
    }

    public void getPromo(){
        Call<Promo> callPromo = authenticationAPIWithToken.RandomPromo();

        callPromo.enqueue(new Callback<Promo>() {
            @Override
            public void onResponse(Call<Promo> call, Response<Promo> response) {
                if(response.isSuccessful()){
                    Promo promo = response.body();

                    Picasso.with(PromoDialogActivity.this).load(promo.getPromoPicture()).into(promoImage);

                }
            }

            @Override
            public void onFailure(Call<Promo> call, Throwable t) {

            }
        });
    }

    private void updateUser(){
        User userUpdate = new User(UserData.getObjectId());

        userUpdate.setShowPromo(1);

        Call<User> call = authenticationAPIWithToken.update(userUpdate);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){




                }else{
                    Utils.Toast(PromoDialogActivity.this,"Please try again later!");

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Logger.e(t.getMessage());

            }
        });

    }

}
