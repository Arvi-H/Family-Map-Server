package Result;

import Model.Person;

/**
 * A class to represent the result of a Person-related request to the server.
 */
public class PersonsResult extends Result {

    /** The array of PersonResult objects representing the requested persons. */
    private Person[] data;

    /**
     * Constructs a new, empty PersonResult object.
     */
    public PersonsResult() {}

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }
}
