-- Tabla que registra cada viaje que realiza un monopatín del sistema
-- Un viaje tiene: horarios, paradas inicial y final, km recorridos, tarifa aplicada y datos del usuario.

CREATE TABLE viaje_ia (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,

    id_usuario BIGINT NOT NULL,
    id_cuenta BIGINT NOT NULL,
    monopatin_id BIGINT NOT NULL,

    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME,

    parada_inicio_id BIGINT NOT NULL,
    parada_fin_id BIGINT,

    km_recorridos DECIMAL(10,2),
    tarifa_id BIGINT,
    tiempo_total_minutos INT,
    pausa_total_minutos INT,

    costo_total DECIMAL(10,2)
);
/*
 CREATE TABLE viaje (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,

    id_usuario BIGINT NOT NULL,
    id_cuenta BIGINT NOT NULL,
    monopatin_id BIGINT NOT NULL,

    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME,

    parada_inicio_id BIGINT NOT NULL,
    parada_fin_id BIGINT,

    km_recorridos DECIMAL(10,2),
    tarifa_id BIGINT,
    tiempo_total_minutos INT,
    pausa_total_minutos INT,

    costo_total DECIMAL(10,2)
);
 */