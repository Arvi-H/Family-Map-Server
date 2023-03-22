package DataAccess;

import JSONData.Location;
import JSONData.Locations;
import Model.Event;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static Network.Deserializer.*;
import static Network.RandomUUID.getRandomUUID;

/**
 * The EventDAO class provides methods to access and manipulate Event data in the database.
 * It includes methods to insert new events, find events by their ID, and clear the entire Event table.
 */
public class EventDao {
    /** The database connection used for executing SQL queries. */
    private final Connection conn;

    private Locations locations;
    private int eventCount;
    /**
     * Constructs a new EventDAO object with the specified database connection.
     * @param conn the database connection to use.
     */
    public EventDao(Connection conn) {
        eventCount = 0;
        this.conn = conn;
        try {
            locations = deserializeLocationsList(new File("json/locations.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new Event object into the database.
     * @param event the Event object to insert.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
        eventCount++;
    }

    /**
     * Finds an Event object in the database by its token string.
     * @param eventID the ID string to search for.
     * @return the Event object with the specified token string, or null if not found.
     * @throws DataAccessException if an error occurs during database access.
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * Clears all Event objects from the database.
     * @throws DataAccessException if an error occurs during database access.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public void clearEvent(String username) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Remove event based on given username
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public Event[] findAllEvents(String userName) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<Event>();

        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered when finding all events for a person");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        Event[] list = events.toArray(new Event[events.size()]);
        return list;
    }

    public boolean eventExists(String eventID) throws DataAccessException {

        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();

            if (!rs.next())
                return false;
            else
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

    public Location getRandomLocation(Locations locations) {
        Location[] data = locations.getLocations();
        int randomIndex = new Random().nextInt(data.length);
        return data[randomIndex];
    }

    public void generateEvent(String associatedUsername, String personID, int year, String eventType) throws DataAccessException {
        Location location = getRandomLocation(locations);
        Event event = new Event(getRandomUUID(), associatedUsername, personID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
        insert(event);
    }
    public void generateMarriageEvent(String associatedUsername, int year, String eventType, String fatherID, String motherID) throws DataAccessException {
        Location location = getRandomLocation(locations);
        Event event = new Event(getRandomUUID(), associatedUsername, motherID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
        Event event2 = new Event(getRandomUUID(), associatedUsername, fatherID, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
        insert(event);
        insert(event2);
    }

    public void generateBirth(String associatedUsername, String personID, int year) throws DataAccessException {
        generateEvent(associatedUsername, personID, year, "birth");
    }

    public void generateMarriage(String associatedUsername, String fatherID, String motherID, int year) throws DataAccessException {
        generateMarriageEvent(associatedUsername, (year-5), "marriage", fatherID, motherID);
    }

    public void generateDeath(String associatedUsername, String personID, int year) throws DataAccessException {
        generateEvent(associatedUsername, personID, (year+65), "death");
    }

    public int getNumOfEvents() {
        return eventCount;
    }
}
