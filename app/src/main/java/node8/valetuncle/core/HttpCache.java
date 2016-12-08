package node8.valetuncle.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

//import com.clopy.clopyapp.BuildConfig;
//import com.clopy.clopyapp.clipboard.LruBitmapCache;

/**
 * Created by nicholasng on 12/5/15.
 */
public class HttpCache {

    public static class Keys{
        public static final String title = "_title";
        public static final String description = "_desc";
    }

    private static volatile HttpCache instance;


    public static HttpCache getInstance(Context context) {
        if (instance == null ) {
            synchronized (HttpCache.class) {
                if (instance == null) {
                    instance = new HttpCache(context);
                }
            }
        }
        return instance;
    }

    private static final String TAG = "HttpCache";

    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB

    private LruCache<String, String> mTextCache;
//    private LruBitmapCache mBitmapCache;

    public HttpCache(Context context){
        init(context);
    }

    private void init(Context ctx){
        mTextCache = new LruCache<String, String>(DEFAULT_MEM_CACHE_SIZE);
//        mBitmapCache = new LruBitmapCache(ctx);
    }

    public void addXMLToCache(String key, String value) {

        if (key == null || value == null) {
            return;
        }
        mTextCache.put(key, value);
    }
    
    public void addBitmapToCache(String key,Bitmap bm){
        if (key == null || bm == null) {
            return;
        }
//        mBitmapCache.putBitmap(key, bm);
    }

    public String getXMLFromMemCache(String key) {
        String memValue = null;

        if (mTextCache != null) {
            try {
                memValue = mTextCache.get(key);
            }
            catch (NullPointerException e)
            {
                return null;
            }
        }
        return memValue;
    }

    public Bitmap getBitmapFromCache(String key){
        Bitmap memValue = null;

//        if (mBitmapCache != null) {
//            try {
//                memValue = mBitmapCache.getBitmap(key);
//            }
//            catch (NullPointerException e)
//            {
//                return null;
//            }
//        }
        return memValue;
    }

//    public LruBitmapCache getBitmapCache(){
//        return mBitmapCache;
//    }
//
//    public void clearCache() {
//        if (mTextCache != null) {
//            mTextCache.evictAll();
//        }
//        if(mBitmapCache != null){
//            mBitmapCache.evictAll();
//        }
//        if (BuildConfig.DEBUG) {
//            Log.d(TAG, "Memory cache cleared");
//        }
//    }
}
