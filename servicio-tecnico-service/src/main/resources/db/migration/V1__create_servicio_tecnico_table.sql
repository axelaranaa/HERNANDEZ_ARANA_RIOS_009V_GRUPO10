CREATE TABLE servicio_tecnico (
    id VARCHAR(36) PRIMARY KEY,
    fecha_ingreso DATE NOT NULL,
    fecha_salida DATE,
    diagnostico VARCHAR(500),
    costo DECIMAL(12,2),
    estado_servicio VARCHAR(100),
    vehiculo_id VARCHAR(36) NOT NULL
);