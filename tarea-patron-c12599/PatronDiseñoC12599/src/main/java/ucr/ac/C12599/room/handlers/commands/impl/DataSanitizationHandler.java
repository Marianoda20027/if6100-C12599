package ucr.ac.C12599.room.handlers.commands.impl;

import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public class DataSanitizationHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        System.out.println("Sanitizing data for request: " + request.getData());

        request.setData(sanitize(request.getData()));

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    private String sanitize(String data) {
        // Simulate data sanitization logic
        return data.replaceAll("[^a-zA-Z0-9]", "");
    }
}
