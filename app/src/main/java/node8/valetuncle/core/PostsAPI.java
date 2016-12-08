package node8.valetuncle.core;//package net.node8.munchgram.core;
//
//import com.atouchlab.android.socnetwork.models.CommentsModel;
//import com.atouchlab.android.socnetwork.models.PostsModel;
//import com.atouchlab.android.socnetwork.models.ResponseModel;
//
//import io.realm.RealmList;
//import retrofit.Callback;
//import retrofit.http.Field;
//import retrofit.http.FormUrlEncoded;
//import retrofit.http.GET;
//import retrofit.http.Multipart;
//import retrofit.http.POST;
//import retrofit.http.Part;
//import retrofit.http.Path;
//import retrofit.mime.TypedFile;
//
///**
// * Created by Abderrahim on 10/26/2015.
// */
//public interface PostsAPI {
//    /*
//   *Feeds operations
//  */
//
//    /**
//     *  method to publish something
//     * @param fileImage
//     * @param fileThumbnail
//     * @param fileVideo
//     * @param status
//     * @param link
//     * @param place
//     * @param privacy
//     * @param responseModel
//     */
//    @Multipart
//    @POST("/posts/publish")
//    void publishPost(@Part("image") TypedFile fileImage,
//                     @Part("thumbnail") TypedFile fileThumbnail,
//                     @Part("video") TypedFile fileVideo,
//                     @Part("status") String status,
//                     @Part("link") String link,
//                     @Part("place") String place,
//                     @Part("privacy") String privacy,
//                     Callback<ResponseModel> responseModel);
//
//    /**
//     * method to get all posts
//     * @param page
//     * @param posts
//     */
//    @GET("/posts/all/{page}")
//    void getPosts(@Path("page") int page,
//                  Callback<RealmList<PostsModel>> posts);
//    /**
//     * method to get hashtag
//     * @param hashtag
//     * @param page
//     * @param posts
//     */
//    @GET("/posts/all/{hashtag}/{page}")
//    void getPosts(@Path("hashtag") String hashtag, @Path("page") int page,
//                  Callback<RealmList<PostsModel>> posts);
//    /**
//     * method to get user list feed
//     * @param userID
//     * @param page
//     * @param feedsModels
//     */
//    @GET("/posts/get/{userId}/{page}")
//    void getUserPosts(@Path("userId") int userID, @Path("page") int page, Callback<RealmList<PostsModel>> feedsModels);
//
//    /**
//     * method to get user list posts for gallery
//     * @param userID
//     * @param page
//     * @param feedsModels
//     */
//    @GET("/posts/gallery/{userId}/{page}")
//    void getUserPostsGallery(@Path("userId") int userID, @Path("page") int page, Callback<RealmList<PostsModel>> feedsModels);
//    /**
//     * method to get a single feed
//     * @param feedID
//     * @param userID
//     * @param feedsModel
//     */
//    @GET("/posts/get/Feed/{FeedId}/{userId}")
//    void getSinglePosts(@Path("FeedId") int feedID, @Path("userId") int userID, Callback<PostsModel> feedsModel);
//
//
//    /**
//     * method to like  feed
//     * @param feedID
//     * @param response
//     */
//    @GET("/posts/Like/{FeedId}")
//    void LikePost(@Path("FeedId") int feedID, Callback<ResponseModel> response);
//
//    /**
//     * method to unlike feed
//     * @param feedID
//     * @param response
//     */
//    @GET("/posts/UnLike/{FeedId}")
//    void unLikePost(@Path("FeedId") int feedID, Callback<ResponseModel> response);
//
//    /**
//     * method to get list favorites
//     * @param page
//     * @param feedsModels
//     */
//    @GET("/posts/favorites/{page}")
//    void getUserFavorites(@Path("page") int page, Callback<RealmList<PostsModel>> feedsModels);
//
//    /**
//     * method to delete a specific feed
//     * @param FeedID
//     * @param userId
//     * @param response
//     */
//    @GET("/posts/delete/{FeedId}/{userId}")
//    void deletePost(@Path("FeedId") int FeedID, @Path("userId") int userId, Callback<ResponseModel> response);
//
//    /**
//     * method to edit a specific feed
//     * @param FeedID
//     * @param status
//     * @param response
//     */
//    @FormUrlEncoded()
//    @POST("/posts/edit/{FeedId}")
//    void editPost(@Path("FeedId") int FeedID, @Field("status") String status, Callback<ResponseModel> response);
//
//    /**
//     * method to send a new reports
//     * @param FeedID
//     * @param Reason
//     * @param Description
//     * @param updateResponse
//     */
//
//    @Multipart
//    @POST("/posts/NewReport/{FeedId}")
//    void insertNewReportPost(@Path("FeedId") int FeedID,
//                             @Part("Reason") String Reason,
//                             @Part("Description") String Description,
//                             Callback<ResponseModel> updateResponse);
//    /*
//    *Comments operations
//    */
//
//    /**
//     * method to get  comments list
//     * @param feedID
//     * @param comments
//     */
//    @GET("/comments/all/{feedId}")
//    void getComments(@Path("feedId") int feedID, Callback<RealmList<CommentsModel>> comments);
//
//    /**
//     * method to delete a specific comment
//     * @param CommentID
//     * @param userId
//     * @param response
//     */
//    @GET("/comments/delete/{CommentId}/{userId}")
//    void deleteComment(@Path("CommentId") int CommentID, @Path("userId") int userId, Callback<ResponseModel> response);
//
//    /**
//     * method to edit a specific comment
//     * @param CommentID
//     * @param comment
//     * @param response
//     */
//    @FormUrlEncoded()
//    @POST("/comments/edit/{CommentId}")
//    void editComment(@Path("CommentId") int CommentID, @Field("comment") String comment, Callback<ResponseModel> response);
//
//    /**
//     * method to add a new comment
//     * @param feedID
//     * @param comment
//     * @param response
//     */
//    @FormUrlEncoded()
//    @POST("/comments/add/{feedId}")
//    void addComment(@Path("feedId") int feedID, @Field("comment") String comment, Callback<ResponseModel> response);
//
//    /**
//     * method to send a new report
//     * @param CommentID
//     * @param Reason
//     * @param Description
//     * @param updateResponse
//     */
//    @Multipart
//    @POST("/comments/NewReport/{CommentId}")
//    void insertNewReport(@Path("CommentId") int CommentID,
//                         @Part("Reason") String Reason,
//                         @Part("Description") String Description,
//                         Callback<ResponseModel> updateResponse);
//
//}
