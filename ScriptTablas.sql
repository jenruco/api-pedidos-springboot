-- =========================
-- ROLES
-- =========================
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles (nombre) VALUES ('ADMIN'), ('USER'), ('CLIENTE');
-- =========================
-- USUARIOS
-- =========================
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol_id INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_usuario_rol
        FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE INDEX idx_usuario_email ON usuarios(email);

-- =========================
-- CATEGORÍAS
-- =========================
CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

-- =========================
-- PRODUCTOS
-- =========================
CREATE TABLE productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10,2) NOT NULL CHECK (precio > 0),
    stock INT NOT NULL CHECK (stock >= 0),
    categoria_id BIGINT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE INDEX idx_producto_categoria ON productos(categoria_id);
CREATE INDEX idx_producto_nombre ON productos(nombre);

-- =========================
-- CARRITO
-- =========================
CREATE TABLE carrito (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_carrito_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- ITEMS DEL CARRITO
CREATE TABLE carrito_items (
    id BIGSERIAL PRIMARY KEY,
    carrito_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),

    CONSTRAINT fk_item_carrito
        FOREIGN KEY (carrito_id) REFERENCES carrito(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_producto
        FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- =========================
-- ÓRDENES
-- =========================
CREATE TABLE ordenes (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    total NUMERIC(12,2) NOT NULL CHECK (total >= 0),
    estado VARCHAR(30) NOT NULL, -- PENDIENTE, PAGADO, ENVIADO, CANCELADO
    fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_orden_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE INDEX idx_orden_usuario ON ordenes(usuario_id);
CREATE INDEX idx_orden_estado ON ordenes(estado);

-- DETALLE DE ÓRDENES
CREATE TABLE orden_detalle (
    id BIGSERIAL PRIMARY KEY,
    orden_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10,2) NOT NULL CHECK (precio_unitario > 0),
    subtotal NUMERIC(12,2) NOT NULL,

    CONSTRAINT fk_detalle_orden
        FOREIGN KEY (orden_id) REFERENCES ordenes(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (producto_id) REFERENCES productos(id)
);

alter table usuarios drop login;
select * from usuarios;
select * from categorias;
select * from productos;

insert into categorias (nombre, descripcion) values('Snacks', 'Tipos de Snacks');
insert into categorias (nombre, descripcion) values('Bebidas', 'Tipos de bebidas');

