package node8.valetuncle;

import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.orhanobut.logger.Logger;

import net.danlew.android.joda.JodaTimeAndroid;
import node8.valetuncle.R;

import node8.valetuncle.core.ApiManager;
import node8.valetuncle.core.UserData;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by stevensutana on 11/21/16.
 */
public class ValetUncleApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Avenir.ttc")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
//

//        ClipDataSource.setContext(this);
        UserData.setContext(this);
        JodaTimeAndroid.init(this);
//        AmazonHelper.initClient(this);
//        JodaTimeAndroid.init(this);

        ApiManager.getInstance(this);
        Logger.init();
        FacebookSdk.sdkInitialize(this);


    }
}
