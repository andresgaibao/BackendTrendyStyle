
# TrendyStyle

**Description:**

TrendyStyle es un ecommerce de zapatos que busca traer el mejor estilo al mercado, nuestro proposito es brindar 
productos con la mejor calidad del mercado a nuestros clientes, brindar la mejor calidad de servicio posible para 
generar una mejor comodidad y confiabilidad a la hora de comprar nuestros productos.

**Instrucciones de descarga:**

1. Abre Git Bash en tu sistema. 
- Utiliza el comando cd para navegar al directorio donde deseas clonar el repositorio. Por ejemplo:

  - cd /ruta/del/directorio.

- Utiliza el comando git clone seguido de la URL de tu repositorio. Por ejemplo:

  - git clone https://github.com/tu-usuario/tu-repositorio.git

- Esto creará un directorio con el nombre del repositorio en tu ubicación actual y descargará el contenido del
repositorio.

2. Abre el Proyecto en IntelliJ IDEA:

- Abre IntelliJ IDEA.
- Selecciona "Open" desde el menú principal o "File > Open" si ya tienes un proyecto abierto.
- Navega al directorio donde clonaste el repositorio y selecciona el directorio del proyecto.

3. Configura el proyecto:

- IntelliJ debería reconocer automáticamente que es un proyecto de Maven o Gradle. Si no lo hace, abre el archivo pom.xml o build.gradle según sea el caso.

4. Configura el SDK de java:

- Ve a File > Project Structure > Project y asegúrate de que el SDK de Java esté configurado correctamente.

- Ejecutar el Proyecto.

- Correr el proyecto.

**Estructura del Proyecto:**

La estructura del proyecto esta constituida de la sigueinte forma.

- Controllers:  el paquete controllers  almacena clases que manejan las solicitudes HTTP y actúan como intermediarios entre el cliente, la lógica de negocio y los modelos de datos. Estas clases desempeñan un papel crucial en la arquitectura MVC (Modelo-Vista-Controlador) de la aplicación.
- Configuration: esta dedicado a configurar aspectos cruciales de tu aplicación Spring Boot tales como:

  - ApplicationConfig se encarga de configuraciones y beans generales.
  - CorsConfig gestiona la configuración de CORS para manejar solicitudes desde orígenes diferentes.
  - JwtAuthenticationFilter se encarga de la autenticación basada en tokens JWT, permitiendo una autenticación segura y autorización en tu aplicación.
  
- Entity:  el paquete entity  se utiliza para almacenar clases de entidad que representan objetos de dominio persistentes en la base de datos. Estas clases juegan un papel fundamental en el modelado y persistencia de datos en la aplicación.
- Enums: el paquete enums  se utiliza para almacenar enumeraciones que representan conjuntos fijos de constantes. Estas enumeraciones son utilizadas en diversas partes del código para mejorar la legibilidad, facilitar la validación de datos y representar de manera eficiente conjuntos discretos de valores en la aplicación.
- Repository:  el paquete repository  almacena interfaces que definen operaciones de acceso a datos para las entidades de la aplicación. Estas interfaces aprovechan la funcionalidad proporcionada por Spring Data JPA para simplificar y automatizar las tareas de acceso a datos en la aplicación.
- Responses:  el paquete response suele utilizarse para organizar clases que representan objetos de respuesta (Response DTO) que encapsulan datos que se enviarán desde el servidor al cliente. Estas clases son esenciales para personalizar la presentación de datos, estructurar las respuestas y proporcionar información detallada sobre el estado de las operaciones al cliente.
- Spring Security es un marco de seguridad integral para aplicaciones Java, y es comúnmente utilizado en proyectos Spring Boot. Se encarga de aspectos como la autenticación, la autorización, la protección contra ataques CSRF, entre otros.
- Security:  el paquete services  contiene clases que implementan la lógica de negocio de la aplicación. Estas clases son responsables de coordinar el acceso a datos, aplicar reglas de negocio y proporcionar servicios a otras capas de la aplicación.

**Uso**

**Creditos**

-Programadores:
    - Esneider Mejía Quintero.
    - Andres Gaibao Cabrera.

