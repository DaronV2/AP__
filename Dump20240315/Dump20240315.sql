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
-- Table structure for table `comptable`
--

DROP TABLE IF EXISTS `comptable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comptable` (
  `co_id` int NOT NULL AUTO_INCREMENT,
  `co_nom` varchar(45) NOT NULL,
  `co_prenom` varchar(45) NOT NULL,
  `cr_identifiant` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`co_id`),
  KEY `fk_cr_identifiant_idx` (`cr_identifiant`),
  CONSTRAINT `fk_co_credentials` FOREIGN KEY (`cr_identifiant`) REFERENCES `credentials` (`cr_identifiant`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comptable`
--

LOCK TABLES `comptable` WRITE;
/*!40000 ALTER TABLE `comptable` DISABLE KEYS */;
INSERT INTO `comptable` VALUES (1,'Étroikien','Jessica','jetroikien'),(2,'Glarot','Cédric','cglarot');
/*!40000 ALTER TABLE `comptable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credentials`
--

DROP TABLE IF EXISTS `credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credentials` (
  `cr_identifiant` varchar(30) NOT NULL,
  `cr_mot_de_passe` varchar(60) NOT NULL,
  PRIMARY KEY (`cr_identifiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` VALUES ('cglarot','cg'),('jetroikien','je'),('jleplataufray','jla'),('jnemar','jn');
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etat_fiche`
--

DROP TABLE IF EXISTS `etat_fiche`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `etat_fiche` (
  `ef_id` int NOT NULL AUTO_INCREMENT,
  `ef_libelle` varchar(30) NOT NULL,
  PRIMARY KEY (`ef_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etat_fiche`
--

LOCK TABLES `etat_fiche` WRITE;
/*!40000 ALTER TABLE `etat_fiche` DISABLE KEYS */;
INSERT INTO `etat_fiche` VALUES (1,'créée'),(2,'clôturée'),(3,'validée'),(4,'mise_en_remboursement'),(5,'remboursée');
/*!40000 ALTER TABLE `etat_fiche` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etat_hors_forfait`
--

DROP TABLE IF EXISTS `etat_hors_forfait`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `etat_hors_forfait` (
  `ehf_id` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(30) NOT NULL,
  PRIMARY KEY (`ehf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etat_hors_forfait`
--

LOCK TABLES `etat_hors_forfait` WRITE;
/*!40000 ALTER TABLE `etat_hors_forfait` DISABLE KEYS */;
INSERT INTO `etat_hors_forfait` VALUES (1,'en_attente'),(2,'validé'),(3,'refusé');
/*!40000 ALTER TABLE `etat_hors_forfait` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `visiteur`
--

DROP TABLE IF EXISTS `visiteur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visiteur` (
  `vi_matricule` varchar(20) NOT NULL,
  `vi_nom` varchar(45) NOT NULL,
  `vi_prenom` varchar(45) NOT NULL,
  `cr_identifiant` varchar(30) NOT NULL,
  PRIMARY KEY (`vi_matricule`),
  KEY `fk_cr_identifiant_idx` (`cr_identifiant`),
  CONSTRAINT `fk_vi_credentials` FOREIGN KEY (`cr_identifiant`) REFERENCES `credentials` (`cr_identifiant`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visiteur`
--

LOCK TABLES `visiteur` WRITE;
/*!40000 ALTER TABLE `visiteur` DISABLE KEYS */;
INSERT INTO `visiteur` VALUES ('NRD/125-01','Leplat Aufray','Jamy','jleplataufray'),('NRD/125-02','Némar','Jean','jnemar');
/*!40000 ALTER TABLE `visiteur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-15 14:52:31
