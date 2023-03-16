package DataAccess;

import Model.AuthToken;

import java.sql.*;

/**
 * The AuthTokenDAO class is responsible for managing the storage and retrieval of AuthToken objects from our database.
 * It provides methods for inserting new AuthToken objects, finding an AuthToken by its
 * token string, and clearing all AuthToken objects from the database.
 */
public class AuthTokenDao {

    /** The database connection used for executing SQL queries. */
    private final Connection conn;

    /**
     * Constructs a new AuthTokenDAO object with the specified database connection.
     * @param conn the database connection to use.
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new AuthToken object into the database.
     * @param token the AuthToken object to insert.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void insert(AuthToken token) throws DataAccessException {
        String sql = "INSERT INTO Authtokens(auth_token, username) VALUES(?,?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthtoken());
            stmt.setString(2, token.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error when adding new token");
        }
    }

    /**
     * Finds an AuthToken object in the database by its token string.
     * @param authToken the token string to search for.
     * @return the AuthToken object with the specified token string, or null if not found.
     * @throws DataAccessException if an error occurs during database access.
     */
    public AuthToken find(String authToken) throws DataAccessException {
        AuthToken a;
        ResultSet rs;
        String sql = "SELECT * FROM Authtokens WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                a = new AuthToken(rs.getString("auth_token"), rs.getString("username"));
                return a;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * Clears all AuthToken objects from the database.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM Authtokens;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered when clearing authorization tokens table");
        }
    }
}
