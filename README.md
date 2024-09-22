# Patrones de Diseño: Chain of Responsibility

## Índice
1. [¿Qué es Chain of Responsibility?](#qué-es-chain-of-responsibility)
2. [Características del Patrón Chain of Responsibility](#características-del-patrón-chain-of-responsibility)
3. [Problema Solucionado por el Patrón Chain of Responsibility](#problema-solucionado-por-el-patrón-chain-of-responsibility)
4. [Descripción de la Solución](#descripción-de-la-solución)
5. [Ejemplo Cotidiano](#ejemplo-cotidiano)

## ¿Qué es Chain of Responsibility?

El patrón de diseño *Chain of Responsibility* es un patrón de comportamiento que facilita el enrutamiento de solicitudes a través de una serie de manejadores. Cada manejador en la cadena tiene la opción de procesar la solicitud o transferirla al siguiente manejador en la secuencia.

Este patrón se destaca por su flexibilidad al manejar situaciones en las que no está claro qué objeto debe procesar una solicitud específica. Ofrece una solución eficaz en escenarios donde la herencia no es adecuada, utilizando una estructura en cadena en la que una serie de objetos intentan atender la solicitud.

El patrón Chain of Responsibility es un patrón de diseño de comportamiento. Los patrones de comportamiento se centran en cómo los objetos interactúan y colaboran entre sí para lograr un comportamiento específico.

## Características del Patrón Chain of Responsibility

- **Desacoplamiento**: Permite desacoplar el remitente de la solicitud del receptor, ya que el remitente no necesita saber qué objeto en la cadena maneja la solicitud.

- **Encadenamiento**: Organiza una serie de objetos en una cadena, donde cada objeto tiene la opción de procesar la solicitud o pasarla al siguiente objeto en la cadena.

- **Flexibilidad**: Ofrece flexibilidad para agregar o modificar manejadores en la cadena sin alterar el código cliente. Puedes añadir nuevos manejadores o cambiar el orden de la cadena sin afectar a otros componentes.

- **Responsabilidad**: Cada objeto en la cadena tiene una responsabilidad específica y decide si puede manejar la solicitud o debe delegarla al siguiente objeto.

## Problema Solucionado por el Patrón Chain of Responsibility

Imagina que estás trabajando en un sistema de pedidos online. Quieres restringir el acceso al sistema de forma que únicamente los usuarios autenticados puedan generar pedidos. Además, los usuarios que tengan permisos administrativos deben tener pleno acceso a todos los pedidos.

Tras planificar un poco, te das cuenta de que estas comprobaciones deben realizarse secuencialmente. La aplicación puede intentar autenticar a un usuario en el sistema cuando reciba una solicitud que contenga las credenciales del usuario. Sin embargo, si esas credenciales no son correctas y la autenticación falla, no hay razón para proceder con otras comprobaciones.

La solicitud debe pasar una serie de comprobaciones antes de que el propio sistema de pedidos pueda gestionarla.

Durante los meses siguientes, implementas varias de esas comprobaciones secuenciales:

1. **Validación de Datos**: Uno de tus colegas sugiere que no es seguro pasar datos sin procesar directamente al sistema de pedidos, por lo que añades un paso adicional para sanear los datos de una solicitud.

2. **Seguridad**: Más tarde, se detecta una vulnerabilidad al desciframiento de contraseñas por fuerza bruta, y añades una comprobación para filtrar solicitudes fallidas repetidas provenientes de la misma dirección IP.

3. **Optimización**: Otra sugerencia es acelerar el sistema devolviendo resultados en caché para solicitudes repetidas con los mismos datos. Así que añades una comprobación que permite a la solicitud pasar por el sistema solo cuando no hay una respuesta adecuada en caché.

**Cuanto más crece el código, más se complica.** Cada adición hace que el código de las comprobaciones se vuelva más desordenado y difícil de mantener. Además, un cambio en una comprobación puede afectar a las demás, y la reutilización de estas comprobaciones en otros componentes del sistema requiere duplicar parte del código.

El sistema se vuelve cada vez más complejo y costoso de mantener. Finalmente, decides refactorizar el código para simplificar y mejorar su gestión.

## Descripción de la Solución

> "El patrón Chain of Responsibility permite pasar una solicitud a lo largo de una cadena de objetos hasta que uno de ellos la maneje. Cada objeto en la cadena tiene la posibilidad de procesar la solicitud o pasarla al siguiente objeto en la cadena. Esto desacopla el remitente de la solicitud de su receptor y permite agregar nuevos manejadores sin modificar el código existente."
> — Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides, *Design Patterns: Elements of Reusable Object-Oriented Software*

## Ejemplo Cotidiano

### Proceso de Atención en un Centro de Atención al Cliente

Imagina un centro de atención al cliente de una empresa que recibe solicitudes de los clientes para distintos tipos de problemas, como consultas generales, problemas técnicos y solicitudes de reembolsos. Cada tipo de solicitud requiere atención especializada y puede ser manejada por diferentes departamentos.

#### Aplicación del Patrón Chain of Responsibility

1. **Recepcionista (Manejador Inicial)**: El recepcionista recibe todas las solicitudes y decide si puede manejar la solicitud o debe pasarla a otro departamento. Este es el primer punto de contacto y actúa como el primer manejador en la cadena.

2. **Departamento de Consultas Generales**: Si la solicitud es una consulta general, el recepcionista la pasa a este departamento. Los empleados aquí están capacitados para responder a preguntas generales.

3. **Departamento Técnico**: Para problemas técnicos, la solicitud se dirige a este departamento especializado. Los técnicos tienen el conocimiento necesario para resolver problemas específicos del producto o servicio.

4. **Departamento de Reembolsos**: Las solicitudes relacionadas con reembolsos o devoluciones se envían a este departamento, que maneja todas las cuestiones financieras y de política de devoluciones.

5. **Escalamiento**: En casos donde un departamento no puede resolver una solicitud, esta puede ser escalada a un nivel superior, como un supervisor o un especialista en un área específica.

#### Solución Ofrecida por el Patrón Chain of Responsibility

- **Desacoplamiento**: El patrón permite desacoplar el manejo de solicitudes del proceso de recepción. El recepcionista no necesita conocer todos los detalles sobre cada tipo de solicitud; simplemente dirige la solicitud al departamento adecuado.

- **Flexibilidad**: Puedes añadir nuevos departamentos o cambiar la cadena de manejadores sin alterar el código de los manejadores existentes. Si surge un nuevo tipo de solicitud, solo necesitas añadir un nuevo manejador a la cadena.

- **Escalabilidad**: La estructura en cadena facilita la ampliación del sistema. A medida que la empresa crece o cambian las necesidades, puedes reorganizar o expandir la cadena de manejadores de manera sencilla.

- **Mantenimiento**: El mantenimiento del sistema se simplifica ya que cada departamento maneja sus propios tipos de solicitudes. Las modificaciones en un departamento no afectan a los otros, y el sistema en su conjunto se mantiene más organizado y manejable.
