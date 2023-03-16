package JSONData;

public class Location {
    private float latitude, longitude;
    private String city, country;

    public Location(float latitude, float longitude, String country, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
    }

    public float getLatitude() {return latitude;}

    public float getLongitude() {return longitude;}

    public String getCity() {return city;}

    public String getCountry() {return country;}
}