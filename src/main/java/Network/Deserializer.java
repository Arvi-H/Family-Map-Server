package Network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Deserializer {
    public static <T> T deserialize(String val, Class<T>  returnType) {
        return (new Gson()).fromJson(val, returnType);
    }

    public static ArrayList<String> deserializeFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return (new Gson()).fromJson(reader, new TypeToken<ArrayList<String>>(){}.getType());
        } catch (IOException e) {
            throw new IOException("Error while deserializing from file: " + e.getMessage());
        }
    }

}
