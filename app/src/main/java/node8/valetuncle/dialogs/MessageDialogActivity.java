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

public class MessageDialogActivity extends AppCompatActivity {


    TransactionAPI transactionAPIWithToken;


    @BindView(R.id.feeTV)
    TextView feeTV;

    @OnClick(R.id.okBtn) void ok(){
        finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_dialog);
        ButterKnife.bind(this);

        feeTV.setText(UserData.getFeeMsg());
    }

}
