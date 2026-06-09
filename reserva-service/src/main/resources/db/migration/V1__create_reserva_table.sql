CREATE TABLE reservas (
    id VARCHAR(36) PRIMARY KEY,
    fecha_reserva DATE NOT NULL,
    estado_reserva VARCHAR(50) NOT NULL,
    cliente_id VARCHAR(36) NOT NULL,
    vehiculo_id VARCHAR(36) NOT NULL
);