package Result;

/**
 * This class represents the result of a user registration attempt.
 */
public class RegisterResult extends Result{

    /**
     * The authentication token generated for the user
     */
    private String authtoken;

    /**
     * The username of the registered user
     */
    private String username;

    /**
     * The ID of the person associated with the registered user
     */

    private String personID;

    /**
     * Constructs a new RegisterResult object with the given parameters.
     *
     * @param authtoken the authentication token generated for the user
     * @param username the username of the registered user
     * @param personID the ID of the person associated with the registered user
     */
    public RegisterResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    public RegisterResult() {}

    public void setRegisterResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }
    public RegisterResult(String message) {
        super();
    }

    public String getAuth_token() {
        return authtoken;
    }

    public void setAuth_token(String auth_token) {
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
