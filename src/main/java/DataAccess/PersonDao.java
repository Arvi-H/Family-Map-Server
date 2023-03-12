package DataAccess;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The PersonDAO class provides methods to access and manipulate Event data in the database.
 * It includes methods to insert new events, find events by their ID, and clear the entire Event table.
 */
public class PersonDao {
    /** The database connection used for executing SQL queries. */
    private final Connection conn;

    /**
     * Constructs a new PersonDAO object with the specified database connection.
     * @param conn the database connection to use.
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new Person object into the database.
     * @param person the Person object to insert.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void insert(Person person) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Persons (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds a Person object in the database by its token string.
     * @param personID the ID string to search for.
     * @return the Person object with the specified token string, or null if not found.
     * @throws DataAccessException if an error occurs during database access.
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Clears all Person objects from the database.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }
}
