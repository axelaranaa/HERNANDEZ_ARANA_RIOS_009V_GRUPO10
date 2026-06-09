CREATE TABLE modelo (
    id VARCHAR(36) NOT NULL,
    nombre_modelo VARCHAR(50) NOT NULL,
    tipo_vehiculo VARCHAR(50) NOT NULL,
    marca_id VARCHAR(36) NOT NULL,

    CONSTRAINT modelo_pk PRIMARY KEY (id)
);