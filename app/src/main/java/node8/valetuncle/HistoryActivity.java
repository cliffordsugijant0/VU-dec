package node8.valetuncle;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import node8.valetuncle.R;

import node8.valetuncle.adapters.HistoryAdapter;
import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.History;
import node8.valetuncle.helpers.EndlessRecyclerOnScrollListener;
import node8.valetuncle.helpers.SimpleDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.top_bar)
    LinearLayout include;

    @BindView(R.id.historyList) RecyclerView historyList;

    public LinearLayoutManager layoutManager;
    private static HistoryAdapter mHistoryAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RealmResults<History> mHistoryModel;


    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;


    AuthenticationAPI authenticationAPIWithToken;

    private Realm myRealm;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        TextView tv = (TextView)include.findViewById(R.id.topTV);
        tv.setText("Trip History");


        TextView titleTV = (TextView)include.findViewById(R.id.titleTV);
        titleTV.setText("Trip History");

        ImageButton backBtn = (ImageButton) include.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("History.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        layoutManager.setStackFromEnd(true);
        historyList.setLayoutManager(layoutManager);


//        mHistoryModel = new ArrayList<>();

        mHistoryModel = myRealm.where(History.class).findAll();

        Logger.e("count " + mHistoryModel.first().getFee());

        mHistoryAdapter = new HistoryAdapter(this, mHistoryModel);
        historyList.setAdapter(mHistoryAdapter);

        mHistoryAdapter.setOrders(mHistoryModel);

        historyList.addItemDecoration(new SimpleDividerItemDecoration(this));

//
//        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//
////                skip+=5;
////                jmlTampil +=5;
////                getOderList();
//            }
//        };
        historyList.setOnScrollListener(endlessRecyclerOnScrollListener);


        authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class, UserData.getAuthToken());


    }

}
