package node8.valetuncle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import node8.valetuncle.R;

import node8.valetuncle.core.models.Promo;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PromoDetailActivity extends AppCompatActivity {

    @BindView(R.id.promoImage)
    ImageView promoImage;

    @BindView(R.id.promoTV)
    TextView promoTV;

    @BindView(R.id.top_bar)
    LinearLayout include;


    Bundle b;
    private int promoId;

    private Realm myRealm;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detail);
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


        b = getIntent().getExtras();

        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(getApplicationContext())
                                .name("Promo.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        getDetail();

    }

    private void getDetail(){
        promoId = b.getInt("Id");
        Promo promoDetail = myRealm.where(Promo.class).equalTo("id",promoId).findFirst();



        Picasso.with(this).load(promoDetail.getPromoPicture()).into(promoImage);
        promoTV.setText(promoDetail.getPromoDetail());



    }
}
