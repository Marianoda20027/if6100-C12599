package ucr.ac.C12599.room.handlers.commands.impl;


import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public class LoggingHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        System.out.println("Registrando la solicitud del usuario: " + request.getUser());

        // Registrar los datos y la situación del proceso
        System.out.println("Datos actuales: " + request.getData() + ", Usuario: " + request.getUser() + ", Rol: " + request.getRole());

        // Pasar al siguiente manejador
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}