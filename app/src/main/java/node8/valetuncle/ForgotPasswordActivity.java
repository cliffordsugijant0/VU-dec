package node8.valetuncle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.models.BaseResponse;
import node8.valetuncle.helpers.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.include)
    RelativeLayout include;
    @BindView(R.id.username)
    EditText usernameET;

    @OnClick(R.id.sendBtn) void send(){
        String _username = usernameET.getText().toString();

        finish();
        Call<BaseResponse> callReset = authenticationAPI.ForgotPassword(_username);
        callReset.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if(response.isSuccessful()){
                    Utils.Toast(ForgotPasswordActivity.this,
                            "New password has been sent to your account!");


                }else{
                    Utils.Toast(ForgotPasswordActivity.this,
                            "Your Email is Not Registered in Valet Uncle. Please Create An Account.");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
            }
        });

    }


    final AuthenticationAPI authenticationAPI = APIService.createService(AuthenticationAPI.class,null);

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);


        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
