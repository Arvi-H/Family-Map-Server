package DataAccess;

import JSONData.Location;
import JSONData.Locations;
import Model.Event;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
            locations = deserializeFromFile(new File("json/locations.json"), Locations.class);
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
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " + "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
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

    public void clear(String username) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public Event[] findAllEvents(String userName) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<>();

        String sql = "SELECT * FROM Events WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    events.add(new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"), rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"), rs.getString("Country"), rs.getString("City"), rs.getString("EventType"), rs.getInt("Year")));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered when finding all events for a person");
        }

        return events.toArray(new Event[0]);
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

    public int getNumOfEvents() {
        return eventCount;
    }
}
