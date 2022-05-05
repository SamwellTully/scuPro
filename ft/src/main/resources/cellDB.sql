/*
SQLyog Ultimate v8.32 
MySQL - 5.7.20 : Database - cell
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cell` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cell`;

/*Table structure for table `cell` */

DROP TABLE IF EXISTS `cell`;

CREATE TABLE `cell` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sample_name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `cell` */

insert  into `cell`(`id`,`sample_name`) values (1,'肾脏'),(2,'DNA'),(3,'RNA');

/*Table structure for table `cell_property` */

DROP TABLE IF EXISTS `cell_property`;

CREATE TABLE `cell_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(20) NOT NULL COMMENT '属性',
  `key` varchar(20) NOT NULL COMMENT '值',
  `cell_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `cell_property` */

insert  into `cell_property`(`id`,`value`,`key`,`cell_id`) values (1,'2022-4-27','stock_time',1),(2,'test','cell_name',1),(3,'5','DnaProperty1',2),(4,'56','DnaProperty2',2),(5,'56','DnaProperty3',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
