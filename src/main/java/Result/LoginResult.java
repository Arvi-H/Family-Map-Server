package Result;

/**
 * This class represents a user login result.
 * It contains the authentication token, username, personID, success status, and message.
 */
public class LoginResult extends Result {
    /** The authentication token. */
    private String authToken;

    /** The username. */
    private String username;

    /** The person ID associated with the user. */
    private String personID;

    /**
     * Constructs a new LoginResult object.
     * @param authToken the authentication token to set
     * @param username the username to set
     * @param personID the person ID to set
     */
    public LoginResult(String authToken, String username, String personID, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    /**
     * Constructs an empty LoginResult object.
     */
    public LoginResult() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String auth_token) {
        this.authToken = auth_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
