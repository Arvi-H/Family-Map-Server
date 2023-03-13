package Result;

/**
 * A class to represent the result of a Person-related request to the server.
 */
public class PersonsResult extends Result {

    /** The array of PersonResult objects representing the requested persons. */
    private PersonsResult[] data;

    /**
     * Constructs a new PersonResult object with the specified data, message, and success flag.
     *
     * @param data an array of PersonResult objects representing the requested persons.
     */
    public PersonsResult(PersonsResult[] data) {
        this.data = data;
    }

    /**
     * Constructs a new, empty PersonResult object.
     */
    public PersonsResult() {}

    public PersonsResult[] getData() {
        return data;
    }

    public void setData(PersonsResult[] data) {
        this.data = data;
    }
}
