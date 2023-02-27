package Request;

/**
 * The LoginRequest class represents a request to log in a user.
 */
public class LoginRequest {
    /**
     * The username of the user attempting to log in.
     */
    private String username;
    /**
     * The password of the user attempting to log in.
     */
    private String password;

    /**
     * Creates a new LoginRequest object with the given username and password.
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Creates a new empty LoginRequest object.
     */
    public LoginRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
