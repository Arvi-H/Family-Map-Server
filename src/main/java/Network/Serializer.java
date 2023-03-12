package Network;

import com.google.gson.Gson;

public class Serializer {
    public static <T> String serialize(T returnType) {
        return (new Gson()).toJson(returnType);
    }
}