package Result;

/**
 * The result of a request to retrieve events from the database.
 */
public class EventsResult extends Result {
    /**
     * An array of events matching the request parameters.
     */
    private EventsResult[] data;
    /**
     * A Boolean indicating whether the request was successful.
     */
    private boolean success;

    /**
     * Creates a new instance of the EventResult class.
     * @param data An array of events matching the request parameters.
     */
    public EventsResult(EventsResult[] data) {
        this.data = data;
    }

    /**
     * Creates a new, empty instance of the EventResult class.
     */
    public EventsResult() {}

    public EventsResult[] getData() {
        return data;
    }

    public void setData(EventsResult[] data) {
        this.data = data;
    }
}
