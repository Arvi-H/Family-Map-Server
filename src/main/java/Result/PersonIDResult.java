package Result;

/**
 * PersonIDResult is a class that represents the result of a request to retrieve information about a person from a database.
 */
public class PersonIDResult extends Result {
    /**
     The associated username for the person.
     */
    private String associatedUsername;
    /**
     The ID of the person.
     */
    private String personID;
    /**
     The first name of the person.
     */
    private String firstName;
    /**
     The last name of the person.
     */
    private String lastName;
    /**
     The gender of the person.
     */
    private String gender;
    /**
     The ID of the person's father.
     */
    private String fatherID;
    /**
     The ID of the person's mother.
     */
    private String motherID;
    /**
     The ID of the person's spouse.
     */
    private String spouseID;

    /**
     Constructs a PersonIDResult object with the specified parameters.
     @param associatedUsername The associated username for the person.
     @param personID The ID of the person.
     @param firstName The first name of the person.
     @param lastName The last name of the person.
     @param gender The gender of the person.
     @param fatherID The ID of the person's father.
     @param motherID The ID of the person's mother.
     @param spouseID The ID of the person's spouse.
     */
    public PersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     Constructs an empty PersonIDResult object.
     */
    public PersonIDResult() {}

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
