package ucr.ac.C12599;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucr.ac.C12599.handlers.RegisterUserHandler;

@RestController
public class HelloController {

    @Autowired // usada para inyeccion de dependencias
    // con esto se ve que tiene una instancia en algun lugar.
    private RegisterUserHandler handler;

    @GetMapping("/hello")
    public String hello() {
       var result = handler.RegisterUser(
               new RegisterUserHandler.Command(
                "Mariano",
                "marianoda20027@gmail.com",
                "password"
        )
       );

       return switch (result){ // no es switch convencional es el switch expression
           case RegisterUserHandler.Result.Success success ->
                   success.message();
           case RegisterUserHandler.Result.InvalidData invalidData -> "Invalid data" + String.join(",",invalidData.fields());
       };
    }
}
