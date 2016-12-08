package node8.valetuncle.core;


import node8.valetuncle.core.models.BaseResponse;
import node8.valetuncle.core.models.LoginModel;
import node8.valetuncle.core.models.Promo;
import node8.valetuncle.core.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface AuthenticationAPI {


    /**
     * method to login
     * @param UserName
     * @param UserPassword
     * @param response
     */
    @FormUrlEncoded
    @POST("/setlogin/")
    void setlogin(@Field("username") String UserName,
                  @Field("password") String UserPassword,
                  Callback<LoginModel> response);

    /**
     * method to get token
     * @param UserName
     * @param UserPassword
     * @param response
     */
    @FormUrlEncoded
    @POST("/apitokenauth/")
    void apitokenauth(@Field("username") String UserName,
                      @Field("password") String UserPassword,
                      Callback<LoginModel> response);

//    /**
//     * method to register
//     */
//    @Multipart
//    @POST("/register/")
//    void register(
//            @Field("username") String UserName,
//            @Field("email") String UserEmail,
//            @Field("password") String UserPassword,
//            @Field("name") String Name,
//            @Field("phone_code") String UserPhone,
//            @Field("phone_number") String UserPhoneCode,
//            @Field("country") String UserCountry,
//            @Field("vercode") int UserVer,
//            Callback<BaseResponse> response);

//    /**
//     * method to register
//     */
//    @POST("/user/registration/")
//    Call<BaseResponse> register(@Field("username") String UserName,
//                             @Field("email") String UserEmail,
//                             @Field("password") String UserPassword,
//                             @Field("name") String Name,
//                             @Field("phone_code") String UserPhone,
//                             @Field("phone_number") String UserPhoneCode,
//                             @Field("country") String UserCountry,
//                             @Field("vercode") int UserVer);

    /**
     * method to register
     */
    @POST("user/registration/")
    Call<BaseResponse> register(@Body User user);


    /**
     * method to register
     */
    @POST("user/forgot-password/")
    Call<BaseResponse> ForgotPassword(@Field("username") String user);

    /**
     * method to get token
     */
    @FormUrlEncoded
    @POST("user/token/")
    Call<BaseResponse> token(@Field("username") String username,@Field("password") String password);

    /**
     * method to get detail
     */
    @POST("user/detail/")
    Call<User> detail(@Body User user);

    /**
     * method to get detail
     */
    @POST("user/status-login/")
    Call<User> status(@Field("username") String username);

    /**
     * method to get detail
     */
    @FormUrlEncoded
    @POST("user/login/")
    Call<User> login(@Field("username") String username,@Field("password") String password);

    /**
     * method to update user detail
     */
    @POST("user/update/")
    Call<User> update(@Body User user);


    /**
     * method to update user detail
     */
    @FormUrlEncoded
    @POST("user/isms/")
    Call<BaseResponse> isms(@Field("message") String msg,@Field("phone") String phone);


    /**
     * method to update user detail
     */
    @GET("user/random-promo-get/")
    Call<Promo> RandomPromo();
}
