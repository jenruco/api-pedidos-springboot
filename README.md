# Aplicación de Gestión de Pedidos

Una aplicación backend moderna desarrollada con **Spring Boot** que proporciona una API REST para la gestión integral de pedidos, usuarios, productos y roles. Incluye autenticación segura con JWT, validaciones robustas y capacidades de despliegue contenerizado y orquestado mediante **Docker** y **Kubernetes**.

## 🎯 Características

- ✅ **Autenticación y Autorización**: Implementación segura con JWT (JSON Web Tokens)
- ✅ **Cifrado de Contraseñas**: BCrypt para encriptación segura de contraseñas
- ✅ **Gestión de Usuarios**: Registro, login y manejo de perfiles de usuario
- ✅ **Sistema de Roles**: Control de acceso basado en roles (RBAC)
- ✅ **Gestión de Productos**: CRUD completo con categorías
- ✅ **Carrito de Compras**: Gestión completa con relaciones JPA
- ✅ **Gestión de Pedidos**: Creación y seguimiento de pedidos
- ✅ **Validaciones Robustas**: Validaciones en DTOs con mensajes personalizados
- ✅ **Manejo de Excepciones**: Controlador global de excepciones
- ✅ **Base de Datos Relacional**: PostgreSQL con Hibernate/JPA
- ✅ **Mapeo de Objetos**: ModelMapper para conversión entre entidades y DTOs
- ✅ **Logging Detallado**: Trazabilidad de operaciones críticas
- ✅ **Dockerización**: Empaquetado de la aplicación en una imagen Docker reproducible
- ✅ **Orquestación con Kubernetes**: Despliegue declarativo y escalable en un clúster K8s

## 🛠️ Tecnologías Utilizadas

### Backend

- **Java 21**: Lenguaje de programación
- **Spring Boot 4.0.2**: Framework principal
- **Spring Data JPA**: ORM para acceso a datos
- **Spring Security**: Autenticación y autorización
- **Spring Web MVC**: REST API

### Base de Datos

- **PostgreSQL**: Sistema gestor de bases de datos relacional
- **Hibernate**: ORM Java

### Librerías Adicionales

- **JWT (JJWT 0.12.3)**: Manejo de tokens de autenticación
- **ModelMapper 3.1.1**: Mapeo automático entre objetos
- **Lombok**: Reducción de boilerplate
- **Validation API**: Validación de datos

### Infraestructura y Herramientas

- **Maven**: Gestor de dependencias y construcción del artefacto JAR
- **Devtools**: Recarga automática en desarrollo
- **Docker / Docker Compose**: Construcción de imágenes, gestión de contenedores y definición de entorno local
- **Kubernetes**: Orquestación de contenedores (Deployments, Services, ConfigMaps, Secrets)
- **kubectl**: Cliente de línea de comandos para operar sobre el clúster

## 🐳 Dockerización

La aplicación se ha dockerizado siguiendo un enfoque de empaquetado inmutable:

- **Imagen base**: Se parte de una imagen oficial de `eclipse-temurin`/`openjdk` adecuada para **Java 21**, sobre la cual se copia el JAR generado por Maven.
- **Separación de build y runtime**: El proceso de compilación se realiza externamente (fase Maven), y la imagen Docker solo contiene el artefacto ya compilado y las dependencias necesarias para tiempo de ejecución.
- **Configuración externalizada**: Las propiedades sensibles (credenciales de BD, secretos JWT, etc.) no se hornean en la imagen. Se inyectan mediante variables de entorno o ficheros de configuración montados en tiempo de despliegue.
- **Optimización de capas**: El `Dockerfile` está estructurado para maximizar el caché de capas (dependencias primero, código de aplicación después), reduciendo tiempos de reconstrucción.

Ejemplo de flujo mínimo local:

```bash
mvn clean package -DskipTests
docker build -t pedidos-backend:latest .
docker run --rm -p 8080:8080 --env-file .env pedidos-backend:latest
```

Opcionalmente, se puede utilizar **Docker Compose** para levantar la aplicación junto a PostgreSQL:

```bash
docker compose up -d
```

## ☸️ Despliegue en Kubernetes

El proyecto está preparado para ejecutarse en un clúster Kubernetes siguiendo buenas prácticas de 12-factor app:

- **Manifiestos declarativos**:
  - `Deployment`: Define el número de réplicas, estrategia de actualización rolling y configuración de recursos (`requests`/`limits` de CPU y memoria).
  - `Service`: Expone la aplicación dentro del clúster (ClusterIP / NodePort) y actúa como punto de entrada estable para el tráfico hacia los pods.
  - `ConfigMap`: Inyección de configuración no sensible (por ejemplo, `SPRING_PROFILES_ACTIVE`, URLs de servicios externos).
  - `Secret`: Almacenamiento cifrado a nivel de clúster de credenciales de base de datos, claves de firma JWT, etc.
- **Health checks**:
  - `livenessProbe` y `readinessProbe` configuradas sobre endpoints de salud de Spring Boot (por ejemplo `/actuator/health`), garantizando que solo los pods sanos reciban tráfico.
- **Escalabilidad horizontal**:
  - Soporte para `HorizontalPodAutoscaler (HPA)` basado en métricas de CPU/memoria, permitiendo escalar dinámicamente el número de réplicas según la carga.
- **Observabilidad**:
  - Integración con el stack de logging del clúster (por ejemplo, EFK/ELK) mediante logs estructurados en stdout/stderr.

Flujo genérico de despliegue:

```bash
docker build -t <registry>/pedidos-backend:tag .
docker push <registry>/pedidos-backend:tag

kubectl apply -f k8s/deployment.yml
kubectl apply -f k8s/service.yml
kubectl apply -f k8s/configmap.yml
kubectl apply -f k8s/secret.yml
```

## 📋 Requisitos Previos

- Java JDK 21 o superior
- PostgreSQL 12 o superior
- Maven 3.6 o superior
- Git

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <repositorio-url>
cd pedidos
```

### 2. Configurar la Base de Datos

Crear la base de datos en PostgreSQL:

```sql
CREATE DATABASE pedidodb;
```

O ejecutar el script SQL proporcionado:

```bash
psql -U postgres -d pedidodb -f ScriptTablas.sql
```

### 3. Configurar las Propiedades

Editar `src/main/resources/application.properties` si es necesario:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pedidodb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 4. Instalar Dependencias

```bash
mvn clean install
```

### 5. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O compilar y ejecutar el JAR:

```bash
mvn clean package
java -jar target/pedidos-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: `http://localhost:8080`

## 📊 Estructura del Proyecto

```
src/main/java/com/jenruco/pedidos/
├── config/
│   ├── ModelMapperConfig.java       # Configuración de ModelMapper
│   └── SecurityConfig.java           # Configuración de Spring Security
├── exception/
│   └── GlobalExceptionHandler.java   # Manejo centralizado de excepciones
├── security/
│   └── JwtAuthFilter.java            # Filtro de autenticación JWT
├── usuario/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
├── rol/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
├── producto/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
├── util/
│   └── JwtUtil.java                  # Utilidades para manejo de JWT
└── PedidosApplication.java           # Clase principal
```

## 🔐 Seguridad

### Autenticación JWT

- Los tokens JWT tienen una validez de 24 horas
- Se envían en el header `Authorization: Bearer <token>`
- Se validan en cada petición protegida

### Control de Acceso

- Sistema de roles flexible
- Endpoints protegidos según permisos
- Encriptación de contraseñas

## 📚 Modelos de Datos

### Usuario

```sql
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    rol_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Producto

```sql
CREATE TABLE productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10,2) NOT NULL CHECK (precio > 0),
    stock INT NOT NULL CHECK (stock >= 0),
    categoria_id BIGINT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Rol

```sql
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🔗 Endpoints Principales

### Autenticación

- `POST /api/usuarios/login` - Iniciar sesión
- `POST /api/usuarios/registro` - Registrar nuevo usuario

### Usuarios

- `GET /api/usuarios` - Listar usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario

### Productos

- `GET /api/productos` - Listar productos
- `GET /api/productos/{id}` - Obtener producto por ID
- `POST /api/productos` - Crear producto
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto

### Roles

- `GET /api/roles` - Listar roles
- `POST /api/roles` - Crear rol
- `PUT /api/roles/{id}` - Actualizar rol

## ✅ Validaciones

Todos los DTOs incluyen validaciones como:

- `@NotBlank`: Campos obligatorios no vacíos
- `@NotNull`: Campos obligatorios
- `@Size`: Límite de caracteres
- `@DecimalMin/Max`: Rangos numéricos
- `@Email`: Formato de correo
- `@PositiveOrZero`: Números positivos

## 🧪 Testing

Ejecutar las pruebas:

```bash
mvn test
```

## 🔄 Flujo de Ejemplo

1. **Registro**: Usuario se registra con credenciales
2. **Login**: Usuario inicia sesión y recibe JWT
3. **Consulta Protegida**: Usuario envía JWT en el header
4. **Validación**: Filtro JWT valida el token
5. **Acceso**: Si es válido, se permite el acceso al recurso

## 🎓 Patrones de Diseño Utilizados

- **MVC**: Separación de Responsabilidades (Controllers, Services, Repositories)
- **DTO**: Transferencia de Datos entre capas
- **Singleton**: Configuraciones y Utilidades
- **Factory**: Creación de objetos con ModelMapper
- **Filter**: Validación de JWT en cada petición

## 📝 Notas de Desarrollo

- La aplicación utiliza JPA Hibernate para ORM con PostgreSQL
- Las propiedades JPA incluyen `spring.jpa.show-sql=true` para debugging
- DDL automático configurado con `spring.jpa.hibernate.ddl-auto=update`
- Todos los DTOs incluyen validaciones personalizadas
 - El ciclo de vida de despliegue contempla build Maven, empaquetado Docker, publicación en registro de contenedores y orquestación en Kubernetes

## 🤝 Contribución

Este es un proyecto personal para portafolio. Si deseas reportar errores o sugerir mejoras, puedes crear un issue.

## 📄 Licencia

Este proyecto está disponible bajo licencia MIT.

## 👨‍💻 Autor

Proyecto desarrollado como parte del portafolio profesional.

---

**Última actualización**: Febrero 2026
