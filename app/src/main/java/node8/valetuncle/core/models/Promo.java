
package node8.valetuncle.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Promo extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("promo_title")
    @Expose
    private String promoTitle;
    @SerializedName("fee")
    @Expose
    private Integer fee;
    @SerializedName("promo_detail")
    @Expose
    private String promoDetail;
    @SerializedName("promo_picture")
    @Expose
    private String promoPicture;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("premium")
    @Expose
    private Integer premium;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The promoCode
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     *
     * @param promoCode
     * The promo_code
     */
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    /**
     *
     * @return
     * The promoTitle
     */
    public String getPromoTitle() {
        return promoTitle;
    }

    /**
     *
     * @param promoTitle
     * The promo_title
     */
    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
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
     * The promoDetail
     */
    public String getPromoDetail() {
        return promoDetail;
    }

    /**
     *
     * @param promoDetail
     * The promo_detail
     */
    public void setPromoDetail(String promoDetail) {
        this.promoDetail = promoDetail;
    }

    /**
     *
     * @return
     * The promoPicture
     */
    public String getPromoPicture() {
        return promoPicture;
    }

    /**
     *
     * @param promoPicture
     * The promo_picture
     */
    public void setPromoPicture(String promoPicture) {
        this.promoPicture = promoPicture;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The premium
     */
    public Integer getPremium() {
        return premium;
    }

    /**
     *
     * @param premium
     * The premium
     */
    public void setPremium(Integer premium) {
        this.premium = premium;
    }

}