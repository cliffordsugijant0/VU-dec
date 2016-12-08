package node8.valetuncle.core.models;

public class LoginModel {

    private String isLogin;
    private String userId;
    private String token;

    /**
     *
     * @return
     * The isLogin
     */
    public String getIsLogin() {
        return isLogin;
    }

    /**
     *
     * @param isLogin
     * The is_login
     */
    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

}