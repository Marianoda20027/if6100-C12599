package ucr.ac.C12599.room.handlers;


import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public interface RequestHandler {
    void setNextHandler(RequestHandler nextHandler);
    void handleRequest(RequestEntity requestEntity);
}