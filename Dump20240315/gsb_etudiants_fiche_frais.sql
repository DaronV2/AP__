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
-- Table structure for table `fiche_frais`
--

DROP TABLE IF EXISTS `fiche_frais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fiche_frais` (
  `ff_id` int NOT NULL AUTO_INCREMENT,
  `ff_mois` date NOT NULL,
  `ff_qte_nuitees` int NOT NULL DEFAULT '0',
  `ff_qte_repas` int NOT NULL DEFAULT '0',
  `ff_qte_km` int NOT NULL DEFAULT '0',
  `ff_date_creation` date DEFAULT NULL,
  `ff_date_cloture` date DEFAULT NULL,
  `ff_date_validation` date DEFAULT NULL,
  `ff_date_mise_en_remboursement` date DEFAULT NULL,
  `ff_date_remboursement` date DEFAULT NULL,
  `vi_matricule` varchar(20) NOT NULL,
  `co_id` int DEFAULT NULL,
  `ef_id` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`ff_id`),
  KEY `fk_ff_comptable_idx` (`co_id`),
  KEY `fk_ff_visiteur_idx` (`vi_matricule`),
  KEY `fk_ff_etat_fiche_idx` (`ef_id`),
  CONSTRAINT `fk_ff_comptable` FOREIGN KEY (`co_id`) REFERENCES `comptable` (`co_id`),
  CONSTRAINT `fk_ff_etat_fiche` FOREIGN KEY (`ef_id`) REFERENCES `etat_fiche` (`ef_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ff_visiteur` FOREIGN KEY (`vi_matricule`) REFERENCES `visiteur` (`vi_matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fiche_frais`
--

LOCK TABLES `fiche_frais` WRITE;
/*!40000 ALTER TABLE `fiche_frais` DISABLE KEYS */;
INSERT INTO `fiche_frais` VALUES (1,'2024-02-03',9,12,750,'2024-02-03',NULL,NULL,NULL,NULL,'NRD/125-01',NULL,1),(2,'2024-01-08',15,15,927,'2024-01-08','2024-02-10','2024-02-15',NULL,NULL,'NRD/125-01',2,3),(3,'2024-01-02',2,3,189,'2024-01-02','2024-02-07','2024-02-14','2024-02-20','2024-02-27','NRD/125-02',1,5),(4,'2024-02-07',1,1,76,'2024-02-07',NULL,NULL,NULL,NULL,'NRD/125-02',NULL,1),(8,'2023-12-01',16,18,838,'2023-12-01','2024-01-08','2024-01-18','2024-01-20','2024-01-20','NRD/125-01',1,5);
/*!40000 ALTER TABLE `fiche_frais` ENABLE KEYS */;
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
