package ucr.ac.C12599.room.handlers.commands.impl;


import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public class EncryptionHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        System.out.println("Encriptando los datos sensibles del usuario: " + request.getUser());

        // Simulación de encriptación (sólo para mostrar el proceso)
        request.setData(encryptData(request.getData()));

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    private String encryptData(String data) {
        return "ENCRYPTED(" + data + ")";
    }
}
