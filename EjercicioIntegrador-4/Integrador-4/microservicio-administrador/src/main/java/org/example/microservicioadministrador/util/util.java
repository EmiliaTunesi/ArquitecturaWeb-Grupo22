package org.example.microservicioadministrador.util;

public class util {
    /*
   inser
   INSERT INTO tarifas (nombre, tipo, precio_por_min, tiempoEspera, vigenteDesde, vigenteHasta, discount_percentage, priority, promo_code)
VALUES (
  'Promo Navidad 2025',
  'NORMAL',
  100, -- precio base de referencia (centavos). se aplicará discount sobre esto: 100 * (1 - 0.20) = 80
  NULL,
  TIMESTAMPTZ '2025-12-20 00:00:00+00',
  TIMESTAMPTZ '2025-12-31 00:00:00+00',
);

scrip de la tabla
-- Crea la tabla 'tarifas' con vigencia y evita solapamientos por tipo.
-- Usa rangos tstzrange y una exclusion constraint para garantizar que no existan
-- dos filas del mismo 'tipo' con periodos vigentes que se solapen.
--
-- Nota: requiere la extensión btree_gist para poder comparar 'tipo' (=) y el rango (&&) en la misma exclusion.
-- Ejecutar en PostgreSQL (como superuser la primera vez para crear la extensión).

CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE IF NOT EXISTS tarifas (
  id_tarifa BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  tipo VARCHAR(40) NOT NULL,                    -- 'NORMAL' | 'PAUSA_EXTENSA' | otros
  precio_por_min INTEGER NOT NULL CHECK (precio_por_min >= 0), -- en centavos (ej: 100 = $1.00)
  tiempoEspera INTEGER NULL CHECK (tiempoEspera > 0),         -- en minutos; NULL cuando no aplica

  vigenteDesde TIMESTAMPTZ NOT NULL,
  vigenteHasta TIMESTAMPTZ NULL, -- NULL = vigente hasta nuevo aviso

  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

  -- columna generada que representa el periodo [vigenteDesde, vigenteHasta)
  periodo TSTZRANGE GENERATED ALWAYS AS (tstzrange(vigenteDesde, COALESCE(vigenteHasta, 'infinity'::timestamptz), '[)')) STORED
);

-- Validación simple de tipos permitidos (ajustar si se agregan más tipos)
ALTER TABLE tarifas
  ADD CONSTRAINT chk_tarifas_tipo CHECK (tipo IN ('NORMAL', 'PAUSA_EXTENSA'));

-- Evita solapamientos de periodos para filas con el mismo tipo.
-- Resultado: no puede existir más de una tarifa del mismo 'tipo' vigente en un mismo instante.
ALTER TABLE tarifas
  ADD CONSTRAINT tarifas_no_solapamiento_periodos_por_tipo
    EXCLUDE USING gist (periodo WITH &&, tipo WITH =);

-- Índices útiles
CREATE INDEX IF NOT EXISTS idx_tarifas_tipo ON tarifas (tipo);
CREATE INDEX IF NOT EXISTS idx_tarifas_vigencia ON tarifas (vigenteDesde, vigenteHasta);

-- Trigger simple para mantener updated_at actualizado (opcional)
CREATE OR REPLACE FUNCTION tarifas_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_tarifas_updated_at ON tarifas;
CREATE TRIGGER trg_tarifas_updated_at
  BEFORE UPDATE ON tarifas
  FOR EACH ROW EXECUTE FUNCTION tarifas_set_updated_at();


  devuelve tarifas vigents
  SELECT id_tarifa, tipo, precio_por_min, tiempoEspera, vigenteDesde, vigenteHasta
FROM tarifas
WHERE vigenteDesde <= now()
  AND (vigenteHasta IS NULL OR vigenteHasta > now())
  AND tipo IN ('NORMAL', 'PAUSA_EXTENSA');
     */
}
