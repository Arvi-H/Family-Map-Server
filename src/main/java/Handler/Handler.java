package Handler;

import Result.Result;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;

import static Network.Deserializer.deserialize;
import static Network.Serializer.serialize;

public abstract class Handler {
    protected boolean isPOSTRequest(HttpExchange exchange) {
        return exchange.getRequestMethod().toUpperCase().equals("POST");
    }

    protected boolean isGETRequest(HttpExchange exchange) {
        return exchange.getRequestMethod().toUpperCase().equals("GET");
    }

    protected boolean hasAuthorizationKey(HttpExchange exchange) {
        return exchange.getRequestHeaders().containsKey("Authorization");
    }

    protected  <T> T parseRequest(HttpExchange exchange, Class<T> dataClass) throws IOException {
        InputStream reqBody = exchange.getRequestBody();
        String reqData = readData(reqBody);
        return deserialize(reqData, dataClass);
    }

    protected void parseResponse(HttpExchange exchange, Result response) throws IOException {
        String json = serialize(response);
        OutputStream os = exchange.getResponseBody();
        writeData(json, os);
    }

    protected void sendResponse(HttpExchange exchange, Result response) throws IOException {
        exchange.sendResponseHeaders(response.isSuccess() ? HttpURLConnection.HTTP_OK : HttpURLConnection.HTTP_BAD_REQUEST, 0);
    }

    protected void sendBadRequestResponse(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
    }

    protected void sendOkResponse(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
    }

    protected void sendUnAuthorizedResponse(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
    }

    protected void sendInternalServerErrorResponse(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
    }

    protected void closeExchange(HttpExchange exchange) throws IOException {
        exchange.getResponseBody().close();
    }

    protected void handleIOException(HttpExchange exchange, IOException e) throws IOException {
        sendInternalServerErrorResponse(exchange);
        closeExchange(exchange);
        e.printStackTrace();
    }

    protected String readData(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    protected void writeData(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
