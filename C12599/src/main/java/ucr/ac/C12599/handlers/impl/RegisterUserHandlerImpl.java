package ucr.ac.C12599.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.C12599.JPA.entities.userEntity;
import ucr.ac.C12599.JPA.repositories.UserRepository;
import ucr.ac.C12599.handlers.RegisterUserHandler;

import java.util.UUID;

@Component
public class RegisterUserHandlerImpl implements RegisterUserHandler {
    @Autowired
    private UserRepository repository;
    @Override
    public Result RegisterUser(Command command) {
        var user = new userEntity();
        user.setId(UUID.randomUUID());
        user.setName(command.name());
        user.setEmail(command.email());
        repository.save(user);
        return new Result.Success("OK");
    }
}
