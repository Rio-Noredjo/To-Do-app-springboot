-- -----------------------------------------------------
-- Schema to-do
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `to-do`;

CREATE SCHEMA IF NOT EXISTS `to-do`; 
USE `to-do` ;

-- -----------------------------------------------------
-- Table `to-do`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `to-do`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `to-do`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `to-do`.`address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `street` VARCHAR(255) NOT NULL,
  `zip_code` VARCHAR(255) NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_country1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `to-do`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `to-do`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `to-do`.`category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(3) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table `to-do`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `to-do`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `user_roles` VARCHAR(45) NOT NULL,
  `address_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `address_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_address1_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `to-do`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `to-do`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `to-do`.`item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NULL DEFAULT NULL,
  `content` VARCHAR(500) NULL DEFAULT NULL,
  `status` VARCHAR(30) NULL DEFAULT NULL,
  `date_created` DATETIME(6) NOT NULL,
  `last_updated` DATETIME(6) NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_item_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_item_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `to-do`.`user` (`id`));

-- -----------------------------------------------------
-- Table `to-do`.`item_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `to-do`.`item_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` BIGINT NOT NULL,
  `category_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_has_item_item1_idx` (`item_id` ASC) VISIBLE,
  INDEX `fk_category_has_item_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_has_item_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `to-do`.`category` (`id`),
  CONSTRAINT `fk_category_has_item_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `to-do`.`item` (`id`));

--
-- Data for table `Country`
--
INSERT INTO`to-do`.`country` VALUES 
(1,'NL','Netherlands'),
(2,'PT','Portugal'),
(3,'DE','Germany'),
(4,'IN','India'),
(5,'TR','Turkey'),
(6,'US','United States'),
(7,'BE','Belgium'),
(8,'SW','Switzerland'),
(9,'AU','Australia'),
(10,'EN','England'),
(11,'SO','South Africa'),
(12,'CA','Canada');

--
-- Data for table `address`
--
INSERT INTO `to-do`.`address` VALUES 
(1,'Noord-Holland','Amsterdam','De dam 101','2134 AS',1),
(2,'Washington','Seattle','Redmond 55','NE 80',2),
(3,'Algarve','Lagos','Beachpark 55','8600-315',3),
(4,'Punjav','Delhi','Karnataka 14-A','25-253',4);

--
-- Data for table `user`
--
INSERT INTO `to-do`.`user` VALUES 
(1,'Rio','Noredjo', 'rio@noredjo.com','Password01!','READ_ONLY', 1),
(2,'Jo-Ann','Williams', 'jo-ann@williams.com','Password01!','CUSTOMER', 2),
(3,'Pedro','Fernandes', 'pedro@fernandes.com','Password01!','ADMIN', 3),
(4,'Navdeep','Singh', 'navdeep@singh.com','Password01!','ADMIN,CUSTOMER', 4);

--
-- Data for table `item_category`
--
INSERT INTO `to-do`.`category` VALUES 
(1,'HE','Health'),
(2,'UR','Urgent'),
(3,'FO','Food/Dining'),
(4,'FF','Family/Friend'),
(5,'AP','Appointment'),
(6,'SP','Special event'),
(7,'PU','Public event'),
(8,'SH','Shopping'),
(9,'TA','Travel'),
(10,'PE','Personal'),
(11,'FI','Financial'),
(12,'HO','Home & Garden'),
(13,'AU','Automotive'),
(14,'ED','Education'),
(15,'BU','Business');

--
-- Data for table `item`
--
INSERT INTO `to-do`.`item` VALUES 
(1,'Personal trainer','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultrices efficitur neque. Cras accumsan aliquet sapien eget pharetra. Suspendisse venenatis, sapien at suscipit bibendum, ex urna semper turpis, nec suscipit mauris libero vitae nibh. Sed id maximus odio. ', 'Concept', now(), now() ,1),
(2,'Repetition','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultrices efficitur neque. Cras accumsan aliquet ', 'Open', now(), now() ,2),
(3,'Dinner with Friends','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus', 'Open', now(), now() ,3),
(4,'Hospital','Tur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Concept', now(), now() ,4),
(5,'Birthday grandma Will','Xonsectetur adipiscing elit. Fusce sem lacus, feugiat et ligula vel', 'Close', now(), now() ,1),
(6,'Christmas','Lorem ipsum dolor sit amet, consectetur adipiscing Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultrices efficitur neque. Cras accumsan aliquet ', 'Close', now(), now() ,2),
(7,'Free festival','Quisque pharetra ac nibh eget hendrerit. Duis tristique quis augue ut aliquet. Nullam quis semper felis. Suspendisse finibus mattis porta. Quisque et consectetur ex. Morbi hendrerit augue dui, sed elementum velit accumsan eget. Curabitur sit amet mi semper diam molestie vehicula vel ut justo.', 'Concept', now(), now() ,3),
(8,'Shopping mall','Curabitur viverra id ante ac rutrum. Proin velit lorem, ultrices nec bibendum quis, dictum vel massa. Pellentesque consectetur ultricies ornare. Vestibulum hendrerit metus at diam lacinia porttitor. Nam varius massa convallis nunc faucibus vehicula. In ac nisl fermentum, porttitor nisl vitae, hendrerit elit.', 'Open', now(), now() ,4),
(9,'Paris','Xonsectetur adipiscing elit Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Close', now(), now() ,1),
(10,'Manicure','Quisque pharetra ac nibh eget Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Open', now(), now() ,2),
(11,'Call the CCT bank','Fusce sem lacus, feugiat et ligula vel Xonsectetur adipiscing elit Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Open', now(), now() ,3),
(12,'Painting the barn','Consectetur adipiscing elit. Fusce Xonsectetur adipiscing elit Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Close', now(), now() ,4),
(13,'Changing tires',' Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Concept', now(), now() ,1),
(14,'Learn to program','Curabitur viverra id ante ac rutrum. Proin velit Consectetur adipiscing elit. Fusce Xonsectetur adipiscing elit Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit', 'Open', now(), now() ,2),
(15,'Create company website','Xonsectetur adipiscing elit. Fusce sem lacus', 'Close', now(), now() ,3),
(16,'Cycling 50 km','Pellentesque iaculis dictum est vitae semper.', 'Concept', now(), now() ,4),
(17,'Call Shabu to make a reservation','Pellentesque iaculis dictum est vitae semper.', 'Open', now(), now() ,1),
(18,'Birthday party Steve','Pellentesque iaculis dictum est vitae semperTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Close', now(), now() ,2),
(19,'Dentist','Pellentesque iaculis dictum est vitae semperXonsectetur adipiscing elit Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Close', now(), now() ,3),
(20,'Discount at Levis','Pellentesque iaculis dictum est vitae semper. ', 'Close', now(), now() ,4),
(21,'Clean the garden','Fusce sem lacus Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'Concept', now(), now() ,1),
(22,'Amsterdam','Pellentesque iaculis dictum est vitae semper.Consectetur adipiscing elit. Fusce Xonsectetur adipiscing elit Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit', 'Close', now(), now() ,2),
(23,'Market at the river','Pellentesque iaculis dictum est vitae semper.', 'Concept', now(), now() ,3),
(24,'Easter with family in law','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultrices efficitur neque. Cras accumsan aliquet sapien eget pharetra. Suspendisse venenatis, sapien at suscipit bibendum, ex urna semper turpis, nec suscipit mauris libero vitae nibh. Sed id maximus odio.', 'Open', now(), now() ,4),
(25,'to the cinema - Titanic','Duis tristique quis augue ut aliquet', 'Close', now(), now() ,1),
(26,'Working out with Jimmy','Fusce sem lacus Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'Concept', now(), now() ,2),
(27,'File a tax return','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultrices efficitur neque. Cras accumsan aliquet sapien eget pharetra. Suspendisse venenatis, sapien at suscipit bibendum, ex urna semper turpis, nec suscipit mauris libero vitae nibh. Sed id maximus odio.', 'Open', now(), now() ,3),
(28,'Hospital','Pellentesque iaculis dictum est vitae semper.Tur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Concept', now(), now() ,4),
(29,'Night out','Tur adipiscing elit. Fusce sem lacus Proin velit lorem, ultrices nec bibendum quisTur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Close', now(), now() ,1),
(30,'Halloween','Pellentesque iaculis dictum est vitae semper.Tur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultri', 'Open', now(), now() ,2),
(31,'Festival Tomorrow Land','Duis tristique quis augue ut aliquet', 'Close', now(), now() ,3),
(32,'Fixing oil leak','Tur adipiscing elit. Fusce sem lacus Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce sem lacus, feugiat et ligula vel, ultrices efficitur neque. Cras accumsan aliquet ', 'Open', now(), now() ,4);


--
-- Data for table `item_category`
--
INSERT INTO `to-do`.`item_category` VALUES 
(1,1,1),
(2,2,2),
(3,3,3),
(4,4,4),
(5,5,5),
(6,6,6),
(7,7,7),
(8,8,8),
(9,9,9),
(10,10,10),
(11,11,11),
(12,12,12),
(13,13,13),
(14,14,14),
(15,15,15),
(16,16,1),
(17,17,2),
(18,18,3),
(19,19,4),
(20,20,5),
(21,21,6),
(22,22,7),
(23,23,8),
(24,24,9),
(25,25,10),
(26,26,11),
(27,27,12),
(28,28,13),
(29,29,14),
(30,30,15),
(31,1,3),
(32,2,4),
(33,3,5),
(34,4,6),
(35,5,7),
(36,6,8),
(37,7,9),
(38,8,10),
(39,9,11),
(40,10,12),
(41,11,13),
(42,12,14),
(43,13,15),
(44,14,1),
(45,15,2),
(46,16,3),
(47,17,4),
(48,18,5),
(49,19,6),
(50,20,7),
(51,21,8),
(52,22,9),
(53,23,10),
(54,24,11),
(55,25,12),
(56,26,13),
(57,27,14),
(58,28,15),
(59,29,1),
(60,30,1),
(61,31,2),
(62,32,3),
(63,31,1),
(64,32,2);