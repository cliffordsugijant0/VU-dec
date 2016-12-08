package node8.valetuncle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.BaseResponse;
import node8.valetuncle.core.models.ResponseUser;
import node8.valetuncle.core.models.User;
import node8.valetuncle.helpers.M;
import node8.valetuncle.helpers.Utils;
import node8.valetuncle.views.ZanyEditText;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ConfirmationActivity extends AppCompatActivity {

    AuthenticationAPI authenticationAPIWithoutToken = APIService.createService(AuthenticationAPI.class,null);
    AuthenticationAPI authenticationAPIWithToken;

    @BindView(R.id.include)
    RelativeLayout include;

    @BindView(R.id.number1)
    ZanyEditText n1;
    @BindView(R.id.number2)
    ZanyEditText n2;
    @BindView(R.id.number3)
    ZanyEditText n3;
    @BindView(R.id.number4)
    ZanyEditText n4;

    public ResponseUser userA;
    public User user;
    @OnClick(R.id.nextBtn) void next(){
        String number1 = n1.getText().toString();
        String number2 = n2.getText().toString();
        String number3 = n3.getText().toString();

        String number4 = n4.getText().toString();

        final String inputUser = number1+number2+number3+number4;

        Logger.e("conf code: " + user.getVercode() + " "+ inputUser);

        if(inputUser.equals(String.valueOf(user.getVercode()))){

            User userUpdate = new User(this.user.getObjectid());
//            userUpdate.setStatuslogin(1);
            userUpdate.setConfirm("verified");
            Call<User> call = authenticationAPIWithToken.update(userUpdate);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if(response.isSuccessful()){

                        finish();
                        UserData.setLoggedIn("true");
                        UserData.flushSettings();
                        startActivity(new Intent(ConfirmationActivity.this,MapsActivity.class));

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    Logger.e(t.getMessage());

                }
            });

        }else{


            Utils.Toast(getApplicationContext(),"Confirmation Code is wrong");
        }

    }

    @OnClick(R.id.changeNumberBtn) void changeNumber(){
        finish();
        startActivity(new Intent(ConfirmationActivity.this,PhoneResetActivity.class));

    }

    @OnClick(R.id.clearBtn) void clearField(){
        n1.setText("");
        n2.setText("");
        n3.setText("");
        n4.setText("");
        n1.requestFocus();

    }


    @OnClick(R.id.resendBtn) void resendCode(){

        User userUpdate = new User(this.user.getObjectid());
        final int randomCode = Utils.generateRandom();
        final String messageSMS = String.valueOf(randomCode);
        final String _fullNumber = user.getPhoneCode() + user.getPhoneNumber();

        userUpdate.setVercode(String.valueOf(randomCode));
        Call<User> call = authenticationAPIWithToken.update(userUpdate);
        M.showDialog(ConfirmationActivity.this,false);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){

                    Call<BaseResponse> callIsms = authenticationAPIWithoutToken.isms(messageSMS,_fullNumber);

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
                            M.hideDialog();

                            Utils.Toast(getApplicationContext(),"Confirmation Code Sent!");
                            getUser();
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

//
//
//
//        final ResponseUser user = new ResponseUser(UserData.getObjectId());
//        user.setVercode(randomCode);
//        Call<RequestUser> call = authenticationAPIWithToken.update(user);
//
//        call.enqueue(new Callback<RequestUser>() {
//            @Override
//            public void onResponse(Call<RequestUser> call, Response<RequestUser> response) {
//                Call<BaseResponse> callIsms = authenticationAPIWithToken.isms(messageSMS,_fullNumber);
//
//                callIsms.enqueue(new Callback<BaseResponse>() {
//                    @Override
//                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//
//                        if(response.isSuccessful()){
//                            Logger.e(response.body().toString());
//
//                        }else{
//
//                            try {
//                                Logger.e(response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BaseResponse> call, Throwable t) {
//
//                        Logger.e(t.getMessage());
//
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(Call<RequestUser> call, Throwable t) {
//
//            }
//        });
//
//

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ButterKnife.bind(this);

        n1.setPrevEditText(null);
        //n1.setNextEditText(n2);
        n2.setPrevEditText(n1);
        //n2.setNextEditText(n3);
        n3.setPrevEditText(n2);
        //n3.setNextEditText(n4);
        n4.setPrevEditText(n3);
        //n4.setNextEditText(null);

        moveFocus2();

        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ConfirmationActivity.this,LoginRegisterActivity.class));
            }
        });

//        confCode = UserData.getConfirmationCode();

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
                                ConfirmationActivity.this.user = response.body();
                                Logger.e("sukses detail" + response.body().getObjectid());
                                Logger.e("sukses detail 2" + ConfirmationActivity.this.user.getVercode());

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


    public void moveFocus2(){
        n1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (n1.getText().toString().length() == 1)
                {
                    n2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        n2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (n2.getText().toString().length() == 1) {
                    n3.requestFocus();
                } else if (n2.getText().toString().length() == 0)
                    n1.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        n3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (n3.getText().toString().length() == 1) {
                    n4.requestFocus();
                } else if (n3.getText().toString().length() == 0)
                    n2.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        n4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (n4.getText().toString().length() == 0)
                    n3.requestFocus();
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    }
}
