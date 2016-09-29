/**
 * Created by Zach on 9/29/16.
 */
public class User {
    Integer id;
    String userName;
    String address;
    String eMail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public User() {}

    public User(Integer id, String userName, String address, String eMail) {

        this.id = id;
        this.userName = userName;
        this.address = address;
        this.eMail = eMail;
    }
}
