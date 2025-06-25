# 🛒 **MAKA STORE**

<div align="center">

☕ **Java 17** | 🌱 **Spring Boot 3** | 🐬 **MySQL 8.0** | 📦 **Maven 3.8**

**Sistema de gestión de productos y categorías desarrollado en Java con Spring Boot**

*Permite realizar operaciones CRUD completas sobre productos almacenados en MySQL*

[🚀 Demo](#-api-endpoints) • [📖 Documentación](#️-configuración-rápida) • [🤝 Contribuir](#-roadmap--próximas-mejoras)

</div>

---

## 🎯 **Descripción del Proyecto**

**Maka Store** es una API REST robusta desarrollada con **Spring Boot** que proporciona un sistema completo de gestión de productos. Diseñada siguiendo las mejores prácticas de desarrollo backend, ofrece operaciones CRUD eficientes y una arquitectura escalable.

### ✨ **Características Principales**

🔹 **API RESTful** - Endpoints bien definidos y documentados  
🔹 **Operaciones CRUD** - Crear, leer, actualizar y eliminar productos  
🔹 **Base de datos MySQL** - Persistencia de datos confiable  
🔹 **Arquitectura MVC** - Separación clara de responsabilidades  
🔹 **JPA/Hibernate** - ORM para manejo eficiente de datos  

---

## 🛠️ **Stack Tecnológico**

<table>
<tr>
<td align="center"><strong>🔥 Backend</strong></td>
<td align="center"><strong>🗄️ Base de Datos</strong></td>
<td align="center"><strong>⚒️ Herramientas</strong></td>
</tr>
<tr>
<td align="center">
  ☕ <strong>Java 17</strong><br>
  🌱 <strong>Spring Boot 3</strong><br>
  📊 <strong>Spring Data JPA</strong>
</td>
<td align="center">
  🐬 <strong>MySQL 8.0</strong><br>
  🧪 <strong>Hibernate ORM</strong>
</td>
<td align="center">
  📦 <strong>Maven 3.8</strong><br>
  🔧 <strong>Postman Testing</strong><br>
  🌿 <strong>Git VCS</strong>
</td>
</tr>
</table>

---

## 📁 **Arquitectura del Proyecto**

```
📦 maka-store/
├── 📂 src/
│   ├── 📂 main/
│   │   ├── 📂 java/com/maka/makastore/
│   │   │   ├── 🎮 controller/     # Endpoints REST
│   │   │   ├── 📊 model/          # Entidades JPA
│   │   │   └── 🗃️  repository/     # Acceso a datos
│   │   └── 📂 resources/
│   │       └── ⚙️ application.properties
├── 📄 pom.xml
└── 📖 README.md
```

---

## ⚙️ **Configuración Rápida**

### 🔧 **Prerrequisitos**
- ☕ Java 17 o superior
- 🗃️ MySQL 8.0+
- 📦 Maven 3.8+
- 💻 IDE (IntelliJ IDEA recomendado)

### 🚀 **Instalación**

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/F250680/maka-store.git
   cd maka-store
   ```

2. **Configurar base de datos**
   ```sql
   CREATE DATABASE maka_store;
   ```

3. **Configurar application.properties**
   ```properties
   # 🗃️ Configuración de Base de Datos
   spring.datasource.url=jdbc:mysql://localhost:3306/maka_store
   spring.datasource.username=root
   spring.datasource.password=tu_contraseña
   
   # 🔧 Configuración JPA/Hibernate
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

🎉 **¡Listo!** La API estará disponible en `http://localhost:8080`

---

## 📡 **API Endpoints**

<div align="center">

### 🌐 **Base URL:** `http://localhost:8080/api`

</div>

| 🔸 Método | 🔗 Endpoint | 📝 Descripción | 📋 Ejemplo |
|-----------|-------------|----------------|-------------|
| 📗 **GET** | `/productos` | Listar todos los productos | `GET /api/productos` |
| 📗 **GET** | `/productos/{id}` | Obtener producto por ID | `GET /api/productos/1` |
| 📘 **POST** | `/productos` | Crear nuevo producto | `POST /api/productos` |
| 📙 **PUT** | `/productos/{id}` | Actualizar producto completo | `PUT /api/productos/1` |
| 📒 **PATCH** | `/productos/{id}` | Actualización parcial | `PATCH /api/productos/1` |
| 📕 **DELETE** | `/productos/{id}` | Eliminar producto | `DELETE /api/productos/1` |

### 📋 **Ejemplo de Payload (JSON)**

```json
{
  "nombre": "Laptop Gaming",
  "descripcion": "Laptop para gaming de alta gama",
  "precio": 1299.99,
  "categoria": "Electrónicos",
  "stock": 15
}
```

---

## 🧪 **Testing & Validación**

<div align="center">

🔧 **Postman** | ✅ **Tested** | 🎯 **All Endpoints Working**

**✅ Todos los endpoints han sido probados exitosamente**

</div>

### 🔍 **Pruebas Realizadas**
- ✅ **CREATE** - Registro de nuevos productos
- ✅ **READ** - Consulta individual y masiva
- ✅ **UPDATE** - Actualización completa y parcial
- ✅ **DELETE** - Eliminación de productos
- ✅ **VALIDATION** - Manejo de errores y casos edge

---

## 🚧 **Roadmap & Próximas Mejoras**

<div align="center">

### 🎯 **En Desarrollo**

</div>

| Estado | Característica | Descripción |
|--------|---------------|-------------|
| 🔄 | **Controlador de Categorías** | CRUD completo para categorías |
| 📋 | **Validaciones @Valid** | Validación robusta de datos |
| 🚨 | **Manejo de Errores** | ResponseEntity personalizado |
| 📚 | **Documentación Swagger** | API docs interactiva |
| 🌐 | **Despliegue Cloud** | Deploy en Heroku/Render |
| 🔐 | **Autenticación JWT** | Sistema de seguridad |
| 📊 | **Paginación** | Consultas optimizadas |

---

## 👨‍💻 **Autor**

<div align="center">

🧑‍💻 **Franco Fernando Ojeda Insuasty**

*Backend Developer | Java Enthusiast*

🌟 **GitHub:** [@F250680](https://github.com/F250680)  
💼 **LinkedIn:** [Conectar](https://linkedin.com/in/tu-linkedin)  
📧 **Email:** tu-email@ejemplo.com

</div>

---

## 📄 **Licencia**

<div align="center">

⚖️ **MIT License**

Este proyecto está bajo la **Licencia MIT**. Puedes usarlo, modificarlo y compartirlo libremente.

[📋 Ver Licencia Completa](LICENSE)

</div>

---

<div align="center">

## ⭐ **¿Te gustó este proyecto?**

**¡Dale una estrella ⭐ y sígueme para más proyectos increíbles!**

🌟 **Star this repo** | 👥 **Follow me** | 🔔 **Watch for updates**

---

### 🚀 **¡Desarrollado con ❤️ y ☕ por Franco!**

</div>
