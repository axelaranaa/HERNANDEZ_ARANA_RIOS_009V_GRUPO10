CREATE TABLE pagos (
    id VARCHAR(36) PRIMARY KEY,
    fecha_pago DATE NOT NULL,
    monto_abonado DECIMAL(15,2) NOT NULL,
    metodo_pago VARCHAR(100) NOT NULL,
    estado_pago VARCHAR(50) NOT NULL,
    venta_id VARCHAR(36) NOT NULL
);