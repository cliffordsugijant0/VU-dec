package node8.valetuncle.core;//package net.node8.munchgram.core;
//
//import com.atouchlab.android.socnetwork.models.LoginModel;
//import com.atouchlab.android.socnetwork.models.ResponseModel;
//import com.atouchlab.android.socnetwork.models.UsersModel;
//
//import io.realm.RealmList;
//import retrofit.Callback;
//import retrofit.http.Field;
//import retrofit.http.FormUrlEncoded;
//import retrofit.http.GET;
//import retrofit.http.Multipart;
//import retrofit.http.POST;
//import retrofit.http.PUT;
//import retrofit.http.Part;
//import retrofit.http.Path;
//import retrofit.mime.TypedFile;
//
///**
// * Created by Abderrahim on 10/1/2015.
// */
//public interface UsersAPI {
//    /**
//     * method to get user information
//     * @param userID
//     * @param user
//     */
//    @GET("/users/get/{id}")
//    void getUserInfo(@Path("id") int userID,
//                     Callback<UsersModel> user);
//    /**
//     * method to get user information
//     * @param mention
//     * @param user
//     */
//    @GET("/users/mention/{mention}")
//    void getUserInfoByMention(@Path("mention") String mention,
//                              Callback<UsersModel> user);
//
//    /**
//     * method to get list friends
//     * @param page
//     * @param user
//     */
//    @GET("/users/friends/all/{page}")
//    void getFriendsList(@Path("page") int page,
//                        Callback<RealmList<UsersModel>> user);
//
//    /**
//     * method to get user list freinds
//     * @param userID
//     * @param page
//     * @param user
//     */
//    @GET("/users/friends/userFriends/{userId}/{page}")
//    void getUserFriendsList(@Path("userId") int userID, @Path("page") int page,
//                            Callback<RealmList<UsersModel>> user);
//
//    /**
//     * method to get chat list friends
//     * @param page
//     * @param user
//     */
//    @GET("/users/friends/chat/{page}")
//    void getFriendsChatList(@Path("page") int page,
//                            Callback<RealmList<UsersModel>> user);
//
//    /**
//     * method to get sent request list friends
//     * @param page
//     * @param user
//     */
//    @GET("/users/friends/sent/{page}")
//    void getSentRequestsList(@Path("page") int page,
//                             Callback<RealmList<UsersModel>> user);
//
//    /**
//     * method to get new request list friends
//     * @param page
//     * @param user
//     */
//    @GET("/users/friends/new/{page}")
//    void getNewRequestsList(@Path("page") int page,
//                            Callback<RealmList<UsersModel>> user);
//
//    /**
//     * method to get blocked users list friends
//     * @param page
//     * @param user
//     */
//    @GET("/users/friends/blocked/{page}")
//    void getBlockedFriendsList(@Path("page") int page,
//                               Callback<RealmList<UsersModel>> user);
//
//    /**
//     * method to search for new friends
//     * @param string
//     * @param callback
//     */
//    @FormUrlEncoded
//    @POST("/users/SearchFriends")
//    void SearchFriends(@Field("string") String string, Callback<RealmList<UsersModel>> callback);
//
//    /**
//     * method to send a new request friend
//     * @param id
//     * @param updateResponseModelCallback
//     */
//    @GET("/users/sentRequestFriend/{id}")
//    void sentRequestFriend(@Path("id") int id, Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to cancel a sent requests friend
//     * @param id
//     * @param updateResponseModelCallback
//     */
//    @GET("/users/cancelRequestFriend/{id}")
//    void cancelRequestFriend(@Path("id") int id, Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to accept new requests friend
//     * @param id
//     * @param updateResponseModelCallback
//     */
//    @GET("/users/acceptRequestFriend/{id}")
//    void acceptRequestFriend(@Path("id") int id, Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to decline the new requests friend
//     * @param id
//     * @param updateResponseModelCallback
//     */
//    @GET("/users/DeclineRequestFriend/{id}")
//    void DeclineRequestFriend(@Path("id") int id, Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to remove a friend from the list friends
//     * @param id
//     * @param updateResponseModelCallback
//     */
//    @GET("/users/UnFriend/{id}")
//    void UnFriend(@Path("id") int id, Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to block a friend
//     * @param id
//     * @param updateResponseModelCallback
//     */
//    @GET("/users/BlockRequestFriend/{id}")
//    void BlockRequestFriend(@Path("id") int id, Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to unblock a friend
//     * @param id
//     * @param updateResponseModelCallback
//     */
//    @GET("/users/UnBlockRequestFriend/{id}")
//    void UnBlockRequestFriend(@Path("id") int id, Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to give feedback
//     * @param UserName
//     * @param Rating
//     * @param Message
//     * @param response
//     */
//    @Multipart
//    @POST("/feedback")
//    void setRating(
//            @Part("fromUser") String UserName,
//            @Part("Rating") String Rating,
//            @Part("Message") String Message,
//            Callback<LoginModel> response);
//
//    /**
//     * method to update user information
//     * @param UserName
//     * @param UserEmail
//     * @param UserPassword
//     * @param UserImage
//     * @param UserCover
//     * @param FullName
//     * @param UserJob
//     * @param UserAddress
//     * @param updateResponse
//     */
//    @Multipart
//    @POST("/users/update")
//    void updateProfile(@Part("UserName") String UserName,
//                       @Part("UserEmail") String UserEmail,
//                       @Part("UserPassword") String UserPassword,
//                       @Part("UserImage") TypedFile UserImage,
//                       @Part("UserCover") TypedFile UserCover,
//                       @Part("FullName") String FullName,
//                       @Part("UserJob") String UserJob,
//                       @Part("UserAddress") String UserAddress,
//                       Callback<ResponseModel> updateResponse);
//
//    /**
//     * method to update registerID for GCM
//     * @param regID
//     * @param response
//     */
//    @FormUrlEncoded
//    @POST("/users/regId/")
//    void updateRegID(@Field("regID") String regID, Callback<ResponseModel> response);
//
//    /**
//     * method to update status and set it offline
//     * @param userID
//     * @param updateResponse
//     */
//    @Multipart
//    @PUT("/users/statusOffline")
//    void updateUserStatusOffline(@Part("id") int userID, Callback<ResponseModel> updateResponse);
//
//    /**
//     * method to update status and set it online
//     * @param userID
//     * @param updateResponse
//     */
//    @Multipart
//    @PUT("/users/statusOnline")
//    void updateUserStatusOnline(@Part("id") int userID, Callback<ResponseModel> updateResponse);
//
//    /**
//     * method to deactivate owner user account
//     * @param userID
//     * @param updateResponse
//     */
//    @Multipart
//    @PUT("/users/DeactivateAccount")
//    void deactivateAccount(@Part("id") int userID, Callback<ResponseModel> updateResponse);
//
//    /**
//     * method to rest password
//     * @param email
//     * @param response
//     */
//    @FormUrlEncoded
//    @POST("/users/restPassword")
//    void restPassword(@Field("email") String email, Callback<ResponseModel> response);
//}
