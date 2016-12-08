package node8.valetuncle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.location.Address;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.orhanobut.logger.Logger;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.TransactionAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.Fee;
import node8.valetuncle.core.models.History;
import node8.valetuncle.core.models.Promo;
import node8.valetuncle.core.models.User;
import node8.valetuncle.dialogs.PromoDialogActivity;
import node8.valetuncle.helpers.CheckNetwork;
import node8.valetuncle.helpers.GPSTracker;
import node8.valetuncle.helpers.M;
import node8.valetuncle.views.AnimationLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,AnimationLayout.Listener {

    private static final int REQUEST_CODE = 1001;
    private GoogleMap mMap;

    //optional gps
    GPSTracker gps;



    //like uber

    private LatLng center;

    private Geocoder geocoder;
    private List<Address> addresses;

    private float currentZoom = 1.0f;

    private LatLng centerZoom;
    private boolean isZoomed = false;
    //for gps actual location
    static String actualLocation;



    AuthenticationAPI authenticationAPIWithoutToken = APIService.createService(AuthenticationAPI.class,null);
    AuthenticationAPI authenticationAPIWithToken;

    TransactionAPI transactionAPIWithToken;

    @BindView(R.id.nameTV) TextView nameTV;

    @BindView(R.id.animation_layout) AnimationLayout mLayout;

    @BindView(R.id.addTxt)
    TextView addTxt;

    @OnClick(R.id.profileTV) void profileClicked(){
        if(CheckNetwork.isInternetAvailable(MapsActivity.this))
            startActivity(new Intent(MapsActivity.this,ProfileActivity.class));
        else
            Toast.makeText(MapsActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();


    }

    @OnClick(R.id.historyTV) void historyClicked(){
        if(CheckNetwork.isInternetAvailable(MapsActivity.this))
            startActivity(new Intent(MapsActivity.this,HistoryActivity.class));
        else
            Toast.makeText(MapsActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.aboutTV) void aboutClicked(){
        if(CheckNetwork.isInternetAvailable(MapsActivity.this))
            startActivity(new Intent(MapsActivity.this,AboutActivity.class));
        else
            Toast.makeText(MapsActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.promoTV) void promoClicked(){
        if(CheckNetwork.isInternetAvailable(MapsActivity.this))
            startActivity(new Intent(MapsActivity.this,PromoActivity.class));
        else
            Toast.makeText(MapsActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.signoutTV) void signOutClicked(){
        User user = new User(UserData.getObjectId());

        user.setStatuslogin(0);

        Call<User> call = authenticationAPIWithToken.update(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){

                    UserData.ClearAllSettings();
                    UserData.flushSettings();
                    finish();
                    startActivity(new Intent(MapsActivity.this,LoginRegisterActivity.class));


                }else{

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MapsActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();

                Logger.e(t.getMessage());

            }
        });



    }


    @OnClick(R.id.pickUpBtn) void pickUpClicked(){
        if(CheckNetwork.isInternetAvailable(MapsActivity.this)){
            Intent i = new Intent(MapsActivity.this, MapsDetailActivity.class);
            i.putExtra("fromMaps", "toDetail");
            i.putExtra("address", addTxt.getText().toString());
            i.putExtra("latPickUpLoc", center);
            i.putExtra("actualLocation",actualLocation);
            finish();
            startActivity(i);
        }
        else
            Toast.makeText(MapsActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();

//        finish();
//        startActivity(new Intent(MapsActivity.this,MapsDetailActivity.class));

    }

    //realm

    private Realm myRealm,userRealm,historyRealm;
    private ArrayList<Promo> mOrdersModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        mLayout.setListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        nameTV.setText(UserData.getUsername());
        authenticationAPIWithToken = APIService.createService(AuthenticationAPI.class,UserData.getAuthToken());
        transactionAPIWithToken = APIService.createService(TransactionAPI.class,UserData.getAuthToken());

        myRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("Promo.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );


        userRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("User.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );


        historyRealm =
                Realm.getInstance(
                        new RealmConfiguration.Builder(this)
                                .name("History.realm")
                                .deleteRealmIfMigrationNeeded()
                                .build()
                );

        if(M.isNetworkAvailable(MapsActivity.this)){

            getFeeDetail();
            getPromo();
            getUser();
            getHistory();
        }else{
//            Utils.Toast(MapsActivity.this,"Please check your internet connection");
        }

    }


    public void onClickMyLocation(View v){
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
        }
    }

    public void getUser(){
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

                    if(user.getShowPromo() == 1){

                        startActivity(new Intent(MapsActivity.this, PromoDialogActivity.class));

                    }


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

    public void getHistory(){
        Call<ArrayList<History>> callGetHistory = transactionAPIWithToken.GetHistory(UserData.getObjectId());

        callGetHistory.enqueue(new Callback<ArrayList<History>>() {
            @Override
            public void onResponse(Call<ArrayList<History>> call, Response<ArrayList<History>> response) {
                ArrayList<History> arrHistory = response.body();



                Logger.e("get history " + arrHistory.size());
                historyRealm.beginTransaction();
                historyRealm.copyToRealmOrUpdate(arrHistory);
                historyRealm.commitTransaction();


            }

            @Override
            public void onFailure(Call<ArrayList<History>> call, Throwable t) {

            }
        });
    }


    public void getFeeDetail(){

        Call<Fee> callFee = transactionAPIWithToken.transFee(UserData.getUsername());
        callFee.enqueue(new Callback<Fee>() {
            @Override
            public void onResponse(Call<Fee> call, Response<Fee> response) {

                if(response.isSuccessful()){

                    Fee fee = response.body();
                    UserData.setFee(String.valueOf(fee.getFee()));
                    UserData.setFeeMsg(fee.getMessage());

                }else{

                }
            }

            @Override
            public void onFailure(Call<Fee> call, Throwable t) {

            }
        });

    }

    public void getPromo(){
        Call<ArrayList<Promo>> callGetPromo = transactionAPIWithToken.GetPromo(UserData.getUsername());

        callGetPromo.enqueue(new Callback<ArrayList<Promo>>() {
            @Override
            public void onResponse(Call<ArrayList<Promo>> call, Response<ArrayList<Promo>> response) {
                ArrayList<Promo> arrPromo = response.body();


                myRealm.beginTransaction();
                myRealm.copyToRealmOrUpdate(arrPromo);
                myRealm.commitTransaction();


            }

            @Override
            public void onFailure(Call<ArrayList<Promo>> call, Throwable t) {

            }
        });
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        gps = new GPSTracker(MapsActivity.this);
//        gps.setmMap(mMap);
//
//        if (gps.canGetLocation()) {
//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//
//            CameraUpdate center =
//                    CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
//            CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
//
//            this.center = new LatLng(gps.getLatitude(), gps.getLongitude());
//            centerZoom = new LatLng(gps.getLatitude(), gps.getLongitude());
//            new GetActualLocationAsync(gps.getLatitude(),gps.getLongitude()).execute();
////            Log.e("gps",gps.getLatitude()+" "+gps.getLongitude());
//
////            new GetActualLocationAsync(gps.getLatitude(),gps.getLongitude()).execute();
//
////                Log.e("location",getLocationCityName(gps.getLatitude(), gps.getLongitude()));
//            mMap.moveCamera(center);
//            mMap.animateCamera(zoom);
//        }

        startFetchingLocation();
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                gps.stopUsingGPS();

                new GetLocationAsync(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude).execute();
//                try {
//                    Geocoder geo = new Geocoder(MapsActivity.this.getApplicationContext(), Locale.getDefault());
//                    List<Address> addresses = geo.getFromLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude, 1);
//                    if (addresses.isEmpty()) {
//                        addTxt.setText("Waiting for Location");
//                    }
//                    else {
//                        if (addresses.size() > 0) {
//                            addTxt.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
//                            //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//                catch (Exception e) {
//                    e.printStackTrace(); // getFromLocation() may sometimes fail
//                }


                MapsActivity.this.center = new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
//                if (gps.canGetLocation) {
//
//
//                    if (cameraPosition.zoom != currentZoom) {
//                        isZoomed = true;
//                        currentZoom = cameraPosition.zoom;  // here you get zoom level
//
//                        CameraUpdate initCenter =
//                                CameraUpdateFactory.newLatLng(new LatLng(centerZoom.latitude, centerZoom.longitude));
//
//                        mMap.moveCamera(initCenter);
//                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(currentZoom);
//                        mMap.stopAnimation();
//                        mMap.animateCamera(zoom);
//                        mMap.stopAnimation();
//                    } else {
//                        isZoomed = false;
//                        center = mMap.getCameraPosition().target;
//                    }
//
//                    if (!isZoomed) {
//                        centerZoom = center;
//                    }
//
//
////                    Log.e("camera",""+center.latitude+center.longitude);
//
//                    try {
//                        new GetLocationAsync(center.latitude, center.longitude)
//                                .execute();
////                        asd(center.latitude,center.longitude);
//
//
//                    } catch (Exception e) {
//                    }
//
//                }


            }
        });
    }

    @Override
    public void onSidebarOpened() {

    }

    @Override
    public void onSidebarClosed() {

    }


    @Override
    public boolean onContentTouchedWhenOpening() {
//        Log.d(TAG, "going to close sidebar");
        mLayout.closeSidebar();
        return true;
    }


    public void onClickContentButton(View v) {
        mLayout.toggleSidebar();
    }

    @Override
    public void onBackPressed() {
        if (mLayout.isOpening()) {
            mLayout.closeSidebar();
        } else {
//            finish();
        }
    }


    private class GetLocationAsync extends AsyncTask<String, Void, String> {

        // boolean duplicateResponse;
        double x, y;
        StringBuilder str;

        public GetLocationAsync(double latitude, double longitude) {
            // TODO Auto-generated constructor stub

            x = latitude;
            y = longitude;
        }

        @Override
        protected void onPreExecute() {
            addTxt.setText("Getting location....");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                geocoder = new Geocoder(MapsActivity.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (geocoder.isPresent()) {
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + "");
                    str.append(city + "" + region_code + "");
                    str.append(zipcode + "");

                } else {
                }
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
//                new GeocoderHelper().fetchCityName(MapsActivity.this,x,y,addTxt);
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                addTxt.setText(addresses.get(0).getAddressLine(0)
                        + " " +addresses.get(0).getAddressLine(1) + " ");
            } catch (Exception e) {
//                Log.e("message",e.getMessage());
                asd(x,y);
                e.printStackTrace();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    /**
     * for track location
     */
    private class GetActualLocationAsync extends AsyncTask<String, Void, String> {

        // boolean duplicateResponse;
        double x, y;
        StringBuilder str;

        public GetActualLocationAsync(double latitude, double longitude) {
            // TODO Auto-generated constructor stub

            x = latitude;
            y = longitude;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e("xy",x+" "+y);
                geocoder = new Geocoder(MapsActivity.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (geocoder.isPresent()) {
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + "");
                    str.append(city + "" + region_code + "");
                    str.append(zipcode + "");

                } else {
                }
            } catch (Exception e) {
                Log.e("tagIO", e.getMessage());
                actualLocation = x+";"+y;
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {

                actualLocation = addresses.get(0).getAddressLine(0)
                        + " "
                        + addresses.get(0).getAddressLine(1) + " ";

                Log.e("DETAIL",actualLocation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
    //emergency
    private void asd(double latitude,double longitude )
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + ","
                + longitude + "&sensor=false&language=fr";


        Map<String, String> params = new HashMap<String, String>();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, googleMapUrl, new JSONObject(params),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", String.valueOf(response));
                        try {
//                            String confirmationCode = response.getString("confCode");
//                            String user_id = response.getString("pk");
//                            String phoneNo = response.getString("phoneNo");
//                            String alias = response.getString("alias");
//                            Log.v("Response", confirmationCode);


//                            if(b!=null){
//                                if (b.getString("advance")!=null)
//                                {
//                                    advanceBook();
//                                }
//                            }

                            JSONArray results = (JSONArray) response.get("results");

                            String cityName = results.getJSONObject(0).getString("formatted_address");

                            addTxt.setText(cityName);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error response map",error.getMessage()+"ERROR");
                    }
                });

        queue.add(jsObjRequest);
    }


    private boolean checkingGPS;
    @Override
    protected void onResume() {
        super.onResume();
        if(!checkingGPS) {
            checkGPS();
        }
    }

    private void checkGPS(){
        checkingGPS = true;
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Ask the user to enable GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Manager");
            builder.setMessage("Would you like to enable GPS?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Launch settings, allowing user to make a change
                    dialog.dismiss();
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(i, REQUEST_CODE);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //No location service, no Activity
                    finish();
                }
            });
            builder.create().show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE && resultCode == 0){

            LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                startFetchingLocation();
            }else{
                Logger.e("not able");
                checkGPS();
            }

//            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//            if(provider != null){
////                Log.v(TAG, " Location providers: "+provider);
//                //Start searching for location and update the location text when update available.
//// Do whatever you want
//                startFetchingLocation();
//            }else{
//                //Users did not switch on the GPS
//                Logger.e("not able");
//            }
        }
    }


    private void startFetchingLocation(){
        Logger.e("HERE FETCH LOCATION");
        if(gps == null) {
            gps = new GPSTracker(MapsActivity.this);
            gps.setmMap(mMap);
        }
        Logger.e("gps " + gps.canGetLocation());

        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);

            this.center = new LatLng(gps.getLatitude(), gps.getLongitude());
            centerZoom = new LatLng(gps.getLatitude(), gps.getLongitude());
            new GetActualLocationAsync(gps.getLatitude(),gps.getLongitude()).execute();
//            Log.e("gps",gps.getLatitude()+" "+gps.getLongitude());

//            new GetActualLocationAsync(gps.getLatitude(),gps.getLongitude()).execute();

//                Log.e("location",getLocationCityName(gps.getLatitude(), gps.getLongitude()));
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            new GetLocationAsync(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude).execute();
        }
    }





}
