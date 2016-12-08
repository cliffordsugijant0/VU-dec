package node8.valetuncle.core;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by steven on 3/1/15.
 */


public class ApiManager {

    private static Context _mContext;
    private CopyOnWriteArrayList<RequestQueue> _mRequestQueue;
    private ImageLoader _mImageLoader;
    private static volatile ApiManager instance;

    public static ApiManager getInstance(Context context) {
        if (instance == null ) {
            synchronized (ApiManager.class) {
                if (instance == null) {
                    instance = new ApiManager(context);
                }
            }
        }
        return instance;
    }

    private ApiManager(Context context) {
        _mContext = context;

        _mRequestQueue = new CopyOnWriteArrayList<>();
        for(int i = 0; i < 2; i++)
        {
            _mRequestQueue.add(Volley.newRequestQueue(_mContext.getApplicationContext()));
        }
//        _mImageLoader = new ImageLoader(_mRequestQueue.get(1), HttpCache.getInstance(context).getBitmapCache());
    }


    public ImageLoader getImageLoader(){
        return _mImageLoader;
    }

    public RequestQueue getRequestQueue(){
        return _mRequestQueue.get(1);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        _mRequestQueue.get(0).add(req);
    }


    public void Login(String imei,
                      String idNumber,
                      final Response.Listener<String> listener,
                      final Response.ErrorListener errorListener) {

        final String api = Constants.MAIN + "loginDriver/";

        final Map<String, String> params = new HashMap<String, String>();
        params.put("imei", imei);
        params.put("idNumber", idNumber);
        addToRequestQueue(new CustomStringRequest(Request.Method.POST, api, params, null, listener, errorListener));




    }

    public void UpdateDriver(String objectId,
                      String input,
                      final Response.Listener<JSONObject> listener,
                      final Response.ErrorListener errorListener) {

        final String api = Constants.MAIN + "updateDriver/";

        final Map<String, String> params = new HashMap<String, String>();
        params.put("objectId", objectId);
        params.put("input", input);


    }


}
