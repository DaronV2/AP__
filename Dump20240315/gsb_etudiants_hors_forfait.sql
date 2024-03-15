-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gsb_etudiants
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hors_forfait`
--

DROP TABLE IF EXISTS `hors_forfait`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hors_forfait` (
  `hf_id` int NOT NULL AUTO_INCREMENT,
  `hf_date` date NOT NULL,
  `hf_libelle` varchar(128) NOT NULL,
  `hf_montant` decimal(6,2) NOT NULL DEFAULT '0.00',
  `ff_id` int NOT NULL,
  `ehf_id` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`hf_id`),
  KEY `fk_hf_fiche_frais_idx` (`ff_id`),
  KEY `fk_hf_etat_hors_forfait_idx` (`ehf_id`),
  CONSTRAINT `fk_hf_etat_hors_forfait` FOREIGN KEY (`ehf_id`) REFERENCES `etat_hors_forfait` (`ehf_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_hf_fiche_frais` FOREIGN KEY (`ff_id`) REFERENCES `fiche_frais` (`ff_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hors_forfait`
--

LOCK TABLES `hors_forfait` WRITE;
/*!40000 ALTER TABLE `hors_forfait` DISABLE KEYS */;
INSERT INTO `hors_forfait` VALUES (1,'2020-12-18','Repas Représentation',156.00,2,1),(2,'2020-12-22','Achat Fleuriste Soirée \"MediLog\"',120.30,2,1);
/*!40000 ALTER TABLE `hors_forfait` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-15 14:51:19
