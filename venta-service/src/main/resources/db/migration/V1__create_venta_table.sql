CREATE TABLE venta (
    id VARCHAR(36) PRIMARY KEY,
    fecha_venta DATE NOT NULL,
    monto_total DECIMAL(12,2) NOT NULL,
    estado_venta VARCHAR(30) NOT NULL,
    cliente_id VARCHAR(36) NOT NULL
);