## Trabajo de Investigación

### Indicaciones

- A realizarse en grupos de 3.
- Se deberá elegir un tema entre los listados y realizar un trabajo de investigación sobre los conceptos que se les solicitan.
- Los entregables deben ser:
    - Documento formal de los resultados de investigación.
    - Documento con estándares, recomendaciones o reglas para ser aplicado en el proyecto.
    - Exposición de no más de 20 minutos donde expongan los hallazgos.

#### Guía de Despliegue (Grupo _)

- Estrategias de implementación de software (despliegue continuo, despliegue gradual, etc.).
- Herramientas y plataformas de automatización de despliegue (CI/CD).
- Consideraciones de seguridad en el despliegue de aplicaciones.
- Monitorización y registro de aplicaciones en producción.
- Estrategias de reversión y manejo de fallos en el despliegue.

### Entregables

- Documento formal de los resultados de investigación(33%).
    - Debe contener una introducción, desarrollo y conclusiones.
    - Debe contener referencias bibliográficas.
    - Debe contener ejemplos prácticos.
- Documento con estándares, recomendaciones o reglas para ser aplicado en el proyecto(33%).
    - Debe ser un documento técnico.
    - Debe contener instrucciónes claras y concisas.
- Exposición de no más de 20 minutos donde expongan los hallazgos(34%).
    - Debe ser una presentación formal.
    - Debe contener ejemplos prácticos.
    - Debe contener una demostración práctica.
    - Debe contener una sección de preguntas y respuestas.

Todos los documentos se deberán entregar en un Pull Request donde se deben crear un subdirectorio en el directorio de investigación con el nombre del proyecto.

### Fecha de entrega

- La fecha de entrega será para el día 1 de noviembre del 2021 a las 5:00 p.m.
- Se debe registrar la entrega en el siguiente enlace: [Registro de Entregas](https://forms.gle/rtrNtDY3JSajjvLZ7)

  # Desarrollo
  Consideraciones de Seguridad en el Despliegue de Aplicaciones
La seguridad en el despliegue de software no solo abarca la protección del código y los datos durante el proceso de desarrollo, sino también la seguridad de las infraestructuras de producción. A continuación, se presentan cinco consideraciones clave:

Integración de seguridad en el ciclo de vida del desarrollo de software (SDLC) Implementar la seguridad desde el inicio del ciclo de vida del software es esencial para identificar y mitigar posibles vulnerabilidades antes de que el software se despliegue. Esto incluye la revisión de código, análisis de vulnerabilidades y pruebas de seguridad en cada etapa del pipeline de CI/CD​
IBM - UNITED STATES
. Las herramientas automatizadas, como el escaneo de código estático y dinámico, son fundamentales para este propósito.

Validación y autenticación de usuarios Uno de los principales riesgos en el despliegue de aplicaciones son los accesos no autorizados. Implementar fuertes mecanismos de autenticación y autorización, como el uso de multifactor authentication (MFA), reduce significativamente el riesgo de ataques de suplantación de identidad y acceso no autorizado​
IBM - UNITED STATES
. Asimismo, la encriptación de los datos de autenticación garantiza que la información sensible no se vea comprometida durante las fases de despliegue.

Pruebas de seguridad previas al despliegue Las pruebas de seguridad incluyen tanto pruebas manuales como automatizadas que simulan ataques comunes para detectar vulnerabilidades, tales como inyecciones SQL, Cross-Site Scripting (XSS), y ataques DDoS​
IBM - UNITED STATES
​
INVGATE ITSM BLOG
. Al probar la aplicación en entornos similares a los de producción, los equipos pueden identificar brechas de seguridad antes del despliegue.

Manejo de dependencias y librerías externas Las aplicaciones modernas suelen depender de librerías y frameworks externos que, si no se gestionan adecuadamente, pueden introducir vulnerabilidades en el sistema. Mantener actualizadas las dependencias y realizar análisis regulares para detectar vulnerabilidades conocidas es crucial para asegurar el entorno de despliegue​
IBM - UNITED STATES
​
NINJAONE
.

Protección de datos sensibles durante el despliegue Durante el proceso de despliegue, es fundamental asegurar que los datos sensibles, como información de clientes, se mantengan protegidos a través de técnicas de cifrado y políticas de acceso restrictivas. Las configuraciones erróneas de los sistemas de bases de datos o el acceso inseguro a APIs son riesgos comunes que deben abordarse​
NINJAONE
.

Estrategias de Reversión y Manejo de Fallos en el Despliegue
El despliegue de software nunca está exento de riesgos, por lo que contar con estrategias de reversión y manejo de fallos efectivas es crucial para minimizar el impacto de errores y mantener la continuidad del servicio. A continuación, se describen cinco estrategias de reversión ampliamente utilizadas:

Blue-Green Deployment Esta estrategia implica mantener dos entornos de producción: uno en uso (Green) y uno inactivo pero listo para recibir la nueva versión (Blue). Cuando la versión Blue se prueba y se considera estable, el tráfico se redirige a ella. Si se detecta un fallo, es sencillo revertir al entorno Green con un mínimo tiempo de inactividad​
SENTRIO
.

Ejemplo Práctico: En una implementación Blue-Green para una plataforma e-commerce, la versión Blue contenía nuevas características. Tras desplegarla y redirigir el tráfico, se detectaron errores en la integración con el sistema de pago. Se revirtió rápidamente al entorno Green, garantizando que los clientes no experimentaran fallos en el servicio.

Canary Release En lugar de desplegar la nueva versión a todos los usuarios a la vez, el Canary Release permite que solo una pequeña porción de los usuarios acceda inicialmente a la nueva versión. Si el rendimiento es aceptable y no se detectan problemas, se incrementa gradualmente el porcentaje de usuarios con acceso​
SENTRIO
.

Ejemplo Práctico: Un servicio de streaming implementó una Canary Release para una nueva función de recomendación. Inicialmente, solo el 5% de los usuarios tuvo acceso a la función. Al cabo de una semana de monitoreo, sin incidentes reportados, el despliegue se amplió al 100% de los usuarios.

Rolling Deployment Similar al Canary Release, esta estrategia despliega la nueva versión en pequeños lotes a lo largo del tiempo, reemplazando gradualmente la versión anterior en todos los servidores. Esto es útil para minimizar la sobrecarga del sistema y reducir los riesgos durante el despliegue​
INVGATE ITSM BLOG
.

Ejemplo Práctico: Un proveedor de SaaS utilizó un Rolling Deployment para actualizar su sistema de gestión de inventario. Desplegaron la actualización en un 10% de sus servidores en intervalos de 12 horas, permitiendo una fácil reversión en caso de problemas.

Rollback Automatizado En un despliegue fallido, la capacidad de revertir rápidamente a una versión anterior es esencial. El Rollback automatizado se apoya en sistemas de control de versiones y pipelines de CI/CD que permiten restaurar versiones previas sin intervención manual​
NINJAONE
.

Ejemplo Práctico: Durante un despliegue continuo en una aplicación de banca móvil, un error crítico en la autenticación de usuarios forzó un rollback automático a la versión estable anterior en cuestión de minutos, evitando que los clientes quedaran bloqueados de sus cuentas.

Despliegue Oscuro (Dark Launching) En esta estrategia, las nuevas características se implementan en producción pero no son accesibles a los usuarios finales. Esto permite probar el comportamiento de la nueva versión en condiciones de carga real sin afectar la experiencia del usuario​
SENTRIO
.

Ejemplo Práctico: Un equipo de desarrollo activó una nueva funcionalidad de búsqueda avanzada en una aplicación web como un Dark Launch para monitorear el impacto en el rendimiento del servidor antes de permitir que los usuarios interactuaran con ella.
