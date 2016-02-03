CREATE DATABASE IF NOT EXISTS GISDatabase;

USE gisdatabase;

CREATE TABLE IF NOT EXISTS GISDatabase.`UserInfo` (
  `UserName` VARCHAR(45) NOT NULL COMMENT '',
  `Password` VARCHAR(45) NULL COMMENT '',
  `HomeLocation` POINT NULL COMMENT '',
  `Destination` POINT NULL COMMENT '',
  PRIMARY KEY (`UserName`)  COMMENT '')
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS GISDatabase.`History` (
  `id` INT NOT NULL COMMENT '',
  `UserName` VARCHAR(45) NULL COMMENT '',
  `Parameters` VARCHAR(45) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `Username_idx` (`UserName` ASC)  COMMENT '',
  CONSTRAINT `Username`
    FOREIGN KEY (`UserName`)
    REFERENCES `GISDatabase`.`UserInfo` (`UserName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB


CREATE TABLE IF NOT EXISTS GISDatabase.`UserTraces` (
  `TraceName` VARCHAR(45) NOT NULL COMMENT '',
  `UserName` VARCHAR(45) NULL COMMENT '',
  `Coords` LINESTRING NULL COMMENT '',
  PRIMARY KEY (`TraceName`)  COMMENT '',
  INDEX `Username_idx` (`UserName` ASC)  COMMENT '',
  CONSTRAINT `Username1`
    FOREIGN KEY (`UserName`)
    REFERENCES `GISDatabase`.`UserInfo` (`UserName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB