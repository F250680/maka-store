# 🛒 Maka Store

Sistema de gestión de productos y categorías desarrollado en **Java con Spring Boot**. Permite realizar operaciones CRUD (crear, leer, actualizar y eliminar) sobre productos almacenados en una base de datos MySQL.

---

## 🚀 Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA**
- **MySQL**
- **Maven**
- **Postman** (para pruebas)
- **Git & GitHub**

---

## 📁 Estructura del proyecto

src
├── main
│ ├── java
│ │ └── com.maka.makastore
│ │ ├── controller
│ │ ├── model
│ │ └── repository
│ └── resources
│ └── application.properties

yaml
Copiar
Editar

---

## ⚙️ Configuración

Edita tu archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/maka_store
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Asegúrate de tener creada la base de datos maka_store en tu servidor local de MySQL.

📡 Endpoints disponibles
Método	Endpoint	Descripción
GET	/api/productos	Listar todos los productos
GET	/api/productos/{id}	Obtener un producto por ID
POST	/api/productos	Crear un nuevo producto
PUT	/api/productos/{id}	Actualizar un producto
PATCH	/api/productos/{id}	Actualización parcial
DELETE	/api/productos/{id}	Eliminar un producto

🔸 Puedes probar estos endpoints desde Postman o cualquier cliente REST.

🧪 Pruebas con Postman
Se han realizado pruebas exitosas de todos los endpoints, incluyendo:

Registro de producto

Consulta individual y masiva

Actualización completa y parcial

Eliminación de productos

🔄 Próximas mejoras
 Agregar controladores para categorías

 Validaciones con @Valid

 Manejo de errores personalizado

 Documentación con Swagger

 Despliegue a Heroku o Render

🤝 Autor
Proyecto realizado por Franco Fernando Ojeda Insuasty como parte de su proceso de formación y práctica profesional.

📌 GitHub: @F250680

📄 Licencia
Este proyecto está bajo la licencia MIT. Puedes usarlo, modificarlo y compartirlo con libertad.

⭐ ¿Te gustó este proyecto?
Dale una ⭐ en GitHub y sígueme para más proyectos y avances 🚀

yaml
Copiar
Editar

---

### ✅ ¿Cómo agregar este `README.md` al proyecto?

1. Abre tu proyecto en **IntelliJ** o cualquier editor.
2. Reemplaza el contenido del archivo `README.md` con el texto anterior.
3. Guarda el archivo.
4. Luego, en consola:

```bash
git add README.md
git commit -m "Mejora del README con descripción del proyecto"
git push origin master
