package node8.valetuncle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orhanobut.logger.Logger;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.TransactionAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.Promo;
import node8.valetuncle.core.models.Transaction;
import node8.valetuncle.core.models.User;
import node8.valetuncle.dialogs.ConfirmDialogActivity;
import node8.valetuncle.dialogs.FeeDialogActivity;
import node8.valetuncle.dialogs.MessageDialogActivity;
import node8.valetuncle.helpers.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsDetailActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Bundle b;
    AuthenticationAPI authenticationAPIWithToken;
    TransactionAPI transactionAPIWithToken;

    private int transId;

    private Realm userRealm;
    @BindView(R.id.addTxt)
    TextView addTxt;

    @OnClick(R.id.feeBtn) void showMessage(){

        Intent intent = new Intent(this,MessageDialogActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.backBtn) void back(){
        finish();
        startActivity(new Intent(this,MapsActivity.class));

    }
    @OnClick(R.id.reqBtn) void request(){
//        startActivity(new Intent(this,FeeDialogActivity.class));
        String _promoCode = promoET.getText().toString();
        String _remark = remarkET.getText().toString();
        String _pickup = loc.toString();

        Intent intent = new Intent(this,FeeDialogActivity.class);

//        intent.putExtra("promo","0");

        UserData.setTransInfo(_promoCode,_remark,_pickup,pickUp,actualLocation);
        UserData.flushSettings();
        if(!TextUtils.isEmpty(_promoCode)){
//            intent.putExtra("promo","1");
            Promo promoFind = mPromoModel.where().equalTo("promoCode",_promoCode).findFirst();
            if(promoFind!=null){
                UserData.setFeeMsg(promoFind.getPromoDetail());
                UserData.setPromoCode(_promoCode);
                UserData.flushSettings();
            }else{
                Utils.Toast(getApplicationContext(),"Promo Code wrong!");
                return;
            }

            startActivity(intent);
            return;
        }

        startActivity(intent);
//        startActivityForResult(new Intent(this,FeeDialogActivity.class),1);

    }

    @BindView(R.id.promoED)
    EditText promoET;
    @BindView(R.id.remarkED)
    EditText remarkET;
    @BindView(R.id.feeBtn)
    Button feeBtn;

    @BindView(R.id.markerImg)
    ImageView markerImg;

    //from maps activity

    private static String pickUp,actualLocation;

    static LatLng loc,dest;

    private Realm promoRealm,myRealm;
    private RealmResults<Promo> mPromoModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_detail);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        b = getIntent().getExtras();

        if(UserData.getFee() !=null) {
            feeBtn.setText("$" + UserData.getFee());
        }

        promoRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("Promo.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );


        mPromoModel = promoRealm.where(Promo.class).findAll();

        setUpMapIfNeeded();

        authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class,UserData.getAuthToken());

        getUser();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        if(b!=null) {
            if (b.getString("fromMaps") != null) {
                if (b.getString("fromMaps").equals("toDetail")) {
                    pickUp = b.getString("address");
                    actualLocation = b.getString("actualLocation");

                    loc = (LatLng) b.get("latPickUpLoc");

//                    Logger.e(pickUp );
                    addTxt.setText(pickUp);

                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(loc.latitude, loc.longitude));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);

                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);
                }
            }
        }





    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    public void onClickShow(View v){
//        showDialog = true;

        markerImg.setVisibility(View.GONE);
//        beforeReqRL.setVisibility(View.GONE);
//        afterReqRL.setVisibility(View.VISIBLE);
    }


    public void onClickCancel(View v){

        markerImg.setVisibility(View.VISIBLE);
//        beforeReqRL.setVisibility(View.VISIBLE);
//        afterReqRL.setVisibility(View.GONE);
    }


    public void getUser(){

        userRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("User.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        User user = userRealm.where(User.class).findFirst();
//        user.setUsername(UserData.getUsername());
//        user.setPassword(UserData.getPassword());

        String _curPage = user.getCurpage();
        Logger.e("curpage = " + _curPage);
        if(_curPage.equals("2")){
            transId = user.getTransactionid();

            getTrans();

        }else if(_curPage.equals("0")){

        }





    }

    public void getTrans(){
        transactionAPIWithToken = APIService.createService(TransactionAPI.class,UserData.getAuthToken());

        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("Transaction.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        Call<Transaction> callTransDetail = transactionAPIWithToken.DetailTrans(String.valueOf(transId));
        callTransDetail.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Transaction transaction = response.body();

                UserData.setTransId(String.valueOf(transaction.getTransactionid()));
                UserData.flushSettings();
                myRealm.beginTransaction();
                myRealm.copyToRealmOrUpdate(transaction);
                myRealm.commitTransaction();

                startActivity(new Intent(MapsDetailActivity.this,ConfirmDialogActivity.class));
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

            }
        });


    }


}
