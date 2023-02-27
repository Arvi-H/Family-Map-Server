package Request;

import Model.*;

/**
 * The LoadRequest class represents a request to load data into the family map database.
 */
public class LoadRequest {
    /**
     * An array of User objects to be loaded into the database
     */
    private User[] users;
    /**
     * An array of Person objects to be loaded into the database
     */
    private Person[] persons;
    /**
     * An array of Event objects to be loaded into the database
     */
    private Event[] events;

    /**
     * Creates a LoadRequest object with the specified users, persons, and events.
     * @param users An array of User objects to be loaded into the database
     * @param persons An array of Person objects to be loaded into the database
     * @param events An array of Event objects to be loaded into the database
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     * Creates an empty LoadRequest object.
     */
    public LoadRequest() {}

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
