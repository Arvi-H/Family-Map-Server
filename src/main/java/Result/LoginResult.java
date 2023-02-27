package Result;

/**
 * This class represents a user login result.
 * It contains the authentication token, username, personID, success status, and message.
 */
public class LoginResult {
    /** The authentication token. */
    private String auth_token;

    /** The username. */
    private String username;

    /** The person ID associated with the user. */
    private String personID;

    /** The success status of the login attempt. */
    private boolean success;

    /** The message associated with the login attempt. */
    private String message;

    /**
     * Constructs a new LoginResult object.
     * @param auth_token the authentication token to set
     * @param username the username to set
     * @param personID the person ID to set
     * @param success the success status to set
     * @param message the message to set
     */
    public LoginResult(String auth_token, String username, String personID, boolean success, String message) {
        this.auth_token = auth_token;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }

    /**
     * Constructs an empty LoginResult object.
     */
    public LoginResult() {}

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
