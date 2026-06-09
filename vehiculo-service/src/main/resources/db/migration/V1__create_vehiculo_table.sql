CREATE TABLE vehiculo (
    id VARCHAR(36) PRIMARY KEY,
    patente VARCHAR(15) NOT NULL,
    anio INT NOT NULL,
    kilometraje DECIMAL(12,2) NOT NULL,
    precio DECIMAL(12,2) NOT NULL,
    color VARCHAR(50) NOT NULL,
    transmision VARCHAR(50) NOT NULL,
    combustible VARCHAR(50) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    modelo_id VARCHAR(36) NOT NULL
);