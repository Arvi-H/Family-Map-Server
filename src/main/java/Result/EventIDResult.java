package Result;

/**
 * EventIDResult class represents the result of a request for a single event from the server
 */
public class EventIDResult extends Result {
    /**
     * The username of the user account this event belongs to
     */
    private String associatedUsername;
    /**
     * The unique ID of the event
     */
    private String eventID;
    /**
     * The person ID associated with this event
     */
    private String personID;
    /**
     * The latitude of the event's location
     */
    private float latitude;
    /**
     * The longitude of the event's location
     */
    private float longitude;
    /**
     * The country of the event's location
     */
    private String country;
    /**
     * The city of the event's location
     */
    private String city;
    /**
     * The type of event
     */
    private String eventType;
    /**
     * The year of the event
     */
    private int year;

    /**
     * Constructor for creating a new EventIDResult object with all fields set
     * @param associatedUsername the username of the user account this event belongs to
     * @param eventID the unique ID of the event
     * @param personID the person ID associated with this event
     * @param latitude the latitude of the event's location
     * @param longitude the longitude of the event's location
     * @param country the country of the event's location
     * @param city the city of the event's location
     * @param eventType the type of event
     * @param year the year of the event
     */
    public EventIDResult(String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Empty constructor for creating a new EventIDResult object with no fields set
     */
    public EventIDResult() {}

    public void setEventIDResult(String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
