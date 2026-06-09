CREATE TABLE financiamiento (

    id VARCHAR(36) PRIMARY KEY,

    numero_cuotas INT NOT NULL,

    tasa_interes DECIMAL(5,2) NOT NULL,

    monto_solicitado DECIMAL(12,2) NOT NULL,

    pie DECIMAL(12,2) NOT NULL,

    valor_cuota DECIMAL(12,2) NOT NULL,

    estado_financiamiento VARCHAR(30) NOT NULL,

    venta_id VARCHAR(36) NOT NULL

);