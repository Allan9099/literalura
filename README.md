# LiterAlura

**LiterAlura** es una aplicación de consola en Java diseñada para gestionar datos de libros y autores utilizando la API de **Gutendex**. Los datos obtenidos se almacenan en una base de datos PostgreSQL configurada en PGadmin4.

## Características principales

Al iniciar el programa, se despliega un menú con las siguientes opciones:

```
---(Ingrese el número de su opción deseada)---

1 - Buscar libro por título
2 - Lista de libros registrados
3 - Lista de autores registrados
4 - Buscar autores vivos por año
5 - Buscar por idioma

0 - Salir
----------------------------------------------
```

### Funcionalidades

1. **Buscar libro por título**
   - Esta opción permite buscar un libro en **Gutendex** por su título.
   - Los datos de cada libro encontrado se guardan en la base de datos, donde se almacenan los siguientes datos:
     - **Libro**: título, autor, idioma y número de descargas.
     - **Autor**: nombre, año de nacimiento, año de muerte.

2. **Lista de libros registrados**
   - Muestra todos los libros guardados en la base de datos.

3. **Lista de autores registrados**
   - Lista todos los autores almacenados en la base de datos.

4. **Buscar autores vivos por año**
   - Muestra los autores que estaban vivos en un año específico ingresado por el usuario.

5. **Buscar por idioma**
   - Permite buscar libros almacenados por su idioma.
   - El usuario puede ingresar un código de idioma (por ejemplo, **ES** para español, **EN** para inglés, etc.) o la palabra **"Idiomas"** para listar todos los idiomas únicos disponibles de los libros guardados de su base de datos.
   
   - Ejemplo del menú de búsqueda por idioma:
     ```
     --------(Nota: estas son sugerencias.)--------
     (Ingresa "Idiomas" para ver los de tus libros)

     ES - Español
     EN - Inglés
     JA - Japonés
     PT - Portugués

     ----(Ingresa el código del idioma deseado)----
     ```
   - Si el usuario ingresa **"Idiomas"**, se muestra una lista de los idiomas únicos en los libros guardados, como:
     ```
     ------------(Idiomas disponibles)-------------

     - LA
     - EN
     - ES

     ----------------------------------------------
     ```
   - Luego de esta lista volverá a aparecer el menú para buscar libros por idioma.

## Configuración

Para que **LiterAlura** funcione correctamente, necesitas configurar las siguientes variables de entorno en tu sistema:

- **DB_HOST**: host de la base de datos.
- **DB_NAME**: nombre de la base de datos.
- **DB_USER**: usuario de la base de datos.
- **DB_PASSWORD**: contraseña de la base de datos.

El archivo `application.properties` se debe configurar como sigue para utilizar estas variables:

```
spring.application.name=literalura
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.HSQLDialect

spring.jpa.hibernate.ddl-auto=update
spring.jpa.format-sql = true
```
