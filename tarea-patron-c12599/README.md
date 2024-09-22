# Patrones de Diseño: Chain of Responsibility

## Índice
1. [¿Qué es Chain of Responsibility?](#qué-es-chain-of-responsibility)
2. [Características del Patrón Chain of Responsibility](#características-del-patrón-chain-of-responsibility)
3. [Problema Solucionado por el Patrón Chain of Responsibility](#problema-solucionado-por-el-patrón-chain-of-responsibility)
4. [Descripción de la Solución](#descripción-de-la-solución)
5. [Caso de Estudio: ProjectCloud](#caso-de-estudio-projectcloud)

## ¿Qué es Chain of Responsibility?

El patrón de diseño *Chain of Responsibility* es un patrón de comportamiento que facilita el enrutamiento de solicitudes a través de una serie de manejadores. Cada manejador en la cadena tiene la opción de procesar la solicitud o transferirla al siguiente manejador en la secuencia.

Este patrón se destaca por su flexibilidad al manejar situaciones en las que no está claro qué objeto debe procesar una solicitud específica. Ofrece una solución eficaz en escenarios donde la herencia no es adecuada, utilizando una estructura en cadena en la que una serie de objetos intentan atender la solicitud.

El patrón Chain of Responsibility pertenece a la categoría de patrones de diseño de comportamiento. Estos patrones se centran en cómo los objetos interactúan y colaboran entre sí para lograr un comportamiento específico.

## Características del Patrón Chain of Responsibility

- **Desacoplamiento**: Permite desacoplar el remitente de la solicitud del receptor, ya que el remitente no necesita saber qué objeto en la cadena maneja la solicitud.
- **Encadenamiento**: Organiza una serie de objetos en una cadena, donde cada objeto tiene la opción de procesar la solicitud o pasarla al siguiente objeto en la cadena.
- **Flexibilidad**: Ofrece flexibilidad para agregar o modificar manejadores en la cadena sin alterar el código cliente. Puedes añadir nuevos manejadores o cambiar el orden de la cadena sin afectar a otros componentes.
- **Responsabilidad**: Cada objeto en la cadena tiene una responsabilidad específica y decide si puede manejar la solicitud o debe delegarla al siguiente objeto.

## Problema Solucionado por el Patrón Chain of Responsibility

Imagina que estás trabajando en un sistema de pedidos en línea. Quieres restringir el acceso al sistema de forma que únicamente los usuarios autenticados puedan generar pedidos. Además, los usuarios que tengan permisos administrativos deben tener acceso total a todos los pedidos.

Después de un análisis, te das cuenta de que estas verificaciones deben realizarse secuencialmente. Por ejemplo, la aplicación intentará autenticar a un usuario cuando reciba una solicitud que contenga las credenciales. Sin embargo, si las credenciales no son correctas, no tiene sentido proceder con las siguientes verificaciones.

La solicitud debe pasar una serie de comprobaciones antes de que el sistema de pedidos pueda gestionarla.

### Algunas comprobaciones que se implementan:

1. **Validación de Datos**: No es seguro enviar datos sin procesar al sistema, por lo que se agrega una validación para sanear los datos.
2. **Seguridad**: Se detecta una vulnerabilidad y se implementa una comprobación para bloquear direcciones IP que realicen demasiados intentos fallidos.
3. **Optimización**: Se implementa una verificación de caché para acelerar el sistema devolviendo resultados en caché para solicitudes repetidas.

### Problemas con la implementación inicial

A medida que el código crece, se vuelve más complejo. Cada nueva adición complica el código de validación y mantenimiento. Los cambios en una validación pueden afectar a las demás, y la reutilización de estas comprobaciones en otros componentes requiere duplicar parte del código.

Por eso, decides refactorizar el código utilizando el **Patrón Chain of Responsibility** para simplificar la estructura y mejorar su gestión.

## Descripción de la Solución

> "El patrón Chain of Responsibility permite pasar una solicitud a lo largo de una cadena de objetos hasta que uno de ellos la maneje. Cada objeto en la cadena tiene la posibilidad de procesar la solicitud o pasarla al siguiente objeto en la cadena. Esto desacopla el remitente de la solicitud de su receptor y permite agregar nuevos manejadores sin modificar el código existente."
> — Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides, *Design Patterns: Elements of Reusable Object-Oriented Software*

### Solución Ofrecida por el Patrón Chain of Responsibility

- **Desacoplamiento**: El patrón permite desacoplar el manejo de solicitudes del proceso. Un manejador no necesita conocer todos los detalles de una solicitud; solo se preocupa por su responsabilidad específica.
- **Flexibilidad**: Puedes añadir nuevos manejadores o cambiar el orden de la cadena sin afectar el código de los demás componentes.
- **Escalabilidad**: La estructura en cadena facilita la ampliación del sistema. A medida que los requisitos cambian, puedes reorganizar o expandir la cadena de manejadores.
- **Mantenimiento**: Cada manejador es independiente, lo que hace que las modificaciones en un manejador no afecten a los demás. Esto simplifica el mantenimiento del sistema.

---

## Caso de Estudio: ProjectCloud

### Contexto del Problema (Ficticio)

Imagina que trabajas en una empresa que ha lanzado una nueva aplicación de gestión de proyectos en la nube llamada **"ProjectCloud"**. Esta aplicación permite a los usuarios crear proyectos, asignar tareas y colaborar en tiempo real. Debido a la gran cantidad de usuarios, es crucial implementar varias validaciones antes de permitir el acceso o la ejecución de cualquier acción en la API de **ProjectCloud**.

Los usuarios deben pasar por diferentes pasos de validación antes de acceder a los recursos, como:

1. **Autenticación**: Solo los usuarios autenticados pueden acceder a los recursos.
2. **Validación de Permisos**: Algunos recursos están restringidos a usuarios con roles específicos, como administradores.
3. **Sanitización de Datos**: Los datos enviados (como nombres de proyectos) deben estar limpios de caracteres no permitidos.
4. **Limitación de Tasa (Rate Limiting)**: Se debe limitar el número de solicitudes permitidas por usuario en un período de tiempo.
5. **Caché**: Si una solicitud ha sido procesada recientemente, devolver la respuesta desde la caché mejora el rendimiento.

### Problema

En la implementación inicial, todas estas validaciones estaban dentro de una única función, lo que hacía el código difícil de mantener y propenso a errores. La empresa decide adoptar el **Patrón Chain of Responsibility** para desacoplar las validaciones y mejorar la extensibilidad del sistema.

### Solución: Implementación del Patrón Chain of Responsibility

Cada validación se implementa como un manejador independiente, que es responsable de un aspecto específico de la solicitud (autenticación, permisos, sanitización, etc.). La solicitud se moverá a lo largo de una **cadena** de manejadores, donde cada uno tiene la opción de procesarla o pasarla al siguiente.

### Implementación del Patrón Chain of Responsibility

#### 1. Interfaz `RequestHandler`

Esta interfaz define cómo los manejadores reciben y pasan la solicitud al siguiente manejador en la cadena.

```java
// File: room/handlers/commands/RequestHandler.java
public interface RequestHandler {
    void setNextHandler(RequestHandler nextHandler);
    void handleRequest(Request request);
}
```

#### 2. Clase `Request`

La clase `Request` representa la solicitud que se moverá a través de la cadena. Incluye información del usuario, el rol, los datos y marcadores booleanos que indican si la solicitud ya fue autenticada o está en caché.

```java
// File: room/handlers/commands/Request.java
public class Request {
    private String user;
    private String role;
    private String data;
    private boolean isAuthenticated;
    private boolean isCached;
    private int failedAttempts;

    public Request(String user, String role, String data) {
        this.user = user;
        this.role = role;
        this.data = data;
        this.isAuthenticated = false;
        this.isCached = false;
        this.failedAttempts = 0;
    }

    // Getters and Setters
    public String getUser() { return user; }
    public String getRole() { return role; }
    public String getData() { return data; }
    public boolean isAuthenticated() { return isAuthenticated; }
    public void setAuthenticated(boolean authenticated) { this.isAuthenticated = authenticated; }
    public boolean isCached() { return isCached; }
    public void setCached(boolean cached) { this.isCached = cached; }
    public int getFailedAttempts() { return failedAttempts; }
    public void incrementFailedAttempts() { this.failedAttempts++; }
}
```

#### 3. Manejadores en la Cadena

Cada manejador tiene una responsabilidad específica. A continuación se muestran algunos de los manejadores implementados:

##### Manejador de Autenticación (`AuthHandler`)
Este manejador verifica si el usuario es válido.

```java
// File: room/handlers/commands/impl/AuthHandler.java
public class AuthHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("Processing authentication for user: " + request.getUser());

        if (authenticate(request)) {
            request.setAuthenticated(true);
            System.out.println("Authentication successful for user: " + request.getUser());
            if (nextHandler != null) {
                nextHandler.handleRequest

(request);
            }
        } else {
            request.incrementFailedAttempts();
            System.out.println("Authentication failed for user: " + request.getUser());
        }
    }

    private boolean authenticate(Request request) {
        return "admin".equals(request.getUser());
    }
}
```

##### Manejador de Permisos (`PermissionHandler`)
Verifica si el usuario tiene los permisos necesarios para la acción solicitada.

```java
// File: room/handlers/commands/impl/PermissionHandler.java
public class PermissionHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("Checking permissions for role: " + request.getRole());

        if (checkPermissions(request)) {
            System.out.println("Permission granted for role: " + request.getRole());
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        } else {
            System.out.println("Permission denied for role: " + request.getRole());
        }
    }

    private boolean checkPermissions(Request request) {
        return "ADMIN".equals(request.getRole());
    }
}
```

##### Manejador de Sanitización de Datos (`DataSanitizationHandler`)
Limpia los datos proporcionados en la solicitud.

```java
// File: room/handlers/commands/impl/DataSanitizationHandler.java
public class DataSanitizationHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("Sanitizing data for request: " + request.getData());

        request.setData(sanitize(request.getData()));

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    private String sanitize(String data) {
        return data.replaceAll("[^a-zA-Z0-9]", "");
    }
}
```

##### Manejador de Limitación de Tasa (`RateLimitHandler`)
Controla el número de intentos fallidos y limita el número de solicitudes permitidas.

```java
// File: room/handlers/commands/impl/RateLimitHandler.java
public class RateLimitHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
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
```

##### Manejador de Caché (`CachingHandler`)
Verifica si los resultados de la solicitud ya están en caché.

```java
// File: room/handlers/commands/impl/CachingHandler.java
public class CachingHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
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

    private boolean isCached(Request request) {
        return false;
    }
}
```

### 4. Controlador REST

Implementamos un controlador REST en **Spring Boot** que recibe la solicitud y la pasa a través de la cadena de responsabilidad.

```java
// File: room/api/rest/RequestController.java
package room.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import room.handlers.commands.Request;
import room.handlers.commands.RequestHandler;
import room.handlers.commands.impl.*;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    private final RequestHandler chain;

    public RequestController() {
        RequestHandler authHandler = new AuthHandler();
        RequestHandler permissionHandler = new PermissionHandler();
        RequestHandler dataSanitizationHandler = new DataSanitizationHandler();
        RequestHandler rateLimitHandler = new RateLimitHandler();
        RequestHandler cachingHandler = new CachingHandler();

        authHandler.setNextHandler(permissionHandler);
        permissionHandler.setNextHandler(dataSanitizationHandler);
        dataSanitizationHandler.setNextHandler(rateLimitHandler);
        rateLimitHandler.setNextHandler(cachingHandler);

        this.chain = authHandler;  // Primer manejador en la cadena
    }

    @PostMapping
    public ResponseEntity<String> handleRequest(@RequestBody RequestEntity requestEntity) {
        Request request = new Request(requestEntity.getUser(), requestEntity.getRole(), requestEntity.getData());

        chain.handleRequest(request);

        if (!request.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
        }

        if (request.isCached()) {
            return ResponseEntity.status(HttpStatus.OK).body("Cached response: " + request.getData());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Request processed: " + request.getData());
    }
}
```

---

### 5. Prueba del Sistema con Postman

- **Método**: `POST`
- **URL**: `http://localhost:8080/api/request`
- **Headers**: `Content-Type: application/json`
- **Body**:
  ```json
  {
    "user": "admin",
    "role": "ADMIN",
    "data": "example data with symbols !@#"
  }
  ```

### 6. Respuesta Esperada

#### Caso 1: Autenticación exitosa
```json
{
    "status": 200,
    "message": "Request processed: exampledatawithsymbols"
}
```

#### Caso 2: Falla en la autenticación
```json
{
    "status": 401,
    "message": "Authentication failed."
}
```

---

## Conclusión

Este ejemplo muestra cómo se puede implementar el **Patrón Chain of Responsibility** para mejorar la modularidad, el mantenimiento y la extensibilidad de un sistema de validación. Cada manejador tiene una única responsabilidad, lo que hace que el código sea más limpio, fácil de mantener y adaptable a cambios futuros en la API de **ProjectCloud**.