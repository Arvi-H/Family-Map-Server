package Result;

public class Result {
    /**
     * A message describing the result of the operation.
     */
    protected String message;

    /**
     * Whether the operation was successful.
     */
    protected boolean success;

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
