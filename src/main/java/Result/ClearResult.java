package Result;

/**
 * A class that represents the result of a clear request.
 */
public class ClearResult {

    /**
     * A message indicating the result of the request
     */
    private String message;

    /**
     * Whether the clear request was successful
     */
    private boolean success;

    /**
     * Constructs a new ClearResult object with the specified message and success flag.
     * @param message a message indicating the result of the request
     * @param success whether the clear request was successful
     */
    public ClearResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Constructs a new, empty ClearResult object.
     */
    public ClearResult() {}

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
