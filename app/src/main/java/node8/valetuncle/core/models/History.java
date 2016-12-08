package node8.valetuncle.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by stevensutana on 11/29/16.
 */
public class History extends RealmObject{

    @SerializedName("objectid")
    @Expose
    @PrimaryKey
    private Integer objectid;

    @SerializedName("create")
    @Expose
    private String create;
    @SerializedName("transaction_created")
    @Expose
    private String transactionCreated;
    @SerializedName("fee")
    @Expose
    private Integer fee;
    @SerializedName("pickup")
    @Expose
    private String pickup;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("pickupaddress")
    @Expose
    private String pickupaddress;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("destinationaddress")
    @Expose
    private String destinationaddress;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("trans_id")
    @Expose
    private Integer transId;
    @SerializedName("deductfee")
    @Expose
    private String deductfee;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("promocode")
    @Expose
    private String promocode;

    /**
     *
     * @return
     * The objectid
     */
    public Integer getObjectid() {
        return objectid;
    }

    /**
     *
     * @param objectid
     * The objectid
     */
    public void setObjectid(Integer objectid) {
        this.objectid = objectid;
    }

    /**
     *
     * @return
     * The create
     */
    public String getCreate() {
        return create;
    }

    /**
     *
     * @param create
     * The create
     */
    public void setCreate(String create) {
        this.create = create;
    }

    /**
     *
     * @return
     * The transactionCreated
     */
    public String getTransactionCreated() {
        return transactionCreated;
    }

    /**
     *
     * @param transactionCreated
     * The transaction_created
     */
    public void setTransactionCreated(String transactionCreated) {
        this.transactionCreated = transactionCreated;
    }

    /**
     *
     * @return
     * The fee
     */
    public Integer getFee() {
        return fee;
    }

    /**
     *
     * @param fee
     * The fee
     */
    public void setFee(Integer fee) {
        this.fee = fee;
    }

    /**
     *
     * @return
     * The pickup
     */
    public String getPickup() {
        return pickup;
    }

    /**
     *
     * @param pickup
     * The pickup
     */
    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    /**
     *
     * @return
     * The destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     *
     * @param destination
     * The destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     *
     * @return
     * The pickupaddress
     */
    public String getPickupaddress() {
        return pickupaddress;
    }

    /**
     *
     * @param pickupaddress
     * The pickupaddress
     */
    public void setPickupaddress(String pickupaddress) {
        this.pickupaddress = pickupaddress;
    }

    /**
     *
     * @return
     * The idnumber
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     *
     * @param idnumber
     * The idnumber
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The destinationaddress
     */
    public String getDestinationaddress() {
        return destinationaddress;
    }

    /**
     *
     * @param destinationaddress
     * The destinationaddress
     */
    public void setDestinationaddress(String destinationaddress) {
        this.destinationaddress = destinationaddress;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The transId
     */
    public Integer getTransId() {
        return transId;
    }

    /**
     *
     * @param transId
     * The trans_id
     */
    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    /**
     *
     * @return
     * The deductfee
     */
    public String getDeductfee() {
        return deductfee;
    }

    /**
     *
     * @param deductfee
     * The deductfee
     */
    public void setDeductfee(String deductfee) {
        this.deductfee = deductfee;
    }

    /**
     *
     * @return
     * The review
     */
    public String getReview() {
        return review;
    }

    /**
     *
     * @param review
     * The review
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     *
     * @return
     * The promocode
     */
    public String getPromocode() {
        return promocode;
    }

    /**
     *
     * @param promocode
     * The promocode
     */
    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

}
