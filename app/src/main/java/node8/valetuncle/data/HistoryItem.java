package node8.valetuncle.data;


/**
 * Created by Node8 on 10/4/16.
 */
public class HistoryItem {
//
//    @PrimaryKey
    private String Id;

    private String Date;
    private String Address;
    private String fee;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
