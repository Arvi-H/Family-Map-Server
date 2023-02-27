package Result;

/**
 * A class to represent the result of a Person-related request to the server.
 */
public class PersonResult {

    /** The array of PersonResult objects representing the requested persons. */
    private PersonResult[] data;

    /** An optional message indicating the result of the request. */
    private String message;

    /** A flag indicating whether the request was successful or not. */
    private boolean success;

    /**
     * Constructs a new PersonResult object with the specified data, message, and success flag.
     *
     * @param data an array of PersonResult objects representing the requested persons.
     * @param message an optional message indicating the result of the request.
     * @param success a flag indicating whether the request was successful or not.
     */
    public PersonResult(PersonResult[] data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    /**
     * Constructs a new, empty PersonResult object.
     */
    public PersonResult() {}

    public PersonResult[] getData() {
        return data;
    }

    public void setData(PersonResult[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
