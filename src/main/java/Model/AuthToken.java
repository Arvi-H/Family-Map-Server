package Model;

/**
 * The AuthToken class represents an authentication token object in the family map application.
 */
public class AuthToken {
    /**
     * The authentication token string.
     */
    private String authToken;

    /**
     * The username that is associated with the authToken.
     */
    private String username;

    /**
     * Constructs a new AuthToken object with the specified authentication token string and username.
     * @param auth_token The authentication token string.
     * @param username The username that is associated with the authToken.
     */
    public AuthToken(String auth_token, String username) {
        this.authToken = auth_token;
        this.username = username;
    }

    /**
     * Constructs a new empty AuthToken object.
     */
    public AuthToken() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
