package Network;

import JSONData.Location;
import JSONData.Locations;
import JSONData.Names;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class Deserializer {
    public static <T> T deserialize(String val, Class<T>  returnType) {
        return (new Gson()).fromJson(val, returnType);
    }

    public static ArrayList<String> deserializeFromNamesFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return (new Gson()).fromJson(reader, new TypeToken<ArrayList<String>>(){}.getType());
        } catch (IOException e) {
            throw new IOException("Error while deserializing from file: " + e.getMessage());
        }
    }
    public static Names deserializeNameList(File filename) throws IOException {
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, Names.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
    public static Locations deserializeLocationsList(File filename) throws IOException {
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, Locations.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
}
