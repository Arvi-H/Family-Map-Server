package DataAccess;

import Model.AuthToken;
import java.sql.Connection;

/**
 * The AuthTokenDAO class is responsible for managing the storage and retrieval of AuthToken objects from our database.
 * It provides methods for inserting new AuthToken objects, finding an AuthToken by its
 * token string, and clearing all AuthToken objects from the database.
 */
public class AuthTokenDAO {

    /** The database connection used for executing SQL queries. */
    private final Connection conn;

    /**
     * Constructs a new AuthTokenDAO object with the specified database connection.
     * @param conn the database connection to use.
     */
    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new AuthToken object into the database.
     * @param token the AuthToken object to insert.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void insert(AuthToken token) throws DataAccessException {}

    /**
     * Finds an AuthToken object in the database by its token string.
     * @param authToken the token string to search for.
     * @return the AuthToken object with the specified token string, or null if not found.
     * @throws DataAccessException if an error occurs during database access.
     */
    public AuthToken find(String authToken) throws DataAccessException { return null; }

    /**
     * Clears all AuthToken objects from the database.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void clear() throws DataAccessException {}
}
