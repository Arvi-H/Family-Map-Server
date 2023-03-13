package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {

                String urlPath = exchange.getRequestURI().getPath();
                String filePath = "web" + (urlPath.equals("/") ? "/index.html" : urlPath);
                File file = new File(filePath);
                File notFoundFile = new File("web/HTML/404.html");

                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(notFoundFile.toPath(), respBody);
                }

                exchange.close();
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);

            OutputStream respBody = exchange.getResponseBody();
            respBody.write("Error processing request".getBytes());
            respBody.close();

            e.printStackTrace();
        }
    }
}
