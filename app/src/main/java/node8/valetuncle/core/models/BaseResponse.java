package node8.valetuncle.core.models;

/**
 * Created by stevensutana on 11/23/16.
 */
public class BaseResponse {

    private String pk;
    private String msg;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The pk
     */
    public String getPk() {
        return pk;
    }

    /**
     *
     * @param pk
     * The pk
     */
    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
