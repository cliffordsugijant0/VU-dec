package node8.valetuncle.dialogs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import node8.valetuncle.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReportDialogActivity extends AppCompatActivity {

    @OnClick(R.id.cancelBtn) void cancel(){
        finish();
    }

    @OnClick(R.id.submitBtn) void submit(){
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dialog);
        ButterKnife.bind(this);
    }
}
