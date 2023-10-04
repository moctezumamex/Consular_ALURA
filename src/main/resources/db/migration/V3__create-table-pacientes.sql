
CREATE TABLE pacientes
(
    id BIGINT  NOT NULL auto_increment,
    nombre VARCHAR(100)  NOT NULL,
    email VARCHAR(100)  NOT NULL UNIQUE,
    documentoIdentidad VARCHAR(14)  NOT NULL UNIQUE,
    telefono VARCHAR(20)  NOT NULL,
    urbanización VARCHAR(100)  NOT NULL,
    distrito VARCHAR(100)  NOT NULL,
    codigoPostal VARCHAR(9)  NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    provincia VARCHAR(100)  NOT NULL,
    ciudad VARCHAR(100)  NOT NULL,
    PRIMARY KEY (id)
)