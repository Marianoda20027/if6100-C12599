# Chain of Responsability
## Índice

1. [¿Qué es Chain of Responsibility?](#qué-es-chain-of-responsibility)
2. [Características del Patrón Chain of Responsibility](#características-del-patrón-chain-of-responsibility)
3. [Problema Solucionado por el Patrón Chain of Responsibility](#problema-solucionado-por-el-patrón-chain-of-responsibility)
4. [Descripción de la Solución](#descripción-de-la-solución)
5. [Caso de Estudio: ProjectCloud](#caso-de-estudio-projectcloud)
6. [Implementación del Patrón en ProjectCloud](#implementación-del-patrón-en-projectcloud)

## ¿Qué es Chain of Responsibility?

El patrón de diseño **Chain of Responsibility** es un patrón de comportamiento que permite pasar una solicitud a través de una cadena de manejadores. Cada manejador en la cadena tiene la opción de procesar la solicitud o transferirla al siguiente manejador en la secuencia.

Este patrón es útil en situaciones donde es difícil determinar qué objeto debe manejar una solicitud específica, ofreciendo una estructura flexible en la que varios objetos pueden participar en el procesamiento.

## Características del Patrón Chain of Responsibility

- **Desacoplamiento**: Permite separar el remitente de la solicitud de su receptor.
- **Encadenamiento**: Los manejadores se organizan en una cadena en la que cada uno puede decidir si procesa la solicitud o la pasa al siguiente.
- **Flexibilidad**: Permite agregar nuevos manejadores o cambiar el orden de la cadena sin modificar el código cliente.
- **Responsabilidad**: Cada manejador tiene una responsabilidad específica y puede delegar la solicitud si no la puede manejar.

## Problema Solucionado por el Patrón Chain of Responsibility

Cuando trabajas en sistemas complejos, a menudo necesitas procesar una solicitud a través de una serie de verificaciones. En un sistema monolítico, todas estas verificaciones pueden estar acopladas, lo que genera código difícil de mantener.

Por ejemplo, en una plataforma de pedidos en línea, las solicitudes deben pasar por una serie de verificaciones (autenticación, validación de datos, permisos, etc.). Implementar todo esto en una única función podría hacer que el código se vuelva inmanejable.

### Solución

El patrón **Chain of Responsibility** desacopla las verificaciones, delegando cada validación a un manejador específico. Esto permite una mayor flexibilidad y facilita el mantenimiento.

## Descripción de la Solución

El **Patrón Chain of Responsibility** permite pasar una solicitud por una cadena de objetos hasta que uno de ellos la maneje. Cada objeto puede procesar la solicitud o delegarla al siguiente manejador en la cadena.

Esto permite desacoplar el remitente de la solicitud de los receptores y facilita la adición de nuevos manejadores sin modificar la estructura existente.

## Caso de Estudio: ProjectCloud

**ProjectCloud** es una plataforma de gestión de proyectos que maneja varias capas de validación antes de permitir que los usuarios realicen ciertas acciones. Cada solicitud debe pasar por una serie de validaciones como:

1. **Autenticación**: Solo los usuarios autenticados pueden acceder a los recursos.
2. **Validación de Permisos**: Algunos recursos están restringidos a usuarios con roles específicos (por ejemplo, administradores).
3. **Sanitización de Datos**: Los datos, como nombres de proyectos, deben estar libres de caracteres no permitidos.
4. **Limitación de Tasa (Rate Limiting)**: Se debe limitar el número de intentos fallidos permitidos por usuario en un período de tiempo.

---

## Implementación del Patrón en ProjectCloud

Vamos a implementar este escenario utilizando el patrón **Chain of Responsibility** y probaremos el sistema directamente en el método `main`.

### 1. Interfaz `RequestHandler`

Esta interfaz define la estructura de los manejadores en la cadena.

```java
package room.handlers.commands;

public interface RequestHandler {
    void setNextHandler(RequestHandler nextHandler);
    void handleRequest(Request request);
}
```

### 2. Clase `Request`

Esta clase representa la solicitud del usuario que pasará por los diferentes manejadores.

```java
package room.handlers.commands;

public class Request {
    private String user;
    private String role;
    private String data;
    private boolean isAuthenticated;
    private boolean isAuthorized;
    private int failedAttempts;

    public Request(String user, String role, String data) {
        this.user = user;
        this.role = role;
        this.data = data;
        this.isAuthenticated = false;
        this.isAuthorized = false;
        this.failedAttempts = 0;
    }

    // Getters y Setters
    public String getUser() { return user; }
    public String getRole() { return role; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public boolean isAuthenticated() { return isAuthenticated; }
    public void setAuthenticated(boolean authenticated) { this.isAuthenticated = authenticated; }
    public boolean isAuthorized() { return isAuthorized; }
    public void setAuthorized(boolean authorized) { this.isAuthorized = authorized; }
    public int getFailedAttempts() { return failedAttempts; }
    public void incrementFailedAttempts() { this.failedAttempts++; }
}
```

### 3. Manejadores en la Cadena

#### Manejador de Autenticación (`AuthHandler`)

Verifica si el usuario está autenticado (solo los usuarios con nombre `"admin"` se pueden autenticar).

```java
package room.handlers.commands.impl;

import room.handlers.commands.Request;
import room.handlers.commands.RequestHandler;

public class AuthHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("Verificando autenticación para el usuario: " + request.getUser());

        if (authenticate(request)) {
            request.setAuthenticated(true);
            System.out.println("Autenticación exitosa para el usuario: " + request.getUser());
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        } else {
            request.incrementFailedAttempts();
            System.out.println("Fallo en la autenticación para el usuario: " + request.getUser());
        }
    }

    private boolean authenticate(Request request) {
        return "admin".equals(request.getUser());
    }
}
```

#### Manejador de Permisos (`PermissionHandler`)

Verifica si el usuario tiene los permisos correctos basados en su rol.

```java
package room.handlers.commands.impl;

import room.handlers.commands.Request;
import room.handlers.commands.RequestHandler;

public class PermissionHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("Verificando permisos para el rol: " + request.getRole());

        if (checkPermissions(request)) {
            request.setAuthorized(true);
            System.out.println("Permisos concedidos para el rol: " + request.getRole());
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        } else {
            System.out.println("Permisos denegados para el rol: " + request.getRole());
        }
    }

    private boolean checkPermissions(Request request) {
        return "ADMIN".equals(request.getRole());
    }
}
```

#### Manejador de Sanitización de Datos (`DataSanitizationHandler`)

Este manejador limpia los datos proporcionados en la solicitud para eliminar caracteres no permitidos.

```java
package room.handlers.commands.impl;

import room.handlers.commands.Request;
import room.handlers.commands.RequestHandler;

public class DataSanitizationHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("Sanitizando datos para la solicitud: " + request.getData());

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

#### Manejador de Limitación de Tasa (`RateLimitHandler`)

Este manejador controla cuántos intentos fallidos tiene un usuario y si se ha excedido el límite permitido.

```java
package room.handlers.commands.impl;

import room.handlers.commands.Request;
import room.handlers.commands.RequestHandler;

public class RateLimitHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(Request request) {
        System.out.println("Verificando límite de tasa para el usuario: " + request.getUser());

        if (request.getFailedAttempts() > 3) {
            System.out.println("Se ha excedido el límite de intentos fallidos para el usuario: " + request.getUser());
            return;
        }

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}
``

`

### 4. Clase Principal con el Método `main`

En la clase principal, orquestamos el flujo de las solicitudes en ProjectCloud.

```java
package room;

import room.handlers.commands.Request;
import room.handlers.commands.RequestHandler;
import room.handlers.commands.impl.*;

public class ProjectCloudApp {
    public static void main(String[] args) {
        System.out.println("Iniciando el procesamiento de solicitudes en ProjectCloud...");

        // Configuración de la cadena de responsabilidad
        RequestHandler authHandler = new AuthHandler();
        RequestHandler permissionHandler = new PermissionHandler();
        RequestHandler dataSanitizationHandler = new DataSanitizationHandler();
        RequestHandler rateLimitHandler = new RateLimitHandler();

        // Encadenar los manejadores
        authHandler.setNextHandler(permissionHandler);
        permissionHandler.setNextHandler(dataSanitizationHandler);
        dataSanitizationHandler.setNextHandler(rateLimitHandler);

        // Caso 1: Usuario autenticado pero sin permisos (rol "USER")
        System.out.println("\n=== Caso 1: Usuario 'admin' con rol 'USER' ===");
        Request request1 = new Request("admin", "USER", "Datos importantes!@#");
        authHandler.handleRequest(request1);
        printResult(request1);

        // Caso 2: Usuario autenticado con permisos (rol "ADMIN")
        System.out.println("\n=== Caso 2: Usuario 'admin' con rol 'ADMIN' ===");
        Request request2 = new Request("admin", "ADMIN", "Datos válidos");
        authHandler.handleRequest(request2);
        printResult(request2);

        // Caso 3: Usuario no autenticado
        System.out.println("\n=== Caso 3: Usuario 'user1' con rol 'USER' ===");
        Request request3 = new Request("user1", "USER", "Datos inválidos");
        authHandler.handleRequest(request3);
        printResult(request3);
    }

    private static void printResult(Request request) {
        if (!request.isAuthenticated()) {
            System.out.println("Resultado: Autenticación fallida.");
        } else if (!request.isAuthorized()) {
            System.out.println("Resultado: Permisos denegados.");
        } else {
            System.out.println("Resultado: Solicitud procesada exitosamente.");
        }
    }
}
```

### Ejecución

Al ejecutar el programa, la salida simulada será algo como:

```plaintext
Iniciando el procesamiento de solicitudes en ProjectCloud...

=== Caso 1: Usuario 'admin' con rol 'USER' ===
Verificando autenticación para el usuario: admin
Autenticación exitosa para el usuario: admin
Verificando permisos para el rol: USER
Permisos denegados para el rol: USER
Resultado: Permisos denegados.

=== Caso 2: Usuario 'admin' con rol 'ADMIN' ===
Verificando autenticación para el usuario: admin
Autenticación exitosa para el usuario: admin
Verificando permisos para el rol: ADMIN
Permisos concedidos para el rol: ADMIN
Sanitizando datos para la solicitud: Datos válidos
Verificando límite de tasa para el usuario: admin
Resultado: Solicitud procesada exitosamente.

=== Caso 3: Usuario 'user1' con rol 'USER' ===
Verificando autenticación para el usuario: user1
Fallo en la autenticación para el usuario: user1
Resultado: Autenticación fallida.
```

### Conclusión

Este código simula el flujo de validación usando el patrón **Chain of Responsibility** en el contexto de la plataforma ficticia **ProjectCloud**. La cadena de manejadores valida la autenticación, permisos, sanitiza los datos y limita las solicitudes para proporcionar un sistema flexible y desacoplado.
