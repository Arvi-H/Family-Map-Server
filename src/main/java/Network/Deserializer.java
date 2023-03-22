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

    public static <T> T deserializeFromFile(File filename, Class<T> classType) throws IOException {
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, classType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
}
