package ucr.ac.C12599.room.handlers.commands.impl;

import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public class CachingHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        System.out.println("Checking cache for data: " + request.getData());

        if (isCached(request)) {
            System.out.println("Cached response found for data: " + request.getData());
            request.setCached(true);
        } else {
            System.out.println("No cached response, processing request.");
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        }
    }

    private boolean isCached(RequestEntity request) {
        // Simulate cache lookup logic
        return false;
    }
}
