package DataAccess;

import JSONData.Names;
import Model.Person;
import Model.User;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static Network.Deserializer.deserializeFromFile;
import static Network.RandomUUID.getRandomUUID;

/**
 * The PersonDAO class provides methods to access and manipulate Event data in the database.
 * It includes methods to insert new events, find events by their ID, and clear the entire Event table.
 */
public class PersonDao {
    /** The database connection used for executing SQL queries. */
    private final Connection conn;
    private ArrayList<String> maleNames;
    private ArrayList<String> femaleNames;
    private ArrayList<String> surnamesList;
    private int numPersons;
    /**
     * Constructs a new PersonDAO object with the specified database connection.
     * @param conn the database connection to use.
     */
    public PersonDao(Connection conn) {
        numPersons = 0;
        this.conn = conn;
        try {
            Names males = deserializeFromFile(new File("json/mnames.json"), Names.class);
            Names females = deserializeFromFile(new File("json/fnames.json"), Names.class);
            Names surnames = deserializeFromFile(new File("json/snames.json"), Names.class);

            maleNames = new ArrayList<String>(Arrays.asList(males.getNames()));
            femaleNames = new ArrayList<String>(Arrays.asList(females.getNames()));
            surnamesList = new ArrayList<String>(Arrays.asList(surnames.getNames()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        numPersons++;
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

    public void clearPerson(String username) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Remove person based on given username
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    public void generateTree(User user, String personID, int numGenerations, EventDao eventDao) throws DataAccessException {
        int birthYear = 1995;

        // Create a new Person object for the user and add it to the database
        insert(new Person(personID, user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender(), null, null, null));

        // Generate a birth event for the user
        eventDao.generateBirth(user.getUsername(), personID, birthYear);

        // Generate the parents for the user recursively
        if(numGenerations > 0) {
            generateParents(user.getUsername(), personID, birthYear, (numGenerations - 1), eventDao, user.getLastName());
        }
    }

    public String getRandomName(ArrayList<String> names) {
        return names.get(new Random().nextInt(names.size()));
    }

    public void generateParents(String username, String IDOfChild, int birthYearOfChild, int numGenerations, EventDao eventDao, String fatherLastName) throws DataAccessException {
        String fatherID = getRandomUUID();
        String motherID = getRandomUUID();

        String fatherName = getRandomName(maleNames);
        String motherName = getRandomName(femaleNames);
        String motherLastName = getRandomName(surnamesList);

        Person father = new Person(fatherID, username, fatherName, fatherLastName, "m", null, null, motherID);
        Person mother = new Person(motherID, username, motherName, motherLastName, "f", null, null, fatherID);

        insertFatherID(IDOfChild, fatherID);
        insertMotherID(IDOfChild, motherID);
        insert(father);
        insert(mother);

        // Generate and insert events for parents
        eventDao.generateBirth(username, fatherID, (birthYearOfChild - 26));
        eventDao.generateBirth(username, motherID, (birthYearOfChild - 26));
        eventDao.generateMarriage(username, fatherID, motherID, (birthYearOfChild - 5));
        eventDao.generateDeath(username, fatherID, (birthYearOfChild + 10));
        eventDao.generateDeath(username, motherID, (birthYearOfChild + 8));

        if(numGenerations > 0) {
            generateParents(username, fatherID, (birthYearOfChild - 26), (numGenerations - 1), eventDao, fatherLastName);
            generateParents(username, motherID, (birthYearOfChild - 26), (numGenerations - 1), eventDao, motherLastName);
        }
    }

    public void insertMotherID(String userID, String motherID) throws DataAccessException {
        String sql = "UPDATE Persons SET motherID = ? WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, motherID);
            stmt.setString(2, userID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting user's motherID");
        }
    }

    public Person[] getPersonsForUsername(String username) throws DataAccessException {
        ArrayList<Person> persons = new ArrayList<Person>();

        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Person person = new Person(rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID"));

                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding persons for the username given");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        Person[] list = persons.toArray(new Person[persons.size()]);
        return list;
    }

    public boolean personExists(String personID) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void insertFatherID(String userID, String fatherID) throws DataAccessException {
        String sql = "UPDATE Persons SET fatherID = ? WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fatherID);
            stmt.setString(2, userID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting user's motherID");
        }
    }

    public int getPersonCount() {
        return numPersons;
    }
}
