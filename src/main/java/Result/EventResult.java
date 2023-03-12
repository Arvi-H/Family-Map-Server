package Result;

/**
 * The result of a request to retrieve events from the database.
 */
public class EventResult extends Result {
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
     */
    public EventResult(EventResult[] data) {
        this.data = data;
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
}
