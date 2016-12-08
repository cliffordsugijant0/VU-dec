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

import node8.valetuncle.R;

import node8.valetuncle.adapters.PromoAdapter;
import node8.valetuncle.core.models.Promo;
import node8.valetuncle.helpers.EndlessRecyclerOnScrollListener;
import node8.valetuncle.helpers.SimpleDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PromoActivity extends AppCompatActivity {

    @BindView(R.id.top_bar)
    LinearLayout include;

    @BindView(R.id.historyList)
    RecyclerView promoList;

    public LinearLayoutManager layoutManager;
    private static PromoAdapter mPromoAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RealmResults<Promo> mPromoModel;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    
    private Realm myRealm;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        ButterKnife.bind(this);

        TextView tv = (TextView)include.findViewById(R.id.topTV);
        tv.setText("Promotion");


        TextView titleTV = (TextView)include.findViewById(R.id.titleTV);
        titleTV.setText("ValetUncle Promo");

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
                                .name("Promo.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        //setup recycler

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        layoutManager.setStackFromEnd(true);
        promoList.setLayoutManager(layoutManager);


//        RealmResults<Promo> results =
        mPromoModel = myRealm.where(Promo.class).findAll();

        mPromoAdapter = new PromoAdapter(this, mPromoModel);
        promoList.setAdapter(mPromoAdapter);


        mPromoAdapter.setOrders(mPromoModel);
        promoList.addItemDecoration(new SimpleDividerItemDecoration(this));


        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {

//                skip+=5;
//                jmlTampil +=5;
//                getOderList();
            }
        };
        promoList.setOnScrollListener(endlessRecyclerOnScrollListener);


    }
}
