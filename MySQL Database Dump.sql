CREATE DATABASE  IF NOT EXISTS `itassetdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `itassetdb`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: itassetdb
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset` (
  `id` int NOT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `model_num` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `assigned_to` int DEFAULT NULL,
  `location` int DEFAULT NULL,
  `purchased_date` varchar(255) DEFAULT NULL,
  `purchased_price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES (1,'Corsair','Keyboard','K60 RGB Pro','Corsair K60 RGB Pro Mechanical Gaming Keyboard - CHERRY Mechanical Keyswitches - Durable AluminumFrame - Customizable Per-Key RGB Backlighting',1,1,'2019-03-19',89.99),(2,'','Keyboard','','',1,0,'2020-12-15',24.99),(4,'Corsair','Mouse','Scimitar RGB Elite','',0,0,'2018-02-25',79.99),(5,'LG','Monitor','34GP83A-B','34 Inch 21: 9 UltraGear Curved QHD (3440 x 1440) 1ms Nano IPS Gaming',1,1,'2021-07-21',799.99),(6,'NB North Bayou','Monitor Stand','1001070680','NB North Bayou Dual Monitor Desk Mount Stand Full Motion Swivel Computer Monitor Arm for Two Screens 17-27 Inch with 4.4~19.8lbs Load Capacity for Each Display F160 ',0,0,'2020-05-07',54.9);
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `email_address` varchar(255) NOT NULL,
  `work_location` int NOT NULL,
  `second_work_location` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Mitchell','Jonathan','Bauer','mitchell@bauerperception.com',1,0);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment` (
  `id` int NOT NULL,
  `mfr` varchar(255) NOT NULL,
  `model_num` varchar(255) DEFAULT NULL,
  `equipment_type` varchar(255) NOT NULL,
  `load_out_id` int NOT NULL,
  `load_out_slot_num` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `purchase_price` float DEFAULT NULL,
  `last_purchase_price` float DEFAULT NULL,
  `purchase_url` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,'Lenovo','Legion','Laptop',1,1,1,1099.99,1099.99,'amazon.com'),(3,'Logitech','920-009294','Keyboard',1,3,1,99.99,99.99,'https://www.amazon.com/Logitech-Advanced-Wireless-Illuminated-Keyboard/dp/B07S92QBCJ/ref=sr_1_2_sspa?dchild=1&keywords=keyboard&qid=1633545495&s=electronics&sr=1-2-spons&psc=1&spLa=ZW5jcnlwdGVkUXVhbGlmaWVyPUEyRkM4TVRFOTJJVlVXJmVuY3J5cHRlZElkPUEwMTUzOTA1VjVUNVMxRFM0TTBNJmVuY3J5cHRlZEFkSWQ9QTA3NDk0MjgzNElMUDJJOEtNOVoxJndpZGdldE5hbWU9c3BfYXRmJmFjdGlvbj1jbGlja1JlZGlyZWN0JmRvTm90TG9nQ2xpY2s9dHJ1ZQ==');
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loadouts`
--

DROP TABLE IF EXISTS `loadouts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loadouts` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loadouts`
--

LOCK TABLES `loadouts` WRITE;
/*!40000 ALTER TABLE `loadouts` DISABLE KEYS */;
INSERT INTO `loadouts` VALUES (1,'Remote Office W/ Lapto');
/*!40000 ALTER TABLE `loadouts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `loadout_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `location_name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (1,'Mitchell Bauer\'s Home Office',1);
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `types` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (16,'Cable'),(9,'Chair'),(5,'Computer Tower'),(8,'Desk'),(15,'Docking Station'),(14,'Headphones'),(11,'Headset'),(3,'Keyboard'),(6,'Laptop'),(13,'Microphone'),(7,'Mobile Phone'),(1,'Monitor'),(10,'Monitor Stand'),(2,'Mouse'),(4,'Mouse Pad'),(17,'Other'),(12,'Webcam');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'itassetdb'
--

--
-- Dumping routines for database 'itassetdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-29  8:20:08
