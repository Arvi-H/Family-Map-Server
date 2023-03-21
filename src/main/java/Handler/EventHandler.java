package Handler;

import Result.EventIDResult;
import Result.EventsResult;
import Service.EventIDService;
import Service.EventsService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class EventHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isGETRequest(exchange)) {
                if (hasAuthorizationKey(exchange)) {
                    EventIDResult eventIDResult;
                    EventsResult eventsAllResult;

                    EventIDService eventIDService = new EventIDService();
                    EventsService eventAllService = new EventsService();

                    String authID = exchange.getRequestHeaders().getFirst("Authorization");
                    String[] paths = exchange.getRequestURI().getPath().split("/");

                    switch (paths.length) {
                        case 2:
                            eventsAllResult = eventAllService.getAllEvents(authID);
                            sendResponse(exchange, eventsAllResult);
                            parseResponse(exchange, eventsAllResult);
                            break;
                        case 3:
                            eventIDResult = eventIDService.getEventById(paths[2], authID);
                            sendResponse(exchange, eventIDResult);
                            parseResponse(exchange, eventIDResult);
                            break;
                        default:
                            sendBadRequestResponse(exchange);
                            break;
                    }
                } else {
                    sendUnAuthorizedResponse(exchange);
                }
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
