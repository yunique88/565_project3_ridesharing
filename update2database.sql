DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `getPoints`(ls linestring, ls1 linestring, e double) RETURNS tinyint(1)
BEGIN
	declare dis, dis1 double; 
    set dis = disPt2Pt(pointn(ls,1), pointn(ls1,1));
    set dis1 = disPt2Pt(pointn(ls,2), pointn(ls1,2));
    if (greatest(dis,dis1) < e) then
		return 1;
	end if;
    return 0;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DBScan`(e INT, minPts INT, startMargin INT, endMargin INT)
BEGIN
	Declare C,n int;
    Declare p, t linestring;
	SET C = 0;
    set n = 1;
    truncate dbscantemp;
    INSERT INTO DBScanTemp (LineName, Coords, `Time`) Select TraceID, Coords, `time` from coordstable;
    REPEAT
		SELECT coords, `time`from DBScanTemp where visited = false limit 1, n INTO p, t;
        UPDATE DBScanTemp set visited = true where coords = p; 
        select *from dbscantemp;
        INSERT INTO NeighborPts (SELECT * from DBScanTemp where visited = false AND getPoints(p, coords, e)
			AND NOT id IN(select id from NeighborPts));
            select count(*) from NeighborPts;
        if ((SELECT Count(Coords) from NeighborPts) >= minPts) then
			SET C = C + 1;
            Call ExpandCluster(C, P, e, minPts, startMargin, endMargin);
		end if;
        set n = n + 1;
        TRUNCATE NeighborPts;
        TRUNCATE NeighborPtsPrime;
    UNTIL n > (Select count(*) from dbscantemp) END REPEAT;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ExpandCluster`(C INT, P LINESTRING, e INT, minPts INT, startMargin INT, endMargin INT)
BEGIN
	Declare p1 linestring;
    declare idN int;
	UPDATE DBScanTemp set cluster = C where Coords = P;
        REPEAT
		(SELECT id, coords from NeighborPts where visited = false limit 1 INTO idN, p1);
        UPDATE NeighborPts SET visited = true where coords = p1;
        INSERT INTO NeighborPtsPrime (SELECT * from DBScanTemp where visited = false AND getPoints(p1, coords, e)
						AND NOT id IN(select id from NeighborPtsPrime));
        if ((SELECT Count(Coords) from NeighborPtsPrime) >= minPts) then
			INSERT INTO NeighborPts (SELECT * from NeighborPtsPrime where NOT id IN(select id from NeighborPts));
            # AND correctTimeMargins(y(pointn(`time`,1)), y(pointn(t,1)), startMargin) 
            #AND correctTimeMargins(y(pointn(`time`,2)), y(pointn(t,2)), endMargin));
		end if;
        if ((SELECT cluster from DBScanTemp where coords = p1 limit 1) = 0) then
			UPDATE DBScanTemp set cluster = C, visited = 1 where id = idN;
		end if;
    UNTIL ROW_COUNT() = 0 END REPEAT;
END$$
DELIMITER ;
