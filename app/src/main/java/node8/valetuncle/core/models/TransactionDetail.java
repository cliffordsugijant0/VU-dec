package node8.valetuncle.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by stevensutana on 11/28/16.
 */

public class TransactionDetail {


    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Transaction result;
    @SerializedName("error")
    @Expose
    private Boolean error;

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
     * The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The result
     */
    public Transaction getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(Transaction result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }
}
