package Result;

/**
 * Represents the result of a load operation.
 */
public class LoadResult {
    /**
     * A message describing the result of the operation.
     */
    private String message;

    /**
     * Whether the operation was successful.
     */
    private boolean success;

    /**
     * Constructs a new LoadResult object.
     * @param message A message describing the result of the operation.
     * @param success Whether the operation was successful.
     */
    public LoadResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Constructs a new LoadResult object with default values.
     */
    public LoadResult() {}

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
