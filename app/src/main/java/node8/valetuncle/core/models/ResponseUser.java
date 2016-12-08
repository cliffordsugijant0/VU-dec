package node8.valetuncle.core.models;

/**
 * Created by stevensutana on 11/23/16.
 */
public class ResponseUser {

    private String objectid;
    private String username,email,password,name,phone_number,phone_code,country;
    private int code,statuslogin;
    private int vercode;

    public ResponseUser(String username, String email, String password, String fullName, String phone, String phoneCode, String country, int verCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = fullName;
        this.phone_number = phone;
        this.phone_code = phoneCode;
        this.country = country;
        this.code = verCode;
    }

    public ResponseUser(String user_id) {
        this.objectid = user_id;
    }

    public ResponseUser() {
    }

    public int getStatuslogin() {
        return statuslogin;
    }

    public void setStatuslogin(int statuslogin) {
        this.statuslogin = statuslogin;
    }

    public int getVercode() {
        return vercode;
    }

    public void setVercode(int vercode) {
        this.vercode = vercode;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String user_id) {
        this.objectid = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus_login() {
        return statuslogin;
    }

    public void setStatus_login(int status_login) {
        this.statuslogin = status_login;
    }
}
