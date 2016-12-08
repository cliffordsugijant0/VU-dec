package node8.valetuncle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.orhanobut.logger.Logger;

import node8.valetuncle.R;

import node8.valetuncle.core.APIService;
import node8.valetuncle.core.AuthenticationAPI;
import node8.valetuncle.core.UserData;
import node8.valetuncle.core.models.User;
import node8.valetuncle.helpers.M;
import node8.valetuncle.helpers.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginRegisterActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    Dialog dialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    AuthenticationAPI authenticationAPIWithoutToken = APIService.createService(AuthenticationAPI.class, null);
    AuthenticationAPI authenticationAPIWithToken;



    //facebook
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            // App code
            System.out.println("Success");


            Map<String, String> logins = new HashMap<String, String>();
            logins.put("graph.facebook.com", loginResult.getAccessToken().getToken());
//                    AmazonHelper.getCredProvider().withLogins(logins);
//                    UserData.setAuthToken(loginResult.getAccessToken().getToken());

//            Utils.setFBToken(loginResult.getAccessToken().getToken(),getApplicationContext());

            makeMeRequest(loginResult.getAccessToken());
//
        }

        @Override
        public void onCancel() {
            // App code
            Utils.Toast(getApplicationContext(), "cancel");
        }

        @Override
        public void onError(FacebookException exception) {
            // App code
            Log.e("facebookexception", exception.getMessage());
            Toast.makeText(LoginRegisterActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
        }

    };

    //google+

    //google+
    GoogleApiClient mGoogleApiClient;
    private SignInButton googleBtn;
    public static final int RC_SIGN_IN = 9001;
    private ConnectionResult mConnectionResult;

    @BindView(R.id.username)
    EditText userET;

    @BindView(R.id.password)
    EditText passET;

    @OnClick(R.id.signInBtn)
    void signIn() {

        final String _username = userET.getText().toString();
        final String _pass = passET.getText().toString();

        final int randomCode = node8.valetuncle.helpers.Utils.generateRandom();
        final String messageSMS = String.valueOf(randomCode);


        M.showDialog(LoginRegisterActivity.this, false);
        Call<User> call = authenticationAPIWithoutToken.login(_username,
                Utils.md5(_pass));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                M.hideDialog();
                if (response.isSuccessful()) {


                    User userResponse = response.body();

                    Logger.e("logn 200" + userResponse.getPk());

                    UserData.setUserInfo(userResponse.getPk(), userResponse.getName(), _username);
                    UserData.setPassword(_pass);

                    UserData.setAuthToken(userResponse.getToken());
                    UserData.setLoggedIn("true");

                    UserData.flushSettings();
                    if(userResponse.getStatuslogin()==null || userResponse.getStatuslogin()==0){
                        finish();
                        startActivity(new Intent(LoginRegisterActivity.this, MapsActivity.class));
                    }
                    else{
                        if(userResponse.getStatuslogin()==1)
                            Toast.makeText(LoginRegisterActivity.this,getString(R.string.loggedin_message),Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(LoginRegisterActivity.this,getString(R.string.maintenance_message),Toast.LENGTH_LONG).show();
                    }


                } else if (response.code() == 406) {

                    User userResponse = new User();

                    Gson gson = new Gson();
                    TypeAdapter<User> adapter = gson.getAdapter(User.class);
                    try {
                        if (response.errorBody() != null)
                            userResponse =
                                    adapter.fromJson(
                                            response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Logger.e("logn 406" + userResponse.getPk());

                    UserData.setUserInfo(userResponse.getPk(), userResponse.getName(), _username);
                    UserData.setPassword(_pass);

                    UserData.setConfirmationCode(String.valueOf(randomCode));


                    UserData.flushSettings();

                    userResponse.setUsername(_username);
                    userResponse.setPassword(_pass);

                    finish();
                    startActivity(new Intent(LoginRegisterActivity.this, ConfirmationActivity.class));

                } else {
                    try {
                        Logger.e(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Utils.Toast(getApplicationContext(), "Password incorrect");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginRegisterActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
                M.hideDialog();
            }
        });

    }

    @OnClick(R.id.signupBtn)
    void signUp() {
        finish();
        startActivity(new Intent(LoginRegisterActivity.this, RegisterActivity.class));

    }

    @OnClick(R.id.forgotBtn)
    void forgot() {
//        finish();
        startActivity(new Intent(LoginRegisterActivity.this, ForgotPasswordActivity.class));
    }

    @OnClick(R.id.loginFBBtn)
    void signInFB() {

        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("email", "public_profile"));

    }

    @OnClick(R.id.googleBtn)
    void signInGoogle() {
//        Utils.Toast(getApplicationContext(),"WAIT");
//        M.showDialog(LoginRegisterActivity.this,false);


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//        setDummy();

        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        LoginManager.getInstance().registerCallback(callbackManager, callback);
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
//                            Utils.setFBToken(currentAccessToken.getToken(),getApplicationContext());
            }
        };

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void setDummy() {
        userET.setText("maman1poi");
        passET.setText("maman");
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("LoginRegister Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    private void makeMeRequest(final AccessToken token) {

        Logger.e("make me req");
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        Log.e("completed",object.toString());

                        Intent intent = new Intent(LoginRegisterActivity.this,RegisterActivity.class);
                        try {
                            intent.putExtra("email",object.getString("email"));
                            intent.putExtra("name", object.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(intent);
                        finish();

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Logger.e("handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);

            Log.e("google sign in", acct.getDisplayName());
            Intent intent = new Intent(LoginRegisterActivity.this,RegisterActivity.class);

            intent.putExtra("email",acct.getEmail());
            intent.putExtra("name",acct.getDisplayName());


            startActivity(intent);
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(LoginRegisterActivity.this,getString(R.string.InternetConnection),Toast.LENGTH_LONG).show();
//            updateUI(false);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("Test error", connectionResult.getErrorMessage());
    }
}
