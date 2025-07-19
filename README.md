# 🛍️ Maka Store - API Backend

¡Bienvenido al backend de **Maka Store**! 🎉

Este proyecto es una API RESTful robusta desarrollada con **Spring Boot**, diseñada para gestionar un sistema de comercio electrónico completo. Es el corazón de tu tienda en línea, proporcionando todos los servicios necesarios para que un frontend pueda interactuar con los datos de manera eficiente y segura.

---

## ✨ Características Principales

### 🔌 API RESTful Completa
- **Endpoints bien definidos** para operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
- **Validación de datos** usando `jakarta.validation`
- **Manejo global de excepciones** centralizado
- **Configuración CORS** para integración con frontends

### 🏪 Gestión de Entidades
| Entidad | Funcionalidades |
|---------|----------------|
| 📦 **Productos** | Crear, listar, obtener por ID, actualizar y eliminar productos |
| 🏷️ **Categorías** | Gestión completa de categorías de productos |
| 👥 **Clientes** | Administración de información de clientes |
| 📋 **Pedidos** | Creación de pedidos con detalles anidados y cálculo automático del total |
| 📝 **Detalles de Pedido** | Gestión de elementos individuales de cada pedido |

### 🛡️ Características Técnicas
- **Capa de Servicio** (Service Layer) para encapsular lógica de negocio
- **Mapeo de DTOs** para desacoplar entidades de la base de datos
- **Fechas automáticas** con `@PrePersist` y `@PreUpdate`
- **Arquitectura de capas** estándar de Spring Boot

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| ☕ **Java** | 17+ | Lenguaje de programación principal |
| 🍃 **Spring Boot** | 3.x | Framework de desarrollo |
| 📦 **Maven** | - | Gestor de dependencias |
| 📊 **Spring Data JPA** | - | Capa de acceso a datos |
| 🧩 **Hibernate** | - | ORM (Object-Relational Mapping) |
| 🐬 **MySQL** | - | Sistema de gestión de base de datos |
| ⚙️ **Lombok** | - | Reducción de código repetitivo |
| ✅ **Jakarta Validation** | - | Validación de datos |
| 📄 **Jackson** | - | Serialización/deserialización JSON |

---

## 📂 Estructura del Proyecto

```
src/main/java/com/maka/makastore/
├── 🔧 config/          # Configuraciones generales (CORS, etc.)
├── 🎮 controlador/     # Endpoints REST (API)
├── 📤 dto/             # Objetos de transferencia de datos
├── ⚠️ excepción/       # Excepciones personalizadas y GlobalExceptionHandler
├── 🗃️ modelo/          # Entidades JPA (mapeo a la base de datos)
├── 💾 repository/      # Interfaces de acceso a datos
└── 🔄 service/         # Lógica de negocio (interfaces e implementaciones)
```

---

## 🚀 Guía de Instalación

### 📋 1. Prerrequisitos

Asegúrate de tener instalado:
- ☕ **Java Development Kit (JDK)** 17 o superior
- 📦 **Apache Maven**
- 🐬 **MySQL Server**
- 📁 **Git**
- 💻 **IntelliJ IDEA** (o tu IDE Java preferido)

### 🗄️ 2. Configuración de la Base de Datos

#### a) Crear la Base de Datos
```sql
CREATE DATABASE IF NOT EXISTS makastore_db;
USE makastore_db;
```

#### b) Script de Inicialización
> ⚠️ **ADVERTENCIA CRÍTICA**: Este script eliminará PERMANENTEMENTE todos los datos existentes.

<details>
<summary>📜 Ver Script SQL Completo</summary>

```sql
-- Deshabilitar las comprobaciones de claves extranjeras
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar tablas en el orden correcto
DROP TABLE IF EXISTS detalle_pedido;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS categorias;

-- Crear tabla 'categorias'
CREATE TABLE categorias (
    id_categoria BIGINT(20) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    PRIMARY KEY (id_categoria)
) ENGINE=InnoDB;

-- Crear tabla 'clientes'
CREATE TABLE clientes (
    id_cliente BIGINT(20) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    PRIMARY KEY (id_cliente)
) ENGINE=InnoDB;

-- Crear tabla 'productos'
CREATE TABLE productos (
    id_producto BIGINT(20) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT(11) NOT NULL,
    descripcion TEXT,
    id_categoria BIGINT(20),
    imagen VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id_producto),
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
) ENGINE=InnoDB;

-- Crear tabla 'pedidos'
CREATE TABLE pedidos (
    id_pedido BIGINT(20) NOT NULL AUTO_INCREMENT,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    estado_pedido VARCHAR(50),
    id_cliente BIGINT(20) NOT NULL,
    PRIMARY KEY (id_pedido),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
) ENGINE=InnoDB;

-- Crear tabla 'detalle_pedido'
CREATE TABLE detalle_pedido (
    id_detalle_pedido BIGINT(20) NOT NULL AUTO_INCREMENT,
    id_pedido BIGINT(20) NOT NULL,
    id_producto BIGINT(20) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_detalle_pedido),
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
) ENGINE=InnoDB;

-- Habilitar nuevamente las comprobaciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- Insertar datos iniciales en 'categorias'
INSERT INTO categorias (nombre, descripcion) VALUES 
('Electrónica', 'Dispositivos electrónicos, gadgets y accesorios.'),
('Ropa', 'Prendas de vestir para hombres, mujeres y niños.'),
('Hogar y Cocina', 'Artículos para el hogar, utensilios de cocina y decoración.');

-- Insertar datos iniciales en 'clientes'
INSERT INTO clientes (nombre, apellido, email, telefono, direccion) VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', '111-222-3333', 'Calle Falsa 123'),
('Ana', 'Gómez', 'ana.gomez@example.com', '444-555-6666', 'Avenida Siempre Viva 742');

-- Insertar datos iniciales en 'productos'
INSERT INTO productos (nombre, precio, stock, descripcion, id_categoria, imagen) VALUES 
('Smartphone X', 799.99, 50, 'Cámara 48MP, 128GB almacenamiento', 1, 'smartphone_x.jpg'),
('Camiseta Algodón', 25.00, 100, 'Camiseta básica 100% algodón', 2, 'camiseta_algodon.jpg'),
('Juego de Sartenes', 89.99, 30, 'Set de 3 sartenes antiadherentes', 3, 'sartenes.jpg'),
('Auriculares Bluetooth', 120.00, 75, 'Auriculares inalámbricos con cancelación de ruido', 1, 'auriculares_bt.jpg');

-- Insertar datos iniciales en 'pedidos'
INSERT INTO pedidos (total, estado_pedido, id_cliente) VALUES 
(824.99, 'PENDIENTE', 1),
(120.00, 'COMPLETADO', 2);

-- Insertar datos iniciales en 'detalle_pedido'
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES 
(1, 1, 1, 799.99, 799.99),
(1, 2, 1, 25.00, 25.00),
(2, 4, 1, 120.00, 120.00);

-- Verificar que todas las tablas tienen datos
SELECT 'Categorias' AS Tabla, COUNT(*) AS Registros FROM categorias
UNION ALL SELECT 'Clientes', COUNT(*) FROM clientes
UNION ALL SELECT 'Productos', COUNT(*) FROM productos
UNION ALL SELECT 'Pedidos', COUNT(*) FROM pedidos
UNION ALL SELECT 'Detalle_Pedido', COUNT(*) FROM detalle_pedido;
```
</details>

#### c) Configurar `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/makastore_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_contraseña_mysql
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

> 🔑 **Importante**: Reemplaza `tu_contraseña_mysql` con la contraseña de tu usuario root de MySQL.

### 📥 3. Clonar el Repositorio

```bash
git clone https://github.com/F250680/maka-store.git
cd maka-store
```

### ▶️ 4. Ejecutar la Aplicación

#### Opción A: Desde IntelliJ IDEA
1. 📂 Abre IntelliJ IDEA
2. 📁 Selecciona **Abrir** y navega hasta la carpeta `maka-store`
3. ⏳ Espera a que Maven descargue las dependencias
4. 🔍 Busca el archivo `MakaStoreApplication.java`
5. ▶️ Clic derecho → **Ejecutar 'MakaStoreApplication.main()'**

#### Opción B: Desde la Línea de Comandos
```bash
# Construir el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

🌐 **El backend se iniciará en**: `http://localhost:8080`

---

## 🌐 Endpoints de la API

### 🏷️ Categorías (`/api/categorias`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/categorias` | Obtener todas las categorías |
| `GET` | `/api/categorias/{id}` | Obtener categoría por ID |
| `POST` | `/api/categorias` | Crear nueva categoría |
| `PUT` | `/api/categorias/{id}` | Actualizar categoría |
| `DELETE` | `/api/categorias/{id}` | Eliminar categoría |

<details>
<summary>📋 Ver ejemplos de JSON</summary>

**POST/PUT - Crear/Actualizar categoría:**
```json
{
    "nombre": "Electrónica",
    "descripcion": "Dispositivos electrónicos y gadgets."
}
```
</details>

### 👥 Clientes (`/api/clientes`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/clientes` | Obtener todos los clientes |
| `GET` | `/api/clientes/{id}` | Obtener cliente por ID |
| `POST` | `/api/clientes` | Crear nuevo cliente |
| `PUT` | `/api/clientes/{id}` | Actualizar cliente |
| `DELETE` | `/api/clientes/{id}` | Eliminar cliente |

<details>
<summary>📋 Ver ejemplos de JSON</summary>

**POST/PUT - Crear/Actualizar cliente:**
```json
{
    "nombre": "Carlos",
    "apellido": "García",
    "email": "carlos.garcia@example.com",
    "telefono": "555-123-4567",
    "direccion": "Av. Siempre Viva 123"
}
```
</details>

### 📦 Productos (`/api/productos`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/productos` | Obtener todos los productos |
| `GET` | `/api/productos/{id}` | Obtener producto por ID |
| `POST` | `/api/productos` | Crear nuevo producto |
| `PUT` | `/api/productos/{id}` | Actualizar producto |
| `DELETE` | `/api/productos/{id}` | Eliminar producto |

<details>
<summary>📋 Ver ejemplos de JSON</summary>

**POST/PUT - Crear/Actualizar producto:**
```json
{
    "nombre": "Licuadora Pro",
    "precio": 150.00,
    "stock": 20,
    "descripcion": "Licuadora de alta potencia para el hogar",
    "idCategoria": 3,
    "imagen": "licuadora.jpg"
}
```
</details>

### 📋 Pedidos (`/api/pedidos`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/pedidos` | Obtener todos los pedidos |
| `GET` | `/api/pedidos/{id}` | Obtener pedido por ID |
| `POST` | `/api/pedidos` | Crear nuevo pedido |
| `PUT` | `/api/pedidos/{id}` | Actualizar pedido |
| `DELETE` | `/api/pedidos/{id}` | Eliminar pedido |

<details>
<summary>📋 Ver ejemplos de JSON</summary>

**POST - Crear pedido:**
```json
{
    "estadoPedido": "PENDIENTE",
    "idCliente": 1,
    "detalles": [
        {
            "cantidad": 1,
            "precioUnitario": 89.99,
            "idProducto": 3
        },
        {
            "cantidad": 2,
            "precioUnitario": 25.00,
            "idProducto": 2
        }
    ]
}
```

**PUT - Actualizar pedido:**
```json
{
    "estadoPedido": "COMPLETADO",
    "idCliente": 1,
    "detalles": [
        {
            "cantidad": 1,
            "precioUnitario": 799.99,
            "idProducto": 1
        }
    ]
}
```
</details>

### 📝 Detalles de Pedido (`/api/detalles`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/detalles` | Obtener todos los detalles |
| `GET` | `/api/detalles/{id}` | Obtener detalle por ID |
| `POST` | `/api/detalles` | Crear nuevo detalle |
| `PUT` | `/api/detalles/{id}` | Actualizar detalle |
| `DELETE` | `/api/detalles/{id}` | Eliminar detalle |

<details>
<summary>📋 Ver ejemplos de JSON</summary>

**POST/PUT - Crear/Actualizar detalle:**
```json
{
    "cantidad": 1,
    "precioUnitario": 120.00,
    "idPedido": 2,
    "idProducto": 4
}
```
</details>

---

## 🧪 Pruebas con Postman

📬 **Recomendación**: Usa **Postman** para probar los endpoints de la API.

1. 📥 Descarga e instala [Postman](https://www.postman.com/)
2. 🔗 Configura la URL base: `http://localhost:8080`
3. 📝 Crea las solicitudes usando los ejemplos JSON proporcionados
4. ✅ Prueba cada endpoint para verificar su funcionamiento

---

## 🔒 Configuración CORS

El backend está configurado para permitir solicitudes desde `http://localhost:3000` (común para desarrollo de React).

**Para cambiar el origen permitido**, edita `CorsConfig.java`:

```java
// En src/main/java/com/maka/makastore/config/CorsConfig.java
registry.addMapping("/**")
    .allowedOrigins("http://localhost:3000", "https://tudominio.com") // ⬅️ Actualiza aquí
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    .allowedHeaders("*")
    .allowCredentials(true)
    .maxAge(3600);
```

---

## 🚀 Próximos Pasos (Mejoras Sugeridas)

### 🔐 Seguridad
- **Autenticación y Autorización** con Spring Security + JWT/OAuth2

### 📊 Funcionalidad
- **Paginación y Filtrado** en endpoints GET
- **Notificaciones por email** para pedidos
- **Sistema de inventario** avanzado

### 🧪 Calidad
- **Pruebas Unitarias e Integración** exhaustivas
- **Documentación API** con Swagger/OpenAPI

### 🐳 DevOps
- **Dockerización** de la aplicación
- **Despliegue en la nube** (Heroku, AWS, Google Cloud, Azure)
- **CI/CD Pipeline**

---

## 👨‍💻 Autor

**Franco Fernando Ojeda Insuasty**
- 🆔 **ID**: F250680
- 📧 **Email**: f2ojeda250680@gmail.com
- 💻 **GitHub**: [F250680](https://github.com/F250680)

---

## 🙏 Agradecimientos

¡Gracias por revisar el backend de **Maka Store**! 🎉

Si tienes alguna pregunta, sugerencia o encuentras algún problema, no dudes en:
- 📧 Contactarme por email
- 🐛 Crear un issue en GitHub
- 🔀 Hacer un pull request

---

<div align="center">
  
**⭐ Si este proyecto te fue útil, ¡no olvides darle una estrella! ⭐**

*Hecho con ❤️ y ☕ en Colombia* 🇨🇴

</div>
