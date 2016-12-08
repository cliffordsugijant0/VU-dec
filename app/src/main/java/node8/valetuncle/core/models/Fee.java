package node8.valetuncle.core.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fee {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("fee")
    @Expose
    private Integer fee;

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
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

}