CREATE DATABASE IF NOT EXISTS GISDatabase;

USE gisdatabase;

CREATE TABLE IF NOT EXISTS GISDatabase.`UserInfo` (
  `UserName` VARCHAR(45) NOT NULL COMMENT '',
  `Password` VARCHAR(45) NULL COMMENT '',
  `HomeLocation` POINT NULL COMMENT '', -- stored as lat/long
  `Destination` POINT NULL COMMENT '', -- stored as lat/long
  PRIMARY KEY (`UserName`)  COMMENT '')
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS GISDatabase.`History` (
  `id` INT NOT NULL,
  `UserName` VARCHAR(45) NULL, -- User can have > 1 Historical Queries
  `StartTime` DATETIME NULL,
  `EndTIme` DATETIME NULL,
  `TimeMargin` INT NULL,
  `Distance` INT NULL,
  `DistanceMargin` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `Username_idx` (`UserName` ASC),
  CONSTRAINT `Username`
    FOREIGN KEY (`UserName`)
    REFERENCES GISDatabase.`UserInfo` (`UserName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- Table to store users GPS Traces (Routes)
CREATE TABLE IF NOT EXISTS GISDatabase.`UserTraces` (
  `TraceName` VARCHAR(45) NOT NULL, -- User can have > 1 Trace
  `UserName` VARCHAR(45) NULL,
  `Coords` LINESTRING NULL, -- Line representing route
  `TIme` LINESTRING NULL, -- Line representing the time for the route
  PRIMARY KEY (`TraceName`),
  INDEX `Username_idx` (`UserName` ASC),
  CONSTRAINT `Username1`
    FOREIGN KEY (`UserName`)
    REFERENCES GISDatabase.`UserInfo` (`UserName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB