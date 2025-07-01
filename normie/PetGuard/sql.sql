-- --------------------------------------------------------
-- Host:                         sql.freedb.tech
-- Versión del servidor:         8.0.42-0ubuntu0.22.04.1 - (Ubuntu)
-- SO del servidor:              Linux
-- HeidiSQL Versión:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para freedb_petguardbd
CREATE DATABASE IF NOT EXISTS `freedb_petguardbd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `freedb_petguardbd`;

-- Volcando estructura para tabla freedb_petguardbd.tbHistorialMedico
CREATE TABLE IF NOT EXISTS `tbHistorialMedico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_mascota` int DEFAULT NULL,
  `tipo_evento` varchar(100) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `descripcion` text,
  `estado` varchar(10) DEFAULT 'activo',
  `dni_propietario` varchar(8) DEFAULT NULL,
  `tratamiento_aplicado` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `proxima_cita` datetime DEFAULT NULL,
  `dni_veterinario` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_mascota` (`id_mascota`),
  KEY `dni_propietario` (`dni_propietario`),
  KEY `fk_historial_veterinario` (`dni_veterinario`),
  CONSTRAINT `fk_historial_veterinario` FOREIGN KEY (`dni_veterinario`) REFERENCES `tbVeterinario` (`dni`),
  CONSTRAINT `tbHistorialMedico_ibfk_1` FOREIGN KEY (`id_mascota`) REFERENCES `tbMascota` (`id`),
  CONSTRAINT `tbHistorialMedico_ibfk_2` FOREIGN KEY (`dni_propietario`) REFERENCES `tbPropietario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla freedb_petguardbd.tbMascota
CREATE TABLE IF NOT EXISTS `tbMascota` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `especie` varchar(50) DEFAULT NULL,
  `raza` varchar(100) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  `sexo` varchar(10) DEFAULT NULL,
  `dni_propietario` varchar(8) DEFAULT NULL,
  `Url` varchar(100) DEFAULT NULL,
  `estado` varchar(10) DEFAULT 'activo',
  PRIMARY KEY (`id`),
  KEY `dni_propietario` (`dni_propietario`),
  CONSTRAINT `tbMascota_ibfk_1` FOREIGN KEY (`dni_propietario`) REFERENCES `tbPropietario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla freedb_petguardbd.tbObservacion
CREATE TABLE IF NOT EXISTS `tbObservacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_historial` int DEFAULT NULL,
  `dni_propietario` varchar(8) DEFAULT NULL,
  `dni_veterinario` varchar(8) DEFAULT NULL,
  `observacion` text,
  `fecha` datetime DEFAULT NULL,
  `estado` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'activo',
  PRIMARY KEY (`id`),
  KEY `fk_idhistorial` (`id_historial`),
  KEY `fk_obs_propietario` (`dni_propietario`),
  KEY `fk_obs_veterinario` (`dni_veterinario`),
  CONSTRAINT `fk_idhistorial` FOREIGN KEY (`id_historial`) REFERENCES `tbHistorialMedico` (`id`),
  CONSTRAINT `fk_obs_propietario` FOREIGN KEY (`dni_propietario`) REFERENCES `tbPropietario` (`dni`),
  CONSTRAINT `fk_obs_veterinario` FOREIGN KEY (`dni_veterinario`) REFERENCES `tbVeterinario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla freedb_petguardbd.tbPropietario
CREATE TABLE IF NOT EXISTS `tbPropietario` (
  `dni` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `correo` varchar(100) NOT NULL,
  `contra` varchar(100) NOT NULL,
  `estado` varchar(10) DEFAULT 'activo',
  `foto` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`dni`),
  UNIQUE KEY `telefono` (`telefono`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla freedb_petguardbd.tbRecomendacion
CREATE TABLE IF NOT EXISTS `tbRecomendacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_historial` int NOT NULL,
  `dni_veterinario` varchar(8) DEFAULT NULL,
  `recomendacion` text,
  `fecha` datetime NOT NULL,
  `estado` varchar(10) DEFAULT 'activo',
  PRIMARY KEY (`id`),
  KEY `fk_idhistorial_R` (`id_historial`),
  KEY `fk_vet_recomendacion` (`dni_veterinario`),
  CONSTRAINT `fk_idhistorial_R` FOREIGN KEY (`id_historial`) REFERENCES `tbHistorialMedico` (`id`),
  CONSTRAINT `fk_vet_recomendacion` FOREIGN KEY (`dni_veterinario`) REFERENCES `tbVeterinario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla freedb_petguardbd.tbVeterinario
CREATE TABLE IF NOT EXISTS `tbVeterinario` (
  `dni` varchar(8) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `telefono` varchar(9) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `foto` varchar(200) DEFAULT NULL,
  `contra` varchar(100) NOT NULL,
  `estado` varchar(10) DEFAULT 'activo',
  PRIMARY KEY (`dni`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
