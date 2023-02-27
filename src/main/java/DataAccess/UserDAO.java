package DataAccess;

import Model.User;
import java.sql.Connection;

/**
 * The UserDAO class provides methods to access and manipulate Event data in the database.
 * It includes methods to insert new events, find events by their ID, and clear the entire Event table.
 */
public class UserDAO {
    /** The database connection used for executing SQL queries. */
    private final Connection conn;

    /**
     * Constructs a new UserDAO object with the specified database connection.
     * @param conn the database connection to use.
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new User object into the database.
     * @param user the User object to insert.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void insert(User user) throws DataAccessException {}

    /**
     * Finds an User object in the database by its token string.
     * @param username the unique username string to search for.
     * @return the User object with the specified token string, or null if not found.
     * @throws DataAccessException if an error occurs during database access.
     */
    public User find(String username) throws DataAccessException { return null; }

    /**
     * Clears all User objects from the database.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void clear() throws DataAccessException {}
}
