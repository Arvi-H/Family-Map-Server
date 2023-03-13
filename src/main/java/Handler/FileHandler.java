package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isGETRequest(exchange)) {
                String urlPath = exchange.getRequestURI().getPath();
                String filePath = "web" + (urlPath.equals("/") ? "/index.html" : urlPath);
                File file = new File(filePath);
                File notFoundFile = new File("web/HTML/404.html");

                if (file.exists()) {
                    sendOkResponse(exchange);
                    Files.copy(file.toPath(), exchange.getResponseBody());
                } else {
                    sendBadRequestResponse(exchange);
                    Files.copy(notFoundFile.toPath(), exchange.getResponseBody());
                }
                exchange.close();
                closeExchange(exchange);
            }
        } catch (IOException e) {
            handleIOException(exchange, e);
        }
    }
}
