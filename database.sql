SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `diskType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

INSERT INTO `diskType` (`id`, `description`) VALUES
(1, 'CD-RW'),
(2, 'DVD-R'),
(3, 'BR'),
(4, 'HD-DVD');

CREATE TABLE IF NOT EXISTS `informationType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

INSERT INTO `informationType` (`id`, `description`) VALUES
(1, 'Фильмы'),
(2, 'Музыка'),
(3, 'Игры');

CREATE TABLE IF NOT EXISTS `disk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `diskType_id` int(11) NOT NULL,
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(128) NOT NULL,
  `informationType_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1` (`diskType_id`),
  KEY `fk2` (`informationType_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

INSERT INTO `disk` (`id`, `diskType_id`, `title`, `description`, `informationType_id`) VALUES
(1, 1, 'Владимир Высоцкий: дискография', 'Описание дискографии(sample)', 2),
(2, 2, 'Семнадцать мгнований весны', 'Описание сериала(sample)', 1),
(3, 3, 'The Witcher III: Wild Hunt', 'Описание игры(sample)', 3);

ALTER TABLE `disk`
  ADD CONSTRAINT `disk_ibfk_1` FOREIGN KEY (`diskType_id`) REFERENCES `diskType` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `disk`
  ADD CONSTRAINT `disk_ibfk_2` FOREIGN KEY (`informationType_id`) REFERENCES `informationType` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;