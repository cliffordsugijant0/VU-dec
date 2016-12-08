package node8.valetuncle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.orhanobut.logger.Logger;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.BaseResponse;
import node8.valetuncle.core.models.User;
import node8.valetuncle.helpers.M;
import node8.valetuncle.helpers.Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.include)
    RelativeLayout include;

    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    @BindView(R.id.firstname)
    EditText fnET;

    @BindView(R.id.lastName)
    EditText lnET;

    @BindView(R.id.email)
    EditText emailET;

    @BindView(R.id.password)
    EditText passET;

    @BindView(R.id.confirmPass)
    EditText confirmET;


    @BindView(R.id.phone)
    EditText phoneET;

    @OnClick(R.id.createBtn) void create(){
        String _fn = fnET.getText().toString();
        String _ln = lnET.getText().toString();
        final String _name = _fn + _ln;

        final String _pass = passET.getText().toString();
        String _confirm = confirmET.getText().toString();

        final String _email = emailET.getText().toString();

        String _code = ccp.getFullNumberWithPlus();
        String _number = phoneET.getText().toString();
        final String _fullNumber = _code + _number;

        String _country = ccp.getSelectedCountryName();

        if(TextUtils.isEmpty(_fn)){

            Utils.showFieldError(fnET,"Please Fill Your First Name");
            return;
        }

        if(TextUtils.isEmpty(_ln)){
            Utils.showFieldError(lnET,"Please Fill Your Last Name");
            return;
        }


        if(TextUtils.isEmpty(_pass)){
            Utils.showFieldError(passET,"Please Fill Your Password");
            return;
        }

        if(TextUtils.isEmpty(_confirm)){
            Utils.showFieldError(confirmET,"Please Confirm Your Password");
            return;
        }

        if(TextUtils.isEmpty(_email)){
            Utils.showFieldError(emailET,"Please Fill Your Email");
            return;
        }

        if(!Utils.isEmailValid(_email)){
            Utils.showFieldError(emailET,"Please Input Valid Email");
            return;
        }

        if(TextUtils.isEmpty(_number)){
            Utils.showFieldError(phoneET,"Please Fill Your Phone Number");
            return;
        }

        if(!_pass.equals(_confirm)){
            Utils.showFieldError(passET,"Please Make Sure Your Password Same as Confirm Password");
            Utils.showFieldError(confirmET,"Please Make Sure Your Password Same as Confirm Password");

            return;
        }

        M.showDialog(RegisterActivity.this,false);
        final int randomCode = Utils.generateRandom();
        final String messageSMS = String.valueOf(randomCode);

        final AuthenticationAPI authenticationAPI = APIService.createService(AuthenticationAPI.class,null);
        final User user = new User(_email, _email, Utils.md5(_pass), _name, _number, _code,_country,String.valueOf(randomCode));
        Call<BaseResponse> call = authenticationAPI.register(user);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    BaseResponse baseResponse = response.body();
                    Logger.e("sukses signup");

                    UserData.setUserInfo(baseResponse.getPk(),_name,_email);
                    UserData.setPassword(_pass);

                    UserData.setConfirmationCode(String.valueOf(randomCode));


                    UserData.flushSettings();

                    user.setUsername(_email);
                    user.setPassword(_pass);



                    Call<BaseResponse> callToken = authenticationAPI.token(user.getUsername(),Utils.md5(user.getPassword()));

                    callToken.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                            if(response.isSuccessful()){
                                BaseResponse baseResponse = response.body();
                                UserData.setAuthToken(baseResponse.getToken());

                                Logger.e("sukses" + UserData.getAuthToken());

//                                AuthenticationAPI authenticationAPI = APIService.createService(AuthenticationAPI.class,UserData.getAuthToken());
//
                                Call<BaseResponse> callIsms = authenticationAPI.isms(messageSMS,_fullNumber);

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
                                        Toast.makeText(RegisterActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
                                        Logger.e(t.getMessage());
                                        M.hideDialog();
                                        finish();
                                        startActivity(new Intent(RegisterActivity.this,ConfirmationActivity.class));
                                    }
                                });

                            }else{

                                try {
                                    Logger.e("fail token" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {

                            Logger.e(t.getMessage());

                        }
                    });



                }else{

                    M.hideDialog();
                    try {
                        Logger.e("signup: "+response.errorBody().string());
                        Utils.Toast(getApplicationContext(),response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                M.hideDialog();
                Toast.makeText(RegisterActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
                Logger.e(t.getMessage());

            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private Bundle bundle;
    private String email,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegisterActivity.this,LoginRegisterActivity.class));
            }
        });

        ccp.setCountryForPhoneCode(62);

        bundle = getIntent().getExtras();
        if(bundle!= null){

            email = bundle.getString("email","");
            name = bundle.getString("name","");
            setValueSosMed();
        }else{
            setDummy();

        }

    }

    public void setDummy(){
        fnET.setText("maman");
        lnET.setText("maman");
        emailET.setText("maman1");
        passET.setText("maman");
        confirmET.setText("maman");
        phoneET.setText("8122166888");
    }

    public void setValueSosMed(){

        emailET.setText(email);

        String[] arrStr = name.split(" ");
        String _fn = arrStr[0];
        String _ln = "";
        if(arrStr.length > 1){
            _ln = arrStr[1];
        }

        fnET.setText(_fn);
        lnET.setText(_ln);
    }
}
