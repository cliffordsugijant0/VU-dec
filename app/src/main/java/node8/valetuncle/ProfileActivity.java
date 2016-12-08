package node8.valetuncle;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.User;
import node8.valetuncle.helpers.M;
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

public class ProfileActivity extends AppCompatActivity {

    AuthenticationAPI authenticationAPIWithoutToken = APIService.createService(AuthenticationAPI.class,null);
    AuthenticationAPI authenticationAPIWithToken;

    public User user;


    private Realm myRealm,userRealm;

    @BindView(R.id.top_bar)
    LinearLayout include;


    @BindView(R.id.curPassLayout)
    TextInputLayout curPassLayout;

    @BindView(R.id.newPassLayout)
    TextInputLayout newPassLayout;

    @BindView(R.id.confirmPassLayout)
    TextInputLayout confirmPassLayout;

    @BindView(R.id.fnET)
    EditText fnET;

    @BindView(R.id.emailET)
    EditText emailET;

    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    @BindView(R.id.phone)
    EditText mobileET;

    @BindView(R.id.addressET)
    EditText addressET;

    @BindView(R.id.curPass)
    EditText curPassET;


    @BindView(R.id.newPass)
    EditText newPassET;


    @BindView(R.id.UserConfirmPassword)
    EditText confirmPassET;


    @OnClick(R.id.editBtn) void edit(final Button btn){
        if(btn.getText().equals("EDIT")) {
            btn.setText("SAVE");
            curPassLayout.setVisibility(View.VISIBLE);
            newPassLayout.setVisibility(View.VISIBLE);
            confirmPassLayout.setVisibility(View.VISIBLE);

            fnET.setEnabled(true);
            mobileET.setEnabled(true);
            addressET.setEnabled(true);
        }else{

            fnET.setEnabled(false);
            mobileET.setEnabled(false);
            addressET.setEnabled(false);

            String _fullname = fnET.getText().toString();
            String _code = ccp.getFullNumberWithPlus();
            String _number = mobileET.getText().toString();
            String _newPass = newPassET.getText().toString();
            String _confirmPass = confirmPassET.getText().toString();
            String _address = addressET.getText().toString();


            if(!_newPass.equals(_confirmPass)){
                Utils.showFieldError(newPassET,"Please Make Sure Your Password Same as Confirm Password");
                Utils.showFieldError(confirmPassET,"Please Make Sure Your Password Same as Confirm Password");

                return;
            }

            User userUpdate = new User(UserData.getObjectId());
            if(!TextUtils.isEmpty(_newPass)){
                userUpdate.setPassword(Utils.md5(_newPass));

            }

            userUpdate.setCompleteaddress(_address);
            userUpdate.setPhoneNumber(_number);
            userUpdate.setPhoneCode(_code);
            userUpdate.setName(_fullname);


            M.showDialog(ProfileActivity.this,false);
            Call<User> call = authenticationAPIWithToken.update(userUpdate);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    M.hideDialog();
                    if(response.isSuccessful()){



                        btn.setText("EDIT");
                        curPassLayout.setVisibility(View.GONE);
                        newPassLayout.setVisibility(View.GONE);
                        confirmPassLayout.setVisibility(View.GONE);

                        getUserSync();


                    }else{
                        Utils.Toast(ProfileActivity.this,"Please try again later!");

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    Logger.e(t.getMessage());

                }
            });

        }


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        TextView tv = (TextView)include.findViewById(R.id.topTV);
        tv.setText("Profile");


        TextView titleTV = (TextView)include.findViewById(R.id.titleTV);
        titleTV.setText("YOUR ACCOUNT DETAILS");


        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ccp.setCountryForPhoneCode(65);
        authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class,UserData.getAuthToken());
        getUser();
    }

    public void getUser(){

        userRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("User.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        user = userRealm.where(User.class).findFirst();
//        user.setUsername(UserData.getUsername());
//        user.setPassword(UserData.getPassword());


                    fnET.setText(user.getName());
                    emailET.setText(user.getEmail());
                    String fullCode = user.getPhoneCode();
//                    Logger.e();
                    Logger.e("phone code" + fullCode + " "+ fullCode.substring(1));
                    ccp.setCountryForPhoneCode(Integer.parseInt(fullCode.substring(1)));
//                    ccp.setDefaultCountryUsingPhoneCode(Integer.parseInt(fullCode.substring(1)));
                    mobileET.setText(user.getPhoneNumber());

        addressET.setText(user.getCompleteaddress());
                    curPassET.setText(UserData.getPassword());



    }
    public void getUserSync(){
        final User user = new User(UserData.getObjectId());

        user.setUsername(UserData.getUsername());
        user.setPassword(UserData.getPassword());


        Call<User> callUser = authenticationAPIWithToken.detail(user);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

//                M.hideDialog();
                if(response.isSuccessful()){

                    User user = response.body();


                    user.setUsername(UserData.getUsername());
                    user.setPassword(UserData.getPassword());

                    userRealm.beginTransaction();
                    userRealm.copyToRealmOrUpdate(user);
                    userRealm.commitTransaction();



                }else{
//                    M.hideDialog();
                    try {
                        Logger.e(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
//                M.hideDialog();
                Logger.e(t.getMessage());

            }
        });


    }
}
