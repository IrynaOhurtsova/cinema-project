--liquibase formatted sql

-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               8.0.25 - MySQL Community Server - GPL
-- Операционная система:         Win64
-- HeidiSQL Версия:              11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Дамп структуры базы данных cinema
CREATE DATABASE IF NOT EXISTS `cinema` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cinema`;

SET FOREIGN_KEY_CHECKS=0; DROP TABLE `seance`;
SET FOREIGN_KEY_CHECKS=0; DROP TABLE `movie`;
SET FOREIGN_KEY_CHECKS=0; DROP TABLE `user`;
SET FOREIGN_KEY_CHECKS=0; DROP TABLE `ticket`;

-- Дамп структуры для таблица cinema.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(60) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп структуры для таблица cinema.movie
CREATE TABLE IF NOT EXISTS `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title_en` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title_uk` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп структуры для таблица cinema.seance
CREATE TABLE IF NOT EXISTS `seance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `movie_id` bigint NOT NULL,
  `price` double unsigned NOT NULL,
  `seating_capacity` int unsigned NOT NULL,
  `free_places` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `movie_title` (`movie_id`) USING BTREE,
  CONSTRAINT `movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп структуры для таблица cinema.ticket
CREATE TABLE IF NOT EXISTS `ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `seance_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `schedule_id` (`seance_id`) USING BTREE,
  CONSTRAINT `seance_id` FOREIGN KEY (`seance_id`) REFERENCES `seance` (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы cinema.movie: ~5 rows (приблизительно)
DELETE FROM `movie`;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` (`id`, `title_en`, `title_uk`) VALUES
	(1, 'The Addams Family', 'Сімейка Адамсів'),
	(2, 'Edward Scissorshands', 'Едвард руки-ножиці'),
	(3, 'Back to the future', 'Назад у майбутнє'),
	(4, 'Alladin', 'Аладін'),
	(5, 'The Lion King', 'Король Лев');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;

-- Дамп данных таблицы cinema.seance: ~23 rows (приблизительно)
DELETE FROM `seance`;
/*!40000 ALTER TABLE `seance` DISABLE KEYS */;
INSERT INTO `seance` (`id`, `date`, `time`, `movie_id`, `price`, `seating_capacity`, `free_places`) VALUES
	(1, '2021-10-04', '09:00:00', 4, 50, 300, 294),
	(2, '2021-10-04', '11:00:00', 2, 50, 300, 296),
	(3, '2021-10-04', '14:00:00', 5, 50, 300, 296),
	(5, '2021-10-04', '20:00:00', 3, 50, 300, 298),
	(6, '2021-10-04', '22:00:00', 2, 50, 300, 298),
	(7, '2021-10-05', '09:00:00', 5, 50, 300, 295),
	(41, '2021-09-17', '11:00:00', 1, 70, 300, 198),
	(42, '2021-09-09', '14:00:00', 5, 60, 300, 299),
	(44, '2021-09-09', '11:00:00', 1, 50, 300, 299),
	(47, '2021-09-10', '14:00:00', 1, 50, 300, 298),
	(48, '2021-09-11', '17:00:00', 1, 50, 300, 299),
	(49, '2021-09-11', '19:00:00', 1, 50, 300, 299),
	(50, '2021-09-11', '22:00:00', 1, 50, 300, 299),
	(51, '2021-09-12', '22:00:00', 1, 50, 300, 300),
	(52, '2021-09-12', '09:00:00', 1, 50, 300, 299),
	(53, '2021-09-12', '11:00:00', 1, 50, 300, 300),
	(54, '2021-09-25', '14:00:00', 4, 50, 300, 299),
	(55, '2021-09-10', '16:00:00', 5, 100, 300, 300),
	(60, '2021-09-23', '11:00:00', 1, 50, 300, 300),
	(63, '2021-09-28', '11:00:00', 5, 50, 300, 300),
	(64, '2021-09-30', '22:00:00', 1, 50, 300, 300),
	(65, '2021-09-30', '17:00:00', 5, 130, 300, 300),
	(66, '2021-09-29', '14:00:00', 1, 50, 300, 300);
/*!40000 ALTER TABLE `seance` ENABLE KEYS */;

-- Дамп данных таблицы cinema.ticket: ~34 rows (приблизительно)
DELETE FROM `ticket`;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` (`id`, `user_id`, `seance_id`) VALUES
	(16, 3, 1),
	(17, 3, 1),
	(18, 3, 2),
	(19, 3, 7),
	(20, 3, 7),
	(21, 3, 7),
	(22, 3, 3),
	(23, 3, 5),
	(24, 3, 3),
	(25, 2, 7),
	(26, 2, 6),
	(27, 4, 7),
	(28, 4, 2),
	(29, 4, 2),
	(30, 4, 3),
	(31, 4, 3),
	(32, 2, 1),
	(33, 2, 1),
	(34, 2, 1),
	(35, 2, 2),
	(36, 2, 5),
	(37, 2, 6),
	(38, 2, 42),
	(39, 2, 44),
	(40, 2, 52),
	(41, 2, 41),
	(42, 2, 49),
	(43, 2, 48),
	(44, 2, 47),
	(45, 2, 50),
	(46, 8, 1),
	(47, 8, 47),
	(48, 8, 54),
	(49, 2, 41);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;

-- Дамп данных таблицы cinema.user: ~7 rows (приблизительно)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
	(1, 'admin', 'admin', 'ADMIN'),
	(2, 'ivanov', 'ivanov', 'CLIENT'),
	(3, 'petrov', 'petrov', 'CLIENT'),
	(4, 'pupkin', 'pupken', 'CLIENT'),
	(5, 'ohurtsova', 'ohurtsova', 'CLIENT'),
	(6, 'ivanov456', 'ivanov456', 'CLIENT'),
	(7, 'petro789', 'petro789', 'CLIENT'),
	(8, 'ivanov890', 'ivanov890', 'CLIENT');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
