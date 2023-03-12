package JSONData;

import java.util.Random;

import static Network.Deserializer.deserializeFromFile;

public class JsonData {
    private Location[] locations;
    private String[] femaleNames;
    private String[] maleNames;
    private String[] surnames;

    public JsonData() {
        String path = System.getProperty("user.home") + "/Documents/Code/Family-Map-Server/json/";
        try {
            locations = deserializeFromFile(path + "locations.json", Location[].class);
            femaleNames = deserializeFromFile(path + "fnames.json", String[].class);
            maleNames = deserializeFromFile(path + "mnames.json", String[].class);
            surnames = deserializeFromFile(path + "snames.json", String[].class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Location getRandomLocation() {
        return locations[(new Random()).nextInt(locations.length)];
    }

    public String getRandomFemaleName() {
        return femaleNames[(new Random()).nextInt(femaleNames.length)];
    }

    public String getRandomMaleName() {
        return maleNames[(new Random()).nextInt(maleNames.length)];
    }

    public String getRandomSurname() {
        return surnames[(new Random()).nextInt(surnames.length)];
    }
}
