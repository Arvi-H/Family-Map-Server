package Request;

/**
 * The RegisterRequest class represents a request to register a new user account.
 */
public class RegisterRequest {
    /**
     * Unique username for the new user
     */
    private String username;
    /**
     * Password for the new user account
     */
    private String password;
    /**
     * Email address for the new user
     */
    private String email;
    /**
     * First name for the new user
     */
    private String firstName;
    /**
     * Last name for the new user
     */
    private String lastName;
    /**
     * Gender for the new user
     */
    private String gender;

    /**
     * Constructs a new RegisterRequest object with the given parameters.
     *
     * @param username Unique username for the new user
     * @param password Password for the new user account
     * @param email Email address for the new user
     * @param firstName First name for the new user
     * @param lastName Last name for the new user
     * @param gender Gender for the new user
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Constructs a new empty RegisterRequest object.
     */
    public RegisterRequest() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
