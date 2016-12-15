package node8.valetuncle.core.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @SerializedName("objectid")
    @Expose
    @PrimaryKey
    private String objectid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("emailverified")
    @Expose
    private Integer emailverified;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_code")
    @Expose
    private String phoneCode;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("create")
    @Expose
    private String create;
    @SerializedName("update")
    @Expose
    private String update;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("curpage")
    @Expose
    private String curpage;
    @SerializedName("confirm")
    @Expose
    private String confirm;
    @SerializedName("completeaddress")
    @Expose
    private String completeaddress;
    @SerializedName("curlatlong")
    @Expose
    private String curlatlong;
    @SerializedName("curdriver")
    @Expose
    private String curdriver;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("advance")
    @Expose
    private String advance;
    @SerializedName("vercode")
    @Expose
    private String vercode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statuslogin")
    @Expose
    private Integer statuslogin;
    @SerializedName("transactionid")
    @Expose
    private Integer transactionid;
    @SerializedName("premium")
    @Expose
    private Integer premium;
    @SerializedName("show_promo")
    @Expose
    private Boolean showPromo;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    @SerializedName("version")
    @Expose
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @SerializedName("pk")
    @Expose
    private String pk;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    /**
     *
     * @return
     * The objectid
     */
    public String getObjectid() {
        return objectid;
    }

    /**
     *
     * @param objectid
     * The objectid
     */
    public void setObjectid(String objectid) {
        this.objectid = objectid;
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
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The emailverified
     */
    public Integer getEmailverified() {
        return emailverified;
    }

    /**
     *
     * @param emailverified
     * The emailverified
     */
    public void setEmailverified(Integer emailverified) {
        this.emailverified = emailverified;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phoneCode
     */
    public String getPhoneCode() {
        return phoneCode;
    }

    /**
     *
     * @param phoneCode
     * The phone_code
     */
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    /**
     *
     * @return
     * The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     * The phone_number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
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
     * The update
     */
    public String getUpdate() {
        return update;
    }

    /**
     *
     * @param update
     * The update
     */
    public void setUpdate(String update) {
        this.update = update;
    }

    /**
     *
     * @return
     * The home
     */
    public String getHome() {
        return home;
    }

    /**
     *
     * @param home
     * The home
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     *
     * @return
     * The curpage
     */
    public String getCurpage() {
        return curpage;
    }

    /**
     *
     * @param curpage
     * The curpage
     */
    public void setCurpage(String curpage) {
          this.curpage = curpage;
    }

    /**
     *
     * @return
     * The confirm
     */
    public String getConfirm() {
        return confirm;
    }

    /**
     *
     * @param confirm
     * The confirm
     */
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    /**
     *
     * @return
     * The completeaddress
     */
    public String getCompleteaddress() {
        return completeaddress;
    }

    /**
     *
     * @param completeaddress
     * The completeaddress
     */
    public void setCompleteaddress(String completeaddress) {
        this.completeaddress = completeaddress;
    }

    /**
     *
     * @return
     * The curlatlong
     */
    public String getCurlatlong() {
        return curlatlong;
    }

    /**
     *
     * @param curlatlong
     * The curlatlong
     */
    public void setCurlatlong(String curlatlong) {
        this.curlatlong = curlatlong;
    }

    /**
     *
     * @return
     * The curdriver
     */
    public String getCurdriver() {
        return curdriver;
    }

    /**
     *
     * @param curdriver
     * The curdriver
     */
    public void setCurdriver(String curdriver) {
        this.curdriver = curdriver;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The advance
     */
    public String getAdvance() {
        return advance;
    }

    /**
     *
     * @param advance
     * The advance
     */
    public void setAdvance(String advance) {
        this.advance = advance;
    }

    /**
     *
     * @return
     * The vercode
     */
    public String getVercode() {
        return vercode;
    }

    /**
     *
     * @param vercode
     * The vercode
     */
    public void setVercode(String vercode) {
        this.vercode = vercode;
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
     * The statuslogin
     */
    public Integer getStatuslogin() {
        return statuslogin;
    }

    /**
     *
     * @param statuslogin
     * The statuslogin
     */
    public void setStatuslogin(Integer statuslogin) {
        this.statuslogin = statuslogin;
    }

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

    /**
     *
     * @return
     * The showPromo
     */
    public Boolean getShowPromo() {
        return showPromo;
    }

    /**
     *
     * @param showPromo
     * The show_promo
     */
    public void setShowPromo(Boolean showPromo) {
        this.showPromo = showPromo;
    }

    /**
     *
     * @return
     * The photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     *
     * @param photoUrl
     * The photo_url
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public User() {
    }

    public User(String objectid) {
        this.objectid = objectid;
    }


    public User(String username, String email, String password, String fullName, String phone, String phoneCode, String country, String verCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = fullName;
        this.phoneNumber = phone;
        this.phoneCode = phoneCode;
        this.country = country;
        this.vercode = verCode;
    }


}