package node8.valetuncle.core;


import node8.valetuncle.core.models.CancelResponse;
import node8.valetuncle.core.models.Fee;
import node8.valetuncle.core.models.History;
import node8.valetuncle.core.models.Promo;
import node8.valetuncle.core.models.Transaction;
import node8.valetuncle.core.models.TransactionDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface TransactionAPI {

    /**
     * method to get user transaction
     */
    @FormUrlEncoded
    @POST("user/transaction-fee/")
    Call<Fee> transFee(@Field("username") String username);


    /**
     * method to get user transaction
     */
    @FormUrlEncoded
    @POST("user/promo/")
    Call<ArrayList<Promo>> GetPromo(@Field("username") String username);


    /**
     * method to get user transaction
     */
    @FormUrlEncoded
    @POST("user/history/")
    Call<ArrayList<History>> GetHistory(@Field("objectid") String objectId);


    /**
     * method to get user transaction
     */
    @FormUrlEncoded
    @POST("user/transaction/")
    Call<Transaction> InsertTrans(@Field("username") String username,
                                  @Field("pickup") String pickup,
                                  @Field("remark") String remark,
                                  @Field("pickupaddress") String pickupaddress,
                                  @Field("actuallocation") String actuallocation,
                                  @Field("promo_code") String promo_code);


    /**
     * method to get user transaction
     */
    @FormUrlEncoded
    @POST("user/cancel-transaction/")
    Call<CancelResponse> CancelTrans(@Field("username") String username,
                                     @Field("transactionid") int transId);


    /**
     * method to get user transaction
     */
    @FormUrlEncoded
    @POST("user/finish-transaction/")
    Call<TransactionDetail> FinishTrans(@Field("username") String username,
                                       @Field("transactionid") int transId);


    /**
     * method to get user transaction
     */
    @FormUrlEncoded
    @POST("user/transaction/detail/")
    Call<Transaction> DetailTrans(@Field("transactionid") String transId);
}
