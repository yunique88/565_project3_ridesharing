#CALL `gisdatabase`.`getMatch`(<{matchID INT}>, <{startTime double}>, <{startTimeMargin double}>, <{endTime double}>, <{endTimeMargin double}>, <{departure Point}>, <{departMargin double}>, <{destination Point}>, <{destinationMargin double}>, <{departureDate double}>);

#need to talk to bei about how the parameters will be passed, ex. time in string or double, margin in mins, etc. 
CALL `gisdatabase`.`getMatch`(1, .7, 30, .9, 30, pointn(@ls,1), 30, pointn(@ls, numpoints(@ls)), 30, 3.00);

select if(@clusterN <> 0, userName, 'No Match') matches from finalmatch group by matches; # found a match if cluster != 0

