package DataAccess;

import Model.Person;
import java.sql.Connection;

/**
 * The PersonDAO class provides methods to access and manipulate Event data in the database.
 * It includes methods to insert new events, find events by their ID, and clear the entire Event table.
 */
public class PersonDAO {
    /** The database connection used for executing SQL queries. */
    private final Connection conn;

    /**
     * Constructs a new PersonDAO object with the specified database connection.
     * @param conn the database connection to use.
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new Person object into the database.
     * @param person the Person object to insert.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void insert(Person person) throws DataAccessException {}

    /**
     * Finds a Person object in the database by its token string.
     * @param personID the ID string to search for.
     * @return the Person object with the specified token string, or null if not found.
     * @throws DataAccessException if an error occurs during database access.
     */
    public Person find(String personID) throws DataAccessException { return null; }

    /**
     * Clears all Person objects from the database.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void clear() throws DataAccessException {}
}
