package node8.valetuncle.core;//package net.node8.munchgram.core;
//
//import com.atouchlab.android.socnetwork.models.ConversationsModel;
//import com.atouchlab.android.socnetwork.models.MessagesModel;
//import com.atouchlab.android.socnetwork.models.ResponseModel;
//
//import java.util.List;
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
// * Created by Abderrahim on 11/2/2015.
// */
//public interface ChatAPI {
//    /**
//     * method to get list conversations
//     * @param page
//     * @param conversations
//     */
//    @GET("/chat/all/{page}")
//    void getConversationsList(@Path("page") int page,
//                              Callback<RealmList<ConversationsModel>> conversations);
//
//    /**
//     * method to get list messages
//     * @param recipientID
//     * @param conversationID
//     * @param messages
//     */
//    @GET("/chat/get/{recipientID}/{conversationID}")
//    void getMessages(@Path("recipientID") int recipientID,
//                     @Path("conversationID") int conversationID,
//                     Callback<List<MessagesModel>> messages);
//
//    /**
//     * method to send a new message
//     * @param reply
//     * @param ConversationID
//     * @param RecipientID
//     * @param updateResponseModelCallback
//     */
//    @Multipart
//    @POST("/chat/add")
//    void addMessage(@Part("reply") String reply,
//                    @Part("image") TypedFile file,
//                    @Part("ConversationID") int ConversationID,
//                    @Part("RecipientID") int RecipientID,
//                    Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to update status of a specific message
//     * @param ConversationID
//     * @param RecipientID
//     * @param updateResponseModelCallback
//     */
//    @FormUrlEncoded()
//    @POST("/chat/Status")
//    void updateStatusMessage(@Field("ConversationID") int ConversationID,
//                             @Field("RecipientID") int RecipientID,
//                             Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to search in list conversations
//     * @param string
//     * @param callback
//     */
//    @FormUrlEncoded
//    @POST("/chat/SearchConversation")
//    void SearchConversation(@Field("string") String string, Callback<RealmList<ConversationsModel>> callback);
//
//    /**
//     * method to delete a conversation
//     * @param ConversationID
//     * @param RecipientID
//     * @param updateResponseModelCallback
//     */
//    @FormUrlEncoded()
//    @POST("/chat/DeleteConversation")
//    void deleteConversation(@Field("ConversationID") int ConversationID,
//                            @Field("RecipientID") int RecipientID,
//                            Callback<ResponseModel> updateResponseModelCallback);
//
//    /**
//     * method to delete a specific message
//     * @param MessageID
//     * @param ConversationID
//     * @param HolderID
//     * @param updateResponseModelCallback
//     */
//    @FormUrlEncoded()
//    @POST("/chat/DeleteMessage")
//    void deleteMessage(
//            @Field("MessageID") int MessageID,
//            @Field("ConversationID") int ConversationID,
//            @Field("HolderID") int HolderID,
//            Callback<ResponseModel> updateResponseModelCallback);
//}
