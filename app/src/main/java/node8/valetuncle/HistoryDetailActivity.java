package node8.valetuncle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import node8.valetuncle.R;

import node8.valetuncle.core.models.History;
import node8.valetuncle.helpers.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryDetailActivity extends AppCompatActivity {



    @BindView(R.id.top_bar)
    LinearLayout include;

    @BindView(R.id.pickup) TextView pickupTV;
    @BindView(R.id.datePickup) TextView datePickupTV;
    @BindView(R.id.goalAddress) TextView goalTV;
    @BindView(R.id.dateGoal) TextView dateGoalTV;

    @BindView(R.id.feeTV) TextView feeTV;

    private Bundle b;
    private int historyId;

    private Realm myRealm;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        ButterKnife.bind(this);

        b = getIntent().getExtras();
        TextView tv = (TextView)include.findViewById(R.id.topTV);
        tv.setText("Trip History");


        TextView titleTV = (TextView)include.findViewById(R.id.titleTV);
        titleTV.setText("Trip History Detail");

        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(getApplicationContext())
                                .name("History.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        getDetail();

    }


    private void getDetail(){
        historyId = b.getInt("Id");
        History historyDetail = myRealm.where(History.class).equalTo("objectid",historyId).findFirst();

        pickupTV.setText(historyDetail.getPickupaddress());
        datePickupTV.setText(Utils.FormatDate(historyDetail.getCreate()));

        goalTV.setText(historyDetail.getDestinationaddress());
        dateGoalTV.setText(Utils.FormatDate(historyDetail.getTransactionCreated()));

        feeTV.setText("$" + historyDetail.getFee());


    }
}
