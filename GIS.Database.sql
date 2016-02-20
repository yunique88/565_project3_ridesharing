CREATE DATABASE IF NOT EXISTS GISDatabase;

USE gisdatabase;

CREATE TABLE IF NOT EXISTS GISDatabase.`UserInfo` (
  `UserName` VARCHAR(45) NOT NULL COMMENT '',
  `Password` VARCHAR(45) NULL COMMENT '',
  `PhoneNo` VARCHAR(20) NULL COMMENT '',
  `HomeLocation` POINT NULL COMMENT '',
  `Destination` POINT NULL COMMENT '',
  PRIMARY KEY (`UserName`)  COMMENT '')
ENGINE = InnoDB

CREATE TABLE `UserParaHistory` (
  `id` INT NOT NULL,
  `userName` varchar(45) NOT NULL,
  `startTime` datetime DEFAULT NULL,
  `startTimeMargin` int(11) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `endTimeMargin` int(11) DEFAULT NULL,
  `departure` point DEFAULT NULL,
  `departureMargin` int(11) DEFAULT NULL,
  `destination` point DEFAULT NULL,
  `destinationMargin` int(11) DEFAULT NULL,
  `departureDate` date DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `Username_idx` (`UserName` ASC),
  CONSTRAINT `Username`
    FOREIGN KEY (`UserName`)
    REFERENCES GISDatabase.`UserInfo` (`UserName`)
    ON DELETE NO ACTION
) ENGINE=InnoDB 


CREATE TABLE `UserTraces` (
  `TraceId` int NOT NULL COMMENT '',
  `userName` varchar(45) NOT NULL,
   `Coords` LINESTRING NULL COMMENT '',
   `Time` LINESTRING NULL COMMENT '',
  PRIMARY KEY (`TraceId`),
  INDEX `Username_idx` (`UserName` ASC),
  CONSTRAINT `Username1`
    FOREIGN KEY (`UserName`)
    REFERENCES GISDatabase.`UserInfo` (`UserName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
) ENGINE=InnoDB 

#Tables for clustering Alg
CREATE TABLE IF NOT EXISTS `gisdatabase`.`DBScanTemp` (
  `LineName` INT NOT NULL AUTO_INCREMENT,
  `Coords` LINESTRING NULL,
  `Visited` TINYINT(1) NULL DEFAULT 0,
  `cluster` INT NULL,
  PRIMARY KEY (`LineName`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `gisdatabase`.`NeighborPts` (
  `LineName` INT NOT NULL AUTO_INCREMENT,
  `Coords` LINESTRING NULL,
  `Visited` TINYINT(1) NULL DEFAULT 0,
  `cluster` INT NULL,
  PRIMARY KEY (`LineName`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `gisdatabase`.`NeighborPtsPrime` (
  `LineName` INT NOT NULL AUTO_INCREMENT,
  `Coords` LINESTRING NULL,
  `Visited` TINYINT(1) NULL DEFAULT 0,
  `cluster` INT NULL,
  PRIMARY KEY (`LineName`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `gisdatabase`.`TEMPTABLE` (
  `Coords` POINT NULL)
ENGINE = InnoDB


DELIMITER $$
USE `gisdatabase`$$
CREATE Function `computeEncodingCost` (ls linestring,startIndex INT,endIndex INT)
Returns Double
BEGIN
DECLARE clusterComponentStart, clusterComponentEnd Point;
DECLARE lon1, lat1, lon2, lat2, encodingcost, perpendicularDist, angularDist double;
SET clusterComponentStart=Pointn(ls,startIndex);
SET Lon1=x(clusterComponentStart);
SET Lat1=y(clusterComponentStart);
SET clusterComponentEnd=Pointn(ls,endIndex);
SET Lon2=x(clusterComponentEnd);
SET Lat2=y(clusterComponentEnd);
SET encodingcost=0;

REPEAT
	SET perpendicularDist = 6371 * 2 * ASIN(SQRT(
            POWER(SIN((Lat1 - abs(Lat2)) * pi()/180 / 2),
            2) + COS(Lat1 * pi()/180 ) * COS(abs(Lat2) *
            pi()/180) * POWER(SIN((Lon1 - Lon2) *
            pi()/180 / 2), 2) ));
            
	SET angularDist = ATAN2(COS(Lat1)*SIN(Lat2)
					-SIN(Lat1)*COS(Lat2)*COS(Lon2-
                    Lon1), SIN(Lon2-Lon1
                    *COS(Lat2)));

	if(perpendicularDist<1.0) then
		SET perpendicularDist=1.0;
	end if;

	if(angularDist<1.0) then
		SET angularDist=1.0;
	end if;

	SET encodingcost=encodingcost+(CEIL(LOG2(perpendicularDistance))+CEIL(LOG2(angularDistance)));

Until (endIndex-startIndex=NumPoints(ls)-1) END REPEAT;

	RETURN encodingcost;
END$$

DELIMITER ;


DELIMITER $$
USE `gisdatabase`$$
CREATE FUNCTION `computeModelCost` (ls linestring, startIndex INT, endIndex INT)
RETURNS Double
BEGIN
DECLARE Point1, Point2 Point;
DECLARE distance, xPoint1, xPoint2, yPoint1, yPoint2 Double;
SET Point1=Pointn(ls,startIndex);
SET Point2=Pointn(ls,endIndex);
SET distance=0;
SET xPoint1=x(Point1);
SET yPoint1=y(Point1);
SET xPoint2=x(Point2);
SET yPoint2=y(Point2);

SET distanceX=Pow(Abs(xPoint1-xPoint2),2);
SET distanceY=Pow(Abs(yPoint2-yPoint1),2);

SET distance=Pow((distanceX+distanceY),0.5);

RETURN modelCost=CEIL(LOG2(distance));

END$$

DELIMITER ;


DELIMITER $$
USE `gisdatabase`$$
CREATE PROCEDURE `DBScan` (e INT, minPts INT)
BEGIN
	SET C = 0;
    INSERT INTO DBScanTemp (Coords) Select Coords from UserTraces;
    REPEAT
		SET p = (SELECT coords from DBScanTemp where visited = false limit 1);
        UPDATE DBScanTemp set visited = true; 
        INSERT INTO NeighborPts (SELECT buffer(pointn(p,1), e) from DBScanTemp where visited = false);
        if ((SELECT Count(Coords) from NeighborPts) >= minPts) then
			SET C = C + 1;
            Call ExpandCluster(C, P, e, minPts);
		end if;
        TRUNCATE NeighborPts;
        TRUNCATE NeighborPtsPrime;
    UNTIL ROW_COUNT() = 0 END REPEAT;
END$$

DELIMITER ;


DELIMITER $$
USE `gisdatabase`$$
CREATE PROCEDURE `ExpandCluster` (C INT, P LINESTRING, e INT, minPts INT)
BEGIN
	UPDATE DBScanTemp set cluster = C where Coords = P;
        REPEAT
		SET p = (SELECT coords from NeighborPts where visited = false limit 1);
        UPDATE NeighborPts SET visited = true where coords = p;
        INSERT INTO NeighborPtsPrime (SELECT buffer(pointn(p,1), e) from DBScanTemp where visited = false);
        if ((SELECT Count(Coords) from NeighborPtsPrime) >= minPts) then
			INSERT INTO NeighborPts (SELECT * from NeighborPtsPrime);
		end if;
        if ((SELECT cluster from DBScanTemp where coords = p) = 0) then
			UPDATE DBScanTemp set cluster = C where coords = p;
		end if;
    UNTIL ROW_COUNT() = 0 END REPEAT;
END$$

DELIMITER ;


DELIMITER $$
USE `gisdatabase`$$
CREATE PROCEDURE `FindOptimal` (ls linestring)
BEGIN
SET numofPoints= NumPoints(ls);
SET startIndex=0;
SET length=1;

SET startpoint=Pointn(ls,1);
Insert INTO TEMPTABLE VALUES (startpoint);

REPEAT
	SET fullpartition=0,partialpartition=0;
REPEAT
	SET fullpartition=fullpartition+computeCostModel(ls,startIndex+length-1, startIndex+length);
	SET partialpartition = computeModelCost(ls, startIndex, startIndex+length) +
						computeEncodingCost(ls, startIndex, startIndex + length);
                        
	if(fullPartitionMDLCost + MDL_COST_ADWANTAGE < partialPartitionMDLCost) then
		Insert into TEMPTABLE values (pointn(ls, startIndex + length -1)); 
					
		SET startIndex = startIndex + length -1;
	END IF;
	UNTIL length=0 END REPEAT;
					

UNTIL (startindex+length>=numofPoints) END REPEAT;
Insert into TEMPTABLE values (pointn(ls, numofPoints)); 

END;$$

DELIMITER ;


DELIMITER $$
USE `gisdatabase`$$
CREATE PROCEDURE `getMatch` (TraceID INT)
BEGIN
	# call findoptimal for each trace in database
    set n = (Select COUNT(*) from USERTraces);
    set i = 1;
    repeat
		set id = (select TraceID from USERTraces LIMIT 1, i);
		set ls = (select coords from USERTraces where TraceID = id LIMIT 1, i);
		call FindOptimal(ls, id);
		set i = i + 1;
    until i > n end repeat;
    
    Call DBScan(e, minPts);
    
    # check the cluster for parameters
END$$

DELIMITER ;

