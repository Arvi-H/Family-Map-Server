package Result;

/**
 * This class represents the result of a user registration attempt.
 */
public class RegisterResult {

    /**
     * The authentication token generated for the user
     */
    private String auth_token;

    /**
     * The username of the registered user
     */
    private String username;

    /**
     * The ID of the person associated with the registered user
     */

    private String personID;

    /**
     * Whether the registration was successful
     */
    private boolean success;

    /**
     * A message indicating the result of the registration attempt
     */
    private String message;

    /**
     * Constructs a new RegisterResult object with the given parameters.
     *
     * @param auth_token the authentication token generated for the user
     * @param username the username of the registered user
     * @param personID the ID of the person associated with the registered user
     * @param success whether the registration was successful
     * @param message a message indicating the result of the registration attempt
     */
    public RegisterResult(String auth_token, String username, String personID, boolean success, String message) {
        this.auth_token = auth_token;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }

    /**
     * Constructs a new RegisterResult object with default values.
     */
    public RegisterResult() {}

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
