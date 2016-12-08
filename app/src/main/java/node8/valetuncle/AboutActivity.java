package node8.valetuncle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import node8.valetuncle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.top_bar)
    LinearLayout include;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

//        LinearLayout ll = (LinearLayout)findViewById(R.id.top_bar);
        TextView tv = (TextView)include.findViewById(R.id.topTV);
        tv.setText("About");


        TextView titleTV = (TextView)include.findViewById(R.id.titleTV);
        titleTV.setText("About ValetUncle");

        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
