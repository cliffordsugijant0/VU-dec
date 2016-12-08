package node8.valetuncle.core;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIService {

    // No need to instantiate this class.
    private APIService() {

    }

    static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();


    public static final String API_BASE_URL = "https://ylhpfupn1m.execute-api.ap-southeast-1.amazonaws.com/dev/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));


    public static <S> S createService(Class<S> serviceClass, final String token) {
        if (token != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "JWT " + token)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

//    public static <S> S createService(Class<S> serviceClass) {
////        RestAdapter.Builder builder = new RestAdapter.Builder()
////                .setEndpoint(Constants.MAIN)
////                .setConverter(new GsonConverter(gson))
////                .setClient(new OkClient(new OkHttpClient()));
////
////        RestAdapter adapter = builder.build();
////
////        return adapter.create(serviceClass);
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.MAIN)
//                .addConverterFactory(GsonConverterFactory.create(gson)).client(new OkHttpClient())
//                .build();
//
//        return retrofit.create(serviceClass);
//    }

}