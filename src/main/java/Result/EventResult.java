package Result;

/**
 * The result of a request to retrieve events from the database.
 */
public class EventResult {
    /**
     * An array of events matching the request parameters.
     */
    private EventResult[] data;
    /**
     * A Boolean indicating whether the request was successful.
     */
    private boolean success;

    /**
     * Creates a new instance of the EventResult class.
     * @param data An array of events matching the request parameters.
     * @param success A Boolean indicating whether the request was successful.
     */
    public EventResult(EventResult[] data, boolean success) {
        this.data = data;
        this.success = success;
    }

    /**
     * Creates a new, empty instance of the EventResult class.
     */
    public EventResult() {}

    public EventResult[] getData() {
        return data;
    }

    public void setData(EventResult[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
