package Handler;

import Result.FillResult;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class FillHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isPOSTRequest(exchange)) {
                FillService fillService = new FillService();
                FillResult fillResult;

                String uri = exchange.getRequestURI().toString();
                StringBuilder url = new StringBuilder(uri);
                url.deleteCharAt(0);

                String[] path = url.toString().split("/");
                String username = path[1];

                int generations = path.length == 3 ? Integer.parseInt(path[2]) : 99;
                fillResult = fillService.fill(username, generations);

                sendResponse(exchange, fillResult);
                parseResponse(exchange, fillResult);
            } else {
                sendBadRequestResponse(exchange);
            }
            closeExchange(exchange);
        } catch (IOException e) {
            handleIOException(exchange, e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
