package Result;

/**
 * A class to represent the result of a Person-related request to the server.
 */
public class PersonResult extends Result {

    /** The array of PersonResult objects representing the requested persons. */
    private PersonResult[] data;

    /**
     * Constructs a new PersonResult object with the specified data, message, and success flag.
     *
     * @param data an array of PersonResult objects representing the requested persons.
     */
    public PersonResult(PersonResult[] data) {
        this.data = data;
    }

    /**
     * Constructs a new, empty PersonResult object.
     */
    public PersonResult() {}

    public PersonResult[] getData() {
        return data;
    }

    public void setData(PersonResult[] data) {
        this.data = data;
    }
}
