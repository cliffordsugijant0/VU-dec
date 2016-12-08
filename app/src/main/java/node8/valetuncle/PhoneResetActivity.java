package node8.valetuncle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.hbb20.CountryCodePicker;
import com.orhanobut.logger.Logger;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.BaseResponse;
import node8.valetuncle.core.models.User;
import node8.valetuncle.helpers.Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PhoneResetActivity extends AppCompatActivity {


    AuthenticationAPI authenticationAPIWithoutToken = APIService.createService(AuthenticationAPI.class,null);
    AuthenticationAPI authenticationAPIWithToken;

    public User user;

    @BindView(R.id.include)
    RelativeLayout include;

    @BindView(R.id.ccp)
    CountryCodePicker ccp;


    @BindView(R.id.phone)
    EditText phoneET;

    @OnClick(R.id.sendBtn) void send(){

        User userUpdate = new User(this.user.getObjectid());
        final int randomCode = Utils.generateRandom();
        final String messageSMS = String.valueOf(randomCode);


        String _code = ccp.getFullNumberWithPlus();
        String _number = phoneET.getText().toString();
        final String _fullNumber = _code + _number;

        userUpdate.setVercode(String.valueOf(randomCode));
        userUpdate.setPhoneCode(_code);
        userUpdate.setPhoneNumber(_number);


        Call<User> call = authenticationAPIWithToken.update(userUpdate);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){

                    Call<BaseResponse> callIsms = authenticationAPIWithToken.isms(messageSMS,_fullNumber);

                    callIsms.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                            if(response.isSuccessful()){
                                Logger.e(response.body().toString());

                            }else{

                                try {
                                    Logger.e(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {

                            Logger.e(t.getMessage());

                            Utils.Toast(PhoneResetActivity.this,"Confirmation Code Sent!");
//                            getUser();
                            finish();

                            startActivity(new Intent(PhoneResetActivity.this,ConfirmationActivity.class));
                        }
                    });


                }else{

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Logger.e(t.getMessage());

            }
        });

//        finish();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_reset);
        ButterKnife.bind(this);


        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getUser();

    }


    public void getUser(){


        final User user = new User(UserData.getObjectId());

        user.setUsername(UserData.getUsername());
        user.setPassword(UserData.getPassword());

        Logger.e(UserData.getUsername() + UserData.getPassword());
        Call<BaseResponse> call = authenticationAPIWithoutToken.token(user.getUsername(),Utils.md5(user.getPassword()));


        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    BaseResponse baseResponse = response.body();
                    UserData.setAuthToken(baseResponse.getToken());
                    UserData.flushSettings();

                    Logger.e("token sukses " + UserData.getAuthToken());
                    authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class,baseResponse.getToken());

                    Call<User> callUser = authenticationAPIWithToken.detail(user);
                    callUser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

//                            ConfirmationActivity.this.confCode = String.valueOf(userA.getCode());
                            if(response.isSuccessful()){
//                                userA = response.body();
                                PhoneResetActivity.this.user = response.body();
                                Logger.e("sukses detail" + response.body().getObjectid());
                                Logger.e("sukses detail 2" + PhoneResetActivity.this.user.getObjectid());

                            }else{
                                try {
                                    Logger.e(response.errorBody().string());
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

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                Logger.e(t.getMessage());

            }
        });


    }

}
