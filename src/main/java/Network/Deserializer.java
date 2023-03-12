package Network;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Deserializer {
    public static <T> T deserialize(String val, Class<T>  returnType) {
        return (new Gson()).fromJson(val, returnType);
    }

    public static <T> T deserializeFromFile(String fileName, Class<T> dataClass) throws IOException {
        try (FileReader fileReader = new FileReader(fileName)) {
            return (new Gson()).fromJson(fileReader, dataClass);
        } catch (IOException e) {
            throw new IOException("Error while deserializing from file: " + e.getMessage());
        }
    }
}
