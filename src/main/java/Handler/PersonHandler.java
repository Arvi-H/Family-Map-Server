package Handler;

import Result.PersonIDResult;
import Result.PersonsResult;
import Service.PersonIDService;
import Service.PersonsService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class PersonHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isGETRequest(exchange)) {
                if (hasAuthorizationKey(exchange)) {
                    PersonIDResult personIDResult;
                    PersonsResult personsAllResult;

                    PersonIDService personIDService = new PersonIDService();
                    PersonsService personsAllService = new PersonsService();

                    String authID = exchange.getRequestHeaders().getFirst("Authorization");
                    String[] paths = exchange.getRequestURI().getPath().split("/");

                    switch (paths.length) {
                        case 2:
                            personsAllResult = personsAllService.getPersons(authID);
                            sendResponse(exchange, personsAllResult);
                            parseResponse(exchange, personsAllResult);
                            break;
                        case 3:
                            personIDResult = personIDService.getPerson(paths[2], authID);
                            sendResponse(exchange, personIDResult);
                            parseResponse(exchange, personIDResult);
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
