-- phpMyAdmin SQL Dump
-- version 3.4.11.1
-- http://www.phpmyadmin.net
--
-- Host: mysql1000.mochahost.com
-- Generation Time: Dec 05, 2013 at 05:07 AM
-- Server version: 5.5.28
-- PHP Version: 5.3.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `vbarstad_cr`
--

-- --------------------------------------------------------

--
-- Table structure for table `CodeFile`
--

CREATE TABLE IF NOT EXISTS `CodeFile` (
  `fileId` int(11) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(30) NOT NULL,
  `package` varchar(50) NOT NULL,
  `project` varchar(30) NOT NULL,
  `addedBy` varchar(20) NOT NULL,
  `date` varchar(20) NOT NULL,
  `contentString` longtext NOT NULL,
  PRIMARY KEY (`fileId`),
  UNIQUE KEY `unique` (`fileName`,`package`,`project`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

-- --------------------------------------------------------

--
-- Table structure for table `Feature`
--

CREATE TABLE IF NOT EXISTS `Feature` (
  `id` varchar(5) NOT NULL,
  `Description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Feature`
--

INSERT INTO `Feature` VALUES
('F1', 'CamelCased variable names'),
('F2', 'CamelCased method names'),
('F3', 'CamelCased constants '),
('F4', 'Camel cased Class names'),
('F5', 'Exeptions handled correct');

-- --------------------------------------------------------

--
-- Table structure for table `FeatureValue`
--

CREATE TABLE IF NOT EXISTS `FeatureValue` (
  `featureId` varchar(5) NOT NULL,
  `value` varchar(5) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `limit_from` int(11) NOT NULL COMMENT '0-100, percent',
  `limit_to` int(11) NOT NULL,
  PRIMARY KEY (`featureId`,`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `FeatureValue`
--

INSERT INTO `FeatureValue` VALUES
('F1', 'N', 'No', 0, 50),
('F1', 'Y', 'Yes', 51, 100),
('F2', 'N', 'No', 0, 50),
('F2', 'Y', 'Yes', 51, 100),
('F3', 'N', 'No', 0, 50),
('F3', 'Y', 'Yes', 51, 100),
('F4', 'N', 'No', 0, 50),
('F4', 'Y', 'Yes', 51, 100),
('F5', 'N', 'No', 0, 50),
('F5', 'Y', 'Yes', 51, 100),
('F6', 'H', 'High', 67, 100),
('F6', 'L', 'Low', 0, 33),
('F6', 'M', 'Middle', 34, 66);

-- --------------------------------------------------------

--
-- Table structure for table `Project`
--

CREATE TABLE IF NOT EXISTS `Project` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `Review`
--

CREATE TABLE IF NOT EXISTS `Review` (
  `reviewId` int(11) NOT NULL AUTO_INCREMENT,
  `fileId` int(11) NOT NULL,
  `offset` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  `reviewType` int(11) NOT NULL,
  `addedBy` varchar(20) NOT NULL,
  `comment` varchar(300) NOT NULL,
  `date` varchar(20) NOT NULL,
  PRIMARY KEY (`reviewId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=27 ;

-- --------------------------------------------------------

--
-- Table structure for table `ReviewFeatureAnalysis`
--

CREATE TABLE IF NOT EXISTS `ReviewFeatureAnalysis` (
  `fileId` int(11) NOT NULL,
  `reviewId` int(11) NOT NULL,
  `reviewType` int(11) NOT NULL,
  `featureId` varchar(5) NOT NULL,
  `value` varchar(5) DEFAULT NULL,
  `pValue` int(11) NOT NULL,
  PRIMARY KEY (`reviewId`,`featureId`,`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `ReviewType`
--

CREATE TABLE IF NOT EXISTS `ReviewType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `ReviewType`
--

INSERT INTO `ReviewType` VALUES
(1, 'Like'),
(2, 'Dislike'),


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
