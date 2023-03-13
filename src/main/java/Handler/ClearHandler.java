package Handler;

import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ClearHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isPOSTRequest(exchange)) {
                ClearService clearService = new ClearService();
                ClearResult clearResult = clearService.clear();

                sendResponse(exchange, clearResult);
                parseResponse(exchange, clearResult);
            } else {
                sendBadRequestResponse(exchange);
            }
            closeExchange(exchange);
        } catch (IOException e) {
            handleIOException(exchange, e);
        }
    }
}

