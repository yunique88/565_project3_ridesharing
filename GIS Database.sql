CREATE DATABASE GIS;
use GIS;

CREATE TABLE `userinfo` (
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `PhoneNo` varchar(20) DEFAULT NULL,
  `HomeLocation` point DEFAULT NULL,
  `Destination` point DEFAULT NULL,
  PRIMARY KEY (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `userparahistory` (
  `id` int(11) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `startTime` time(6) DEFAULT NULL,
  `startTimeMargin` int(11) DEFAULT NULL,
  `endTime` time(6) DEFAULT NULL,
  `endTimeMargin` int(11) DEFAULT NULL,
  `departure` point DEFAULT NULL,
  `departureMargin` int(11) DEFAULT NULL,
  `destination` point DEFAULT NULL,
  `destinationMargin` int(11) DEFAULT NULL,
  `departureDate` date DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Username_idx` (`userName`),
  CONSTRAINT `Username` FOREIGN KEY (`userName`) REFERENCES `userinfo` (`UserName`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `usertraces` (
  `TraceId` int(11) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `Coords` linestring DEFAULT NULL,
  `Time` linestring DEFAULT NULL,
  PRIMARY KEY (`TraceId`),
  KEY `Username_idx` (`userName`),
  CONSTRAINT `Username1` FOREIGN KEY (`userName`) REFERENCES `userinfo` (`UserName`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#******************** 2 test datasets

set @ls = ST_GeomFromText('LINESTRING(872.9 354.9, 901.9 395.0, 904.6 414.0, 903.8 400.5, 896.9 400.5, 901.7 414.2, 902.2 413.8, 908.4 433.5, 911.3 413.3, 909.6 425.7, 907.0 428.8, 911.1 424.7, 898.4 442.0, 903.4 438.8, 908.0 448.4, 897.5 445.7, 895.0 446.7, 889.9 455.5, 868.4 504.7, 849.9 473.4, 846.9 445.3, 845.9 451.4, 845.8 456.3, 877.0 456.6, 883.1 465.8, 834.0 481.5, 855.5 363.4, 858.8 365.4, 866.6 359.6, 869.3 360.7, 871.9 364.4, 874.5 375.0, 895.5 388.8, 928.7 384.6, 940.8 363.4, 936.9 353.2, 934.8 382.7, 909.7 394.4, 894.4 361.3, 894.0 366.9, 868.4 397.8, 855.4 392.7, 869.0 375.2, 884.6 412.8, 858.8 456.8, 862.5 460.9, 866.5 452.6, 861.3 449.8, 878.5 484.3, 866.7 466.8, 866.9 456.6, 882.8 439.8, 884.1 435.5, 896.3 419.8, 908.0 430.2, 909.9 421.2, 933.7 406.0, 925.9 397.1, 942.3 348.3, 945.2 329.8, 926.3 331.0, 935.6 334.5, 927.1 319.5, 930.3 335.0, 930.8 329.9, 941.3 338.5, 933.8 358.3, 891.8 345.0, 893.8 348.0, 877.9 343.7, 871.3 336.3, 868.0 343.8, 870.4 342.7, 869.3 345.6, 876.2 356.3, 863.8 322.1, 860.5 330.2, 886.6 324.7, 893.6 352.2, 893.2 359.1, 888.6 351.3, 890.8 355.3, 879.1 367.3, 840.7 378.7)');
set @ls1 = ST_GeomFromText('LINESTRING(868.4 361.1, 879.6 386.8, 919.3 400.2, 904.3 408.1, 884.9 455.3, 903.8 446.8, 902.2 446.9, 882.6 440.3, 882.7 445.4, 868.8 442.0, 859.8 436.6, 876.0 419.8, 858.9 397.7, 856.6 386.3, 860.9 398.0, 883.3 362.1, 887.0 342.5, 906.2 361.5, 870.0 383.1, 880.8 407.8, 885.2 445.6, 897.8 442.5, 855.3 375.3, 853.9 398.8, 839.1 386.3, 850.8 403.3, 855.6 384.3, 841.4 364.0, 855.0 353.3, 876.6 388.7, 885.5 450.7, 889.4 432.2, 912.3 387.1, 913.0 332.3, 910.8 339.4, 911.8 335.2, 913.5 327.6, 912.5 348.0, 897.8 338.9, 900.8 336.7, 898.4 331.0, 883.3 321.4, 866.9 336.0, 867.8 333.8, 870.1 323.7, 895.1 349.1, 885.9 345.9, 880.2 350.7, 859.7 342.8, 852.2 345.3, 907.3 362.7, 898.0 336.1, 913.2 343.0, 898.4 337.0, 861.6 364.3, 865.5 389.0, 934.8 380.8, 864.1 311.7, 853.5 350.8, 862.7 341.2, 872.0 352.5, 868.6 318.0, 877.8 318.7, 900.2 336.3, 890.3 338.3, 871.0 329.2, 838.6 353.8, 837.8 354.2, 850.8 345.7, 869.8 381.8, 903.1 438.9, 898.5 435.9, 894.3 427.1, 849.2 400.3, 868.2 342.0, 870.2 346.9, 886.7 333.2, 912.9 355.9, 931.7 368.5, 894.5 343.2, 877.4 341.2, 878.8 341.0, 876.3 338.5, 874.8 340.7)');

INSERT INTO userinfo VALUES('jon', 'pass', '2539704185', pointn(@ls, 1), pointn(@ls, numpoints(@ls)));
INSERT INTO userinfo VALUES('bob', 'pass', '2539704185', pointn(@ls1, 1), pointn(@ls1, numpoints(@ls1)));
INSERT INTO userinfo VALUES('temp', '', '', pointn(@ls1, 1), pointn(@ls1, numpoints(@ls1)));

INSERT INTO usertraces VALUES(1, "jon", @ls, @ls);# second ls/ls1 is just a stand in to test the algorithm
INSERT INTO usertraces VALUES(2, "bob", @ls1, @ls1);

#******************** getMatch() procedure
#CALL `gis`.`getMatchOnLineString`(<{LatLong linestring}>, <{timeString linestring}>, <{startTime time}>, <{startTimeMargin double}>, <{endTime time}>, <{endTimeMargin double}>, <{departure Point}>, <{departMargin double}>, <{destination Point}>, <{destinationMargin double}>, <{departureDate double}>);
#CALL `gis`.`getMatchOnTraceID`(<{matchID INT}>, <{startTime time}>, <{startTimeMargin double}>, <{endTime time}>, <{endTimeMargin double}>, <{departure Point}>, <{departMargin double}>, <{destination Point}>, <{destinationMargin double}>, <{departureDate double}>);


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getMatchOnLineString`(LatLong linestring, timeString linestring, startTime time, startTimeMargin double, endTime time, endTimeMargin double,
	departure Point, departMargin double, destination Point, destinationMargin double, departureDate double)
BEGIN
declare n, i, id, clusterN, matchID int;
declare ls, t linestring;
	INSERT INTO usertraces (userName, Coords, `Time`) values ('temp',LatLong, timeString);
    Select TraceId from usertraces where userName = 'temp' AND Coords = LatLong AND `Time` = timeString INTO matchID;
    Select COUNT(*) from USERTraces into n;
    truncate coordstable;
    truncate finalmatch;

    set i = 0;
    repeat
		(select TraceId, coords, `time` from USERTraces limit i, 1 INTO id, ls, t);
		call FindOptimal(ls, id, t);
		set i = i + 1;
    until i > n-1 end repeat;
    Call DBScan(greatest(departMargin, destinationMargin), 2, startTimeMargin * 60, endTimeMargin * 60); #e, minPts);
    
    select cluster from dbscantemp where LineName = matchID limit 1 into clusterN;
    INSERT INTO finalmatch SELECT TraceId, userName, Coords, `time`, clusterN from usertraces where TraceID IN (select LineName from dbscantemp where cluster = clusterN);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getMatchOnTraceID`(matchID INT, startTime time, startTimeMargin double, endTime time, endTimeMargin double,
	departure Point, departMargin double, destination Point, destinationMargin double, departureDate double)
BEGIN
declare n, i, id, clusterN int;
declare ls, t linestring;
    Select COUNT(*) from USERTraces into n;
    truncate coordstable;
    truncate finalmatch;

    set i = 0;
    repeat
		(select TraceId, coords, `time` from USERTraces limit i, 1 INTO id, ls, t);
		call FindOptimal(ls, id, t);
		set i = i + 1;
    until i > n-1 end repeat;
    Call DBScan(greatest(departMargin, destinationMargin), 2, startTimeMargin * 60, endTimeMargin * 60); #e, minPts);
    
    select cluster from dbscantemp where LineName = matchID limit 1 into clusterN;
    INSERT INTO finalmatch SELECT TraceId, userName, Coords, `time`, clusterN from usertraces where TraceID IN (select LineName from dbscantemp where cluster = clusterN);
END$$
DELIMITER ;


#******************** tables for Algorithm

CREATE TABLE `coordstable` (
  `TraceID` int(11) DEFAULT NULL,
  `Coords` linestring DEFAULT NULL,
  `time` linestring DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `dbscantemp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `LineName` int(11) DEFAULT NULL,
  `Coords` linestring DEFAULT NULL,
  `Time` linestring DEFAULT NULL,
  `Visited` tinyint(1) DEFAULT '0',
  `cluster` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

CREATE TABLE `debug` (
  `a` double DEFAULT NULL,
  `b` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `finalmatch` (
  `TraceId` int(11) NOT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `Coords` linestring DEFAULT NULL,
  `Time` linestring DEFAULT NULL,
  `cluster` int(11) DEFAULT NULL,
  PRIMARY KEY (`TraceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `neighborpts` (
  `id` int(11) NOT NULL,
  `LineName` int(11) DEFAULT NULL,
  `Coords` linestring DEFAULT NULL,
  `Time` linestring DEFAULT NULL,
  `Visited` tinyint(1) DEFAULT '0',
  `cluster` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `neighborptsprime` (
  `id` int(11) NOT NULL,
  `LineName` int(11) DEFAULT NULL,
  `Coords` linestring DEFAULT NULL,
  `Time` linestring DEFAULT NULL,
  `Visited` tinyint(1) DEFAULT '0',
  `cluster` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# *************************** cluster algorithm prodecures/functions

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `correctTimeMargins`(time1 INT, time2 INT, margin INT) RETURNS tinyint(1)
BEGIN
	return (abs(time1 - time2) <= margin);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DBScan`(e INT, minPts INT, startMargin INT, endMargin INT)
BEGIN
	Declare C int;
    Declare p, t linestring;
	SET C = 0;
    truncate dbscantemp;
    INSERT INTO DBScanTemp (LineName, Coords, `Time`) Select TraceID, Coords, `time` from coordstable;
    REPEAT
		SELECT coords, `time`from DBScanTemp where visited = false limit 1 INTO p, t;
        UPDATE DBScanTemp set visited = true; 
        INSERT INTO NeighborPts (SELECT * from DBScanTemp where visited = false AND buffer(pointn(p,1), e)
			AND NOT id IN(select id from NeighborPts) AND correctTimeMargins(y(pointn(`time`,1)), y(pointn(t,1)), startMargin) 
            AND correctTimeMargins(y(pointn(`time`,2)), y(pointn(t,2)), endMargin));
        if ((SELECT Count(Coords) from NeighborPts) >= minPts) then
			SET C = C + 1;
            Call ExpandCluster(C, P, e, minPts, startMargin, endMargin);
		end if;
        TRUNCATE NeighborPts;
        TRUNCATE NeighborPtsPrime;
    UNTIL ROW_COUNT() = 0 END REPEAT;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ExpandCluster`(C INT, P LINESTRING, e INT, minPts INT, startMargin INT, endMargin INT)
BEGIN
	Declare p point;
	UPDATE DBScanTemp set cluster = C where Coords = P;
        REPEAT
		SET p = (SELECT coords from NeighborPts where visited = false limit 1);
        UPDATE NeighborPts SET visited = true where coords = p;
        INSERT INTO NeighborPtsPrime (SELECT buffer(pointn(p,1), e) from DBScanTemp where visited = false);
        if ((SELECT Count(Coords) from NeighborPtsPrime) >= minPts) then
			INSERT INTO NeighborPts (SELECT * from NeighborPtsPrime where NOT id IN(select id from NeighborPts)
             AND correctTimeMargins(y(pointn(`time`,1)), y(pointn(t,1)), startMargin) 
            AND correctTimeMargins(y(pointn(`time`,2)), y(pointn(t,2)), endMargin));
		end if;
        if ((SELECT cluster from DBScanTemp where coords = p) = 0) then
			UPDATE DBScanTemp set cluster = C where coords = p;
		end if;
    UNTIL ROW_COUNT() = 0 END REPEAT;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `FindOptimal`(ls linestring, id INT, t linestring)
BEGIN
declare numofPoints, startIndex, length, fullpartition, partialpartition int;
declare startpoint, parpoint, st, et point;
declare parls, part linestring;
SET numofPoints= NumPoints(ls);
SET startIndex=1;
SET length=1;

SET startpoint=Pointn(ls,1);
SET st = pointn(t,1);
#Insert INTO CoordsTable VALUES (startpoint);

REPEAT
	SET fullpartition=0,partialpartition=0;
innerLoop: REPEAT
	SET fullpartition=fullpartition+computeCostModel(ls,startIndex+length-1, startIndex+length);

	SET partialpartition = computeCostModel(ls, startIndex, startIndex+length) +
						computeEncodingCost(ls, startIndex, startIndex+length);
                        
	
   INSERT INTO debug VALUES (startIndex+length, numofpoints);
	if(fullPartition + 25 < partialPartition) then
		#Insert into CoordsTable values (pointn(ls, startIndex + length -1)); 
        set parpoint = pointn(ls, startIndex + length -1);
		SET et = pointn(t,startIndex + length -1);
        set parls = ST_GeomFromText(concat('linestring(',x(startpoint), ' ', y(startpoint), ', ', x(parpoint), ' ', y(parpoint),')'));
        set part = ST_GeomFromText(concat('linestring(',x(st), ' ', y(st), ', ', x(et), ' ', y(et),')'));
        Insert into CoordsTable values (id, parls, part); 
        set startpoint = pointn(ls, startIndex + length -1);
        set st = pointn(t, startIndex + length -1);
		# store in linestrings
		SET startIndex = startIndex + length;
        SET length = 1;
        leave innerLoop;
	END IF;
    SET length = length + 1;
	UNTIL (startIndex+length >= numofPoints) END REPEAT;
					

UNTIL (startindex+length >= numofPoints) END REPEAT;
#Insert into CoordsTable values (pointn(ls, numofPoints)); 
		set parpoint = pointn(ls, startIndex + length -1);
		SET et = pointn(t,startIndex + length -1);
        set parls = ST_GeomFromText(concat('linestring(',x(startpoint), ' ', y(startpoint), ', ', x(parpoint), ' ', y(parpoint),')'));
        set part = ST_GeomFromText(concat('linestring(',x(st), ' ', y(st), ', ', x(et), ' ', y(et),')'));
        Insert into CoordsTable values (id, parls, part);  
        

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `computeAngle`(s1 Point, e1 Point, s2 Point, e2 Point) RETURNS double
BEGIN
	declare vec1x, vec2x, vec1y, vec2y double;
    declare m, cosTheta, sinTheta, vecLen1, vecLen2 double; 
    set vec1x = x(e1) - x(s1);
    set vec2x = x(e2) - x(s2);
    set vec1y = y(e1) - y(s1);
    set vec2y = y(e2) - y(s2);
    
    set vecLen1 = sqrt(pow(vec1x,2) + pow(vec1y,2));
	set vecLen2 = sqrt(pow(vec2x,2) + pow(vec2y,2));
    
    if (vecLen1 = 0 || vecLen2 = 0) then
		return 0;
	end if;
    
    set m = computeInnerProduct(vec1x, vec2x, vec1y, vec2y);
	if (isnull(m)) then
		return 0;
	end if;
    set cosTheta = m / (vecLen1 * vecLen2);
    if (cosTheta > 1.0) then
		set cosTheta = 1;
	end if;
    if (cosTheta < -1.0) then
		set cosTheta = -1;
	end if;

	set sinTheta = sqrt(1 - pow(cosTheta, 2));
    return (vecLen2 * sinTheta);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `computeCostModel`(ls linestring, startIndex INT, endIndex INT) RETURNS double
BEGIN
DECLARE Point1, Point2 Point;
DECLARE distance Double;
SET Point1=Pointn(ls,startIndex);
SET Point2=Pointn(ls,endIndex);
SET distance=disPt2Pt(Point1, Point2);

if (distance < 1) then
	set distance = 1;
end if;

RETURN CEIL(LOG2(distance));

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `computeEncodingCost`(line linestring,startIndex INT,endIndex INT) RETURNS double
BEGIN
DECLARE lon1, lat1, lon2, lat2, encodingcost, perpendicularDist, angularDist double;
DECLARE CS, LS, CE, LE point;
SET encodingcost=0;

SET CS=Pointn(line,startIndex);
SET CE=Pointn(line,endIndex);
    
REPEAT
SET LS=Pointn(line,startIndex);
SET Lon1=abs(x(CS) - x(LS));
SET Lat1=abs(y(CS) - y(LS));
SET LE=Pointn(line,startIndex+1);
SET Lon2=abs(x(CE) - x(LE));
SET Lat2=abs(y(CE) - y(LE));

	#SET perpendicularDist = 6371 * 2 * ASIN(SQRT(
     #       POWER(SIN((Lat1 - abs(Lat2)) * pi()/180 / 2),
      #      2) + COS(Lat1 * pi()/180 ) * COS(abs(Lat2) *
       #     pi()/180) * POWER(SIN((Lon1 - Lon2) *
        #    pi()/180 / 2), 2) ));
	SET perpendicularDist = computePerpendicular(CS, CE, LS, LE);
         
	#SET angularDist = ATAN2(COS(Lat1)*SIN(Lat2)
	#				-SIN(Lat1)*COS(Lat2)*COS(Lon2-
     #               Lon1), SIN(Lon2-Lon1
      #              *COS(Lat2)));
	SET angularDist = computeAngle(CS, CE, LS, LE);
 # INSERT INTO debug VALUES (perpendicularDist, angularDist);
   
	if(perpendicularDist<1.0) then
		SET perpendicularDist=1.0;
	end if;

	if(angularDist<1.0) then
		SET angularDist=1.0;
	end if;

	SET encodingcost=encodingcost+(CEIL(LOG2(perpendicularDist))+CEIL(LOG2(angularDist)));
	SET startIndex = startIndex + 1;
#Until (endIndex-startIndex=NumPoints(ls)-1) END REPEAT;
Until (startIndex >= endIndex) END REPEAT;

	RETURN encodingcost;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `computeInnerProduct`(vec1x double, vec2x double, vec1y double, vec2y double) RETURNS double
BEGIN
    return (vec1x * vec2x) + (vec1y * vec2y);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `computePerpendicular`(s1 Point, e1 Point, s2 Point, e2 Point) RETURNS double
BEGIN
	Declare dis1, dis2 double;
    set dis1 = disFromPt2Line(s1,e1,s2);
    set dis2 = disFromPt2Line(s1,e1,e2);
    if (dis1 = 0 && dis2 = 0) then 
		return 0;
	end if;
    
    return ((pow(dis1,2)+pow(dis2,2))/(dis1 + dis2));
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `disFromPt2Line`(s Point, e Point, p Point) RETURNS double
BEGIN
	declare vec1x, vec2x, vec1y, vec2y double;
    declare m, mx, my, square double; 
    set vec1x = x(p) - x(s);
    set vec2x = x(e) - x(s);
    set vec1y = y(p) - y(s);
    set vec2y = y(e) - y(s);
    
    
    set m = computeInnerProduct(vec1x, vec2x, vec1y, vec2y) / computeInnerProduct(vec2x, vec2x, vec2y, vec2y);
    #if (isnull(m)) then
	#	return 0;
	#end if;
    set mx = x(s) + m*vec2x;
    set my = y(s) + m*vec2y;
     
    set square = pow(mx - x(p), 2) + pow(my - y(p),2);
    return sqrt(square);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `disPt2Pt`(p1 Point, p2 Point) RETURNS double
BEGIN
	declare x, y double;
    set x = pow(x(p1) - x(p2),2);
    set y = pow(y(p1) - y(p2),2);
    return sqrt(x+y);
END$$
DELIMITER ;

