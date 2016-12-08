
package node8.valetuncle.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transaction extends RealmObject{

    @SerializedName("transactionid")
    @Expose
    @PrimaryKey
    private Integer transactionid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("create")
    @Expose
    private String create;
    @SerializedName("fee")
    @Expose
    private Integer fee;
    @SerializedName("pickup")
    @Expose
    private String pickup;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("pickupaddress")
    @Expose
    private String pickupaddress;
    @SerializedName("actuallocation")
    @Expose
    private String actuallocation;
    @SerializedName("promocode")
    @Expose
    private String promocode;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The transactionid
     */
    public Integer getTransactionid() {
        return transactionid;
    }

    /**
     *
     * @param transactionid
     * The transactionid
     */
    public void setTransactionid(Integer transactionid) {
        this.transactionid = transactionid;
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
     * The remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     * The remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * The actuallocation
     */
    public String getActuallocation() {
        return actuallocation;
    }

    /**
     *
     * @param actuallocation
     * The actuallocation
     */
    public void setActuallocation(String actuallocation) {
        this.actuallocation = actuallocation;
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

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}