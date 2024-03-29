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

CREATE SCHEMA gsb_etudiants;

DROP TABLE IF EXISTS `gsb_etudiants.comptable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gsb_etudiants.comptable` (
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

LOCK TABLES `gsb_etudiants.comptable` WRITE;
/*!40000 ALTER TABLE `comptable` DISABLE KEYS */;
INSERT INTO `gsb_etudiants.comptable` VALUES (1,'Étroikien','Jessica','jetroikien'),(2,'Glarot','Cédric','cglarot');
/*!40000 ALTER TABLE `comptable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credentials`
--

DROP TABLE IF EXISTS `gsb_etudiants.credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gsb_etudiants.credentials` (
  `cr_identifiant` varchar(30) NOT NULL,
  `cr_mot_de_passe` varchar(60) NOT NULL,
  PRIMARY KEY (`cr_identifiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `gsb_etudiants.credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `gsb_etudiants.credentials` VALUES ('cglarot','cg'),('jetroikien','je'),('jleplataufray','jla'),('jnemar','jn');
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etat_fiche`
--

DROP TABLE IF EXISTS `gsb_etudiants.etat_fiche`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gsb_etudiants.etat_fiche` (
  `ef_id` int NOT NULL AUTO_INCREMENT,
  `ef_libelle` varchar(30) NOT NULL,
  PRIMARY KEY (`ef_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etat_fiche`
--

LOCK TABLES `gsb_etudiants.etat_fiche` WRITE;
/*!40000 ALTER TABLE `etat_fiche` DISABLE KEYS */;
INSERT INTO `gsb_etudiants.etat_fiche` VALUES (1,'créée'),(2,'clôturée'),(3,'validée'),(4,'mise_en_remboursement'),(5,'remboursée');
/*!40000 ALTER TABLE `etat_fiche` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etat_hors_forfait`
--

DROP TABLE IF EXISTS `gsb_etudiants.etat_hors_forfait`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gsb_etudiants.etat_hors_forfait` (
  `ehf_id` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(30) NOT NULL,
  PRIMARY KEY (`ehf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etat_hors_forfait`
--

LOCK TABLES `gsb_etudiants.etat_hors_forfait` WRITE;
/*!40000 ALTER TABLE `etat_hors_forfait` DISABLE KEYS */;
INSERT INTO `gsb_etudiants.etat_hors_forfait` VALUES (1,'en_attente'),(2,'validé'),(3,'refusé');
/*!40000 ALTER TABLE `etat_hors_forfait` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fiche_frais`
--

DROP TABLE IF EXISTS `gsb_etudiants.fiche_frais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gsb_etudiants.fiche_frais` (
  `ff_id` varchar(128) NOT NULL,
  `ff_mois` date NOT NULL,
  `ff_qte_nuitees` int NOT NULL DEFAULT '0',
  `ff_qte_repas` int NOT NULL DEFAULT '0',
  `ff_qte_km` int NOT NULL DEFAULT '0',
  `ff_date_creation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ff_date_cloture` date DEFAULT NULL,
  `ff_date_validation` date DEFAULT NULL,
  `ff_date_mise_en_remboursement` date DEFAULT NULL,
  `ff_date_remboursement` date DEFAULT NULL,
  `vi_matricule` varchar(20) NOT NULL,
  `co_id` int DEFAULT NULL,
  `ef_id` int NOT NULL DEFAULT '1',
  `ff_total_nuitees` int NOT NULL,
  `ff_total_repas` int NOT NULL,
  `prix_km` int NOT NULL,
  PRIMARY KEY (`ff_id`),
  KEY `fk_ff_comptable_idx` (`co_id`),
  KEY `fk_ff_visiteur_idx` (`vi_matricule`),
  KEY `fk_ff_etat_fiche_idx` (`ef_id`),
  CONSTRAINT `fk_ff_comptable` FOREIGN KEY (`co_id`) REFERENCES `comptable` (`co_id`),
  CONSTRAINT `fk_ff_etat_fiche` FOREIGN KEY (`ef_id`) REFERENCES `etat_fiche` (`ef_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ff_visiteur` FOREIGN KEY (`vi_matricule`) REFERENCES `visiteur` (`vi_matricule`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fiche_frais`
--

LOCK TABLES `gsb_etudiants.fiche_frais` WRITE;
/*!40000 ALTER TABLE `fiche_frais` DISABLE KEYS */;
INSERT INTO `gsb_etudiants.fiche_frais` VALUES ('1','2024-02-03',9,12,750,'2024-02-02 23:00:00',NULL,NULL,NULL,NULL,'NRD/125-01',NULL,1,0,0,0),('18','2017-08-14',12,12,21,'2024-03-22 15:30:53',NULL,NULL,NULL,NULL,'NRD/125-02',NULL,1,960,348,2),('19','2017-08-14',12,12,12,'2024-03-22 15:43:18',NULL,NULL,NULL,NULL,'NRD/125-02',NULL,1,960,348,12),('2','2024-01-08',15,15,927,'2024-01-07 23:00:00','2024-02-10','2024-02-15',NULL,NULL,'NRD/125-01',2,3,0,0,0),('20','2017-08-14',12,12,12,'2024-03-22 15:44:11',NULL,NULL,NULL,NULL,'NRD/125-02',NULL,1,960,348,2),('3','2024-01-02',2,3,189,'2024-01-01 23:00:00','2024-02-07','2024-02-14','2024-02-20','2024-02-27','NRD/125-02',1,5,0,0,0),('4','2024-02-07',1,1,76,'2024-02-06 23:00:00',NULL,NULL,NULL,NULL,'NRD/125-02',NULL,1,0,0,0),('8','2023-12-01',16,18,838,'2023-11-30 23:00:00','2024-01-08','2024-01-18','2024-01-20','2024-01-20','NRD/125-01',1,5,0,0,0),('89795416-6371-4f16-900d-7fab313c7c6c','2024-03-28',12,12,12,'2024-03-28 13:09:04',NULL,NULL,NULL,NULL,'NRD/125-02',NULL,1,960,348,45),('ac81b822-4f45-4bec-8e48-a1de1c443468','2024-03-25',1,1,1,'2024-03-25 14:48:29',NULL,NULL,NULL,NULL,'NRD/125-02',NULL,1,80,29,12);
/*!40000 ALTER TABLE `fiche_frais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hors_forfait`
--

DROP TABLE IF EXISTS `gsb_etudiants.hors_forfait`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gsb_etudiants.hors_forfait` (
  `hf_id` int NOT NULL AUTO_INCREMENT,
  `hf_date` date NOT NULL,
  `hf_libelle` varchar(128) NOT NULL,
  `hf_montant` decimal(6,2) NOT NULL DEFAULT '0.00',
  `ff_id` varchar(128) NOT NULL,
  `ehf_id` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`hf_id`),
  KEY `fk_hf_etat_hors_forfait_idx` (`ehf_id`),
  CONSTRAINT `fk_hf_etat_hors_forfait` FOREIGN KEY (`ehf_id`) REFERENCES `etat_hors_forfait` (`ehf_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hors_forfait`
--

LOCK TABLES `gsb_etudiants.hors_forfait` WRITE;
/*!40000 ALTER TABLE `hors_forfait` DISABLE KEYS */;
INSERT INTO `gsb_etudiants.hors_forfait` VALUES (1,'2020-12-18','Repas Représentation',156.00,'2',1),(2,'2020-12-22','Achat Fleuriste Soirée \"MediLog\"',120.30,'2',1),(3,'2024-03-08','1',1.00,'8a7464ff-3159-40f7-906d-a4355d7cf2c6',1),(4,'2024-03-11','1',1.00,'bbfdc39b-6e35-4537-8f61-6b09028984c3',1),(5,'2024-03-01','1',1.00,'bbfdc39b-6e35-4537-8f61-6b09028984c3',1),(6,'2024-02-29','1',1.00,'249db3e0-4a8d-4dbe-9a2f-da69d9149dc7',1),(7,'2024-03-08','1',1.00,'249db3e0-4a8d-4dbe-9a2f-da69d9149dc7',1),(10,'2024-03-09','1',1.00,'0b2a5154-8371-408f-815f-e46b106fdaf9',1),(11,'2024-03-23','2',2.00,'0b2a5154-8371-408f-815f-e46b106fdaf9',1),(12,'2024-03-22','1',1.00,'836ca419-5adf-4df3-bdf9-3be384166cf1',1),(13,'2024-03-03','2',2.00,'836ca419-5adf-4df3-bdf9-3be384166cf1',1),(14,'2024-03-08','1',1.00,'eb513f02-9ef9-4892-bbf0-956569490e2e',1),(15,'2024-03-08','2',2.00,'eb513f02-9ef9-4892-bbf0-956569490e2e',1),(16,'2024-03-21','libelle',12.00,'ac81b822-4f45-4bec-8e48-a1de1c443468',1),(17,'2024-03-03','libelle2',20.00,'ac81b822-4f45-4bec-8e48-a1de1c443468',1),(18,'2024-03-29','lib1',12.00,'89795416-6371-4f16-900d-7fab313c7c6c',1),(19,'2024-03-16','lib2',15.00,'89795416-6371-4f16-900d-7fab313c7c6c',1);
/*!40000 ALTER TABLE `hors_forfait` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visiteur`
--

DROP TABLE IF EXISTS `gsb_etudiants.visiteur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gsb_etudiants.visiteur` (
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

LOCK TABLES `gsb_etudiants.visiteur` WRITE;
/*!40000 ALTER TABLE `visiteur` DISABLE KEYS */;
INSERT INTO `gsb_etudiants.visiteur` VALUES ('NRD/125-01','Leplat Aufray','Jamy','jleplataufray'),('NRD/125-02','Némar','Jean','jnemar');
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

-- Dump completed on 2024-03-29 13:38:33
