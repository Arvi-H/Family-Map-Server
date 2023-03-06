package Model;

/**
 * The AuthToken class represents an authentication token object in the family map application.
 */
public class AuthToken {
    /**
     * The authentication token string.
     */
    private String authtoken;

    /**
     * The username that is associated with the authToken.
     */
    private String username;

    /**
     * Constructs a new AuthToken object with the specified authentication token string and username.
     * @param authtoken The authentication token string.
     * @param username The username that is associated with the authToken.
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    /**
     * Constructs a new empty AuthToken object.
     */
    public AuthToken() {}

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
