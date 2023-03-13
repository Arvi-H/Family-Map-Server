package Handler;

import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LoadHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isPOSTRequest(exchange)) {
                LoadRequest loadRequest = parseRequest(exchange, Request.LoadRequest.class);
                LoadService loadService = new LoadService();
                LoadResult loadResult = loadService.load(loadRequest);

                sendResponse(exchange, loadResult);
                parseResponse(exchange, loadResult);
            } else {
                sendBadRequestResponse(exchange);
            }
            closeExchange(exchange);
        } catch (IOException e) {
            handleIOException(exchange, e);
        }
    }
}
