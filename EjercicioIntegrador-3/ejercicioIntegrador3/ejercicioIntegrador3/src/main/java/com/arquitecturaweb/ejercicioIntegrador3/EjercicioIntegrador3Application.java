package com.arquitecturaweb.ejercicioIntegrador3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EjercicioIntegrador3Application {

	public static void main(String[] args) {

        SpringApplication.run(EjercicioIntegrador3Application.class, args);
	}
/*
"play" aqui
Abrí el navegador y entrá a:
http://localhost:8081/h2-console
En el H2 Console, poné:
JDBC URL: jdbc:h2:mem:testdb
User: sa (contraseña vacía)
Click en "Connect"
insertar datos manualmente
 */
}

/*
INSERT INTO carrera (id_carrera, nombre, duracion) VALUES
(1, 'Ingeniería Informática', 5),
(2, 'Licenciatura en Administración', 4),
(3, 'Ingeniería Electrónica', 5),
(4, 'Contador Público', 4);


INSERT INTO ESTUDIANTE (id, nombre, apellido, edad, dni, email, genero, ciudad_residencia, lu) VALUES
(1, 'Juan', 'Pérez', 22, '12345678', 'juan.perez@email.com', 'Masculino', 'Córdoba', 1001),
(2, 'Ana', 'García', 24, '23456789', 'ana.garcia@email.com', 'Femenino', 'Rosario', 1002),
(3, 'Lucas', 'Martínez', 21, '34567890', 'lucas.martinez@email.com', 'Masculino', 'Buenos Aires', 1003),
(4, 'María', 'López', 23, '45678901', 'maria.lopez@email.com', 'Femenino', 'Mendoza', 1004),
(5, 'Sofía', 'Fernández', 25, '56789012', 'sofia.fernandez@email.com', 'Femenino', 'La Plata', 1005);


INSERT INTO estudiante_carrera (estudiante_id, carrera_id, anio_inicio, anio_fin, antiguedad) VALUES
(1, 1, '2023', NULL, 1),
(2, 1, '2022', NULL, 2),
(3, 2, '2023', NULL, 1),
(4, 3, '2022', NULL, 2),
(1, 2, '2022', '2023', 2),
(2, 4, '2023', NULL, 1);
 */

