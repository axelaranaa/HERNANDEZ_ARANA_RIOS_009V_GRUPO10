CREATE TABLE cliente (
    id VARCHAR(36) PRIMARY KEY,
    rut VARCHAR(15) NOT NULL,
    dv VARCHAR(1) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(150),
    fecha_registro DATE,
    estado VARCHAR(20),
    usuario_id VARCHAR(36)
);