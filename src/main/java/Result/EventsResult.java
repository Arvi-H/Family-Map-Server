package Result;

import Model.Event;

/**
 * The result of a request to retrieve events from the database.
 */
public class EventsResult extends Result {
    /**
     * An array of events matching the request parameters.
     */
    private Event[] data;

    /**
     * Creates a new, empty instance of the EventResult class.
     */
    public EventsResult() {}

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
