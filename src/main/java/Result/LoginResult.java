package Result;

/**
 * This class represents a user login result.
 * It contains the authentication token, username, personID, success status, and message.
 */
public class LoginResult extends Result {
    /** The authentication token. */
    private String authtoken;

    /** The username. */
    private String username;

    /** The person ID associated with the user. */
    private String personID;

    /**
     * Constructs an empty LoginResult object.
     */
    public LoginResult() {}

    /**
     * @param authToken the authentication token to set
     * @param username the username to set
     * @param personID the person ID to set
     */
    public void setLoginResult(String authToken, String username, String personID) {
        this.authtoken = authToken;
        this.username = username;
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String auth_token) {
        this.authtoken = auth_token;
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
