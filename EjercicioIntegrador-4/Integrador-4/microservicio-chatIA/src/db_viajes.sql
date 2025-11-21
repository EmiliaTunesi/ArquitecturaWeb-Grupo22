CREATE TABLE IF NOT EXISTS viajes_monopatin (
    monopatin_id            INT NOT NULL,
    fecha_hora_inicio       DATETIME NOT NULL,
    fecha_hora_fin          DATETIME NOT NULL,
    parada_inicio_id        INT NOT NULL,
    parada_fin_id           INT NOT NULL,
    km_recorridos           DECIMAL(5,2) NOT NULL,
    tarifa_id               INT NOT NULL,
    tiempo_total_minutos    INT NOT NULL,
    pausa_total_minutos     INT NOT NULL,
    id_usuario              INT NOT NULL,
    id_cuenta               INT NOT NULL,

    PRIMARY KEY (monopatin_id)
    );
