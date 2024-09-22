package ucr.ac.C12599.room.handlers.commands.impl;

import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public class RateLimitHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        System.out.println("Checking rate limits for user: " + request.getUser());

        if (request.getFailedAttempts() > 3) {
            System.out.println("Rate limit exceeded for user: " + request.getUser());
            return;
        }

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}