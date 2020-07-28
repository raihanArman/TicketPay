package id.co.myproject.ticketpay.Model;

public class User {
    private String user_id;
    private String email;
    private String username;

    public User() {
    }

    public User(String user_id, String email, String username) {
        this.user_id = user_id;
        this.email = email;
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
