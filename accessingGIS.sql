USE gisdatabase;

INSERT INTO userinfo VALUES('Jon', 'pass', POINT(1,2), POINT(5,10));
INSERT INTO usertraces VALUES('T1', 'Jon', ST_GeomFromText('linestring(2 4, 2 3, 2 5, 5 15)'));



INSERT INTO mydb.`table` VALUES (1, POINT(1,2));

SELECT ST_X(HomeLocation), ST_Y(HomeLocation) from userinfo;
SELECT NumPoints(Coords), astext(pointn(coords, 2)) from usertraces;
