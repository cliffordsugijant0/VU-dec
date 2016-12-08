package node8.valetuncle.core;//package net.node8.munchgram.core;
//
//
//import com.atouchlab.android.socnetwork.models.LocationModel;
//import com.atouchlab.android.socnetwork.models.ResponseModel;
//
//import retrofit.Callback;
//import retrofit.http.Field;
//import retrofit.http.FormUrlEncoded;
//import retrofit.http.GET;
//import retrofit.http.POST;
//import retrofit.http.Path;
//
///**
// * Created by SmartCoder on 18/04/2015.
// */
//public interface GlobalAPI {
//    /**
//     * method to get Location
//     * @param longitude
//     * @param latitude
//     * @param response
//     */
//    @GET("/place/{longitude}/{latitude}")
//    void getCurrentPlace(@Path("longitude") double longitude,
//                         @Path("latitude") double latitude,
//                         Callback<LocationModel> response);
//
//    /**
//     * method to get Place Name
//     * @param placeName
//     * @param MapInfo
//     */
//    @FormUrlEncoded()
//    @POST("/map/get")
//    void getMapImageUrl(@Field("place_name") String placeName, Callback<ResponseModel> MapInfo);
//
//    /**
//     * method to get Disclaimer
//     * @param responseModel
//     */
//    @GET("/terms")
//    void getTerms(Callback<ResponseModel> responseModel);
//
//}
