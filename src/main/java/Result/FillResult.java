package Result;

/**
 * The result of the fill operation, including whether the operation was successful and a message describing the result.
 */
public class FillResult {
    /**
     * A message describing the result of the fill operation.
     */
    private String message;

    /**
     * Whether the fill operation was successful.
     */
    private boolean success;

    /**
     * Constructs a new FillResult object with the given message and success status.
     *
     * @param message A message describing the result of the fill operation.
     * @param success Whether the fill operation was successful.
     */
    public FillResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Constructs a new, empty FillResult object.
     */
    public FillResult() {}

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
