package node8.valetuncle.core.models;

public class RegisterModel {

    private int pk;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String website;
    private String description;
    private Object photo;
    private String isResto;

    /**
     *
     * @return
     * The pk
     */
    public int getPk() {
        return pk;
    }

    /**
     *
     * @param pk
     * The pk
     */
    public void setPk(int pk) {
        this.pk = pk;
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
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param website
     * The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The photo
     */
    public Object getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The isResto
     */
    public String getIsResto() {
        return isResto;
    }

    /**
     *
     * @param isResto
     * The is_resto
     */
    public void setIsResto(String isResto) {
        this.isResto = isResto;
    }

}