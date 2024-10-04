package ucr.ac.C12599.room;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.handlers.commands.impl.*;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

@SpringBootApplication
public class PatronDiseñoC12599 implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PatronDiseñoC12599.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Aquí ejecutamos la lógica de pruebas al iniciar la aplicación Spring Boot
		System.out.println("Iniciando pruebas de la cadena de responsabilidad...");

		// Configuramos la cadena de responsabilidad
		RequestHandler chain = setupChain();

		// Simulamos tres casos diferentes para probar la cadena
		System.out.println("=== Caso 1: Usuario 'admin' con rol 'USER' ===");
		RequestEntity request1 = new RequestEntity("admin", "USER", "Sensitive data");
		chain.handleRequest(request1);
		printResult(request1);

		System.out.println("\n=== Caso 2: Usuario 'admin' con rol 'ADMIN' ===");
		RequestEntity request2 = new RequestEntity("admin", "ADMIN", "Sensitive data");
		chain.handleRequest(request2);
		printResult(request2);

		System.out.println("\n=== Caso 3: Usuario 'user1' con rol 'USER' (fallo en autenticación) ===");
		RequestEntity request3 = new RequestEntity("user1", "USER", "Sensitive data");
		chain.handleRequest(request3);
		printResult(request3);
	}

	private RequestHandler setupChain() {
		// Configuramos la cadena de responsabilidad
		RequestHandler authHandler = new AuthHandler();
		RequestHandler permissionHandler = new PermissionHandler();
		RequestHandler dataSanitizationHandler = new DataSanitizationHandler();
		RequestHandler rateLimitHandler = new RateLimitHandler();
		RequestHandler loggingHandler = new LoggingHandler();  // Nuevo handler para logging
		RequestHandler encryptionHandler = new EncryptionHandler();  // Nuevo handler para encriptación
		RequestHandler backupHandler = new BackupHandler();  // Nuevo handler para backup

		// Definimos el orden de la cadena
		authHandler.setNextHandler(permissionHandler);
		permissionHandler.setNextHandler(dataSanitizationHandler);
		dataSanitizationHandler.setNextHandler(rateLimitHandler);
		rateLimitHandler.setNextHandler(loggingHandler);
		loggingHandler.setNextHandler(encryptionHandler);
		encryptionHandler.setNextHandler(backupHandler);

		return authHandler;  // Retornamos el primer manejador en la cadena
	}

	private void printResult(RequestEntity request) {
		// Imprimir el resultado de la solicitud
		if (!request.isAuthenticated()) {
			System.out.println("Resultado: Autenticación fallida.");
		} else if (request.isCached()) {
			System.out.println("Resultado: Respuesta en caché.");
		} else {
			System.out.println("Resultado: Solicitud procesada exitosamente.");
		}
	}
}
