/*Part III – Queries for Diablo Database*/

/*12. Games From 2011 and 2012 Year*/
SELECT name, date_format(start, '%Y-%m-%d') AS 'start'
FROM games
WHERE YEAR(start) IN (2011,2012)
ORDER BY start, name
LIMIT 50;

/*13. User Email Providers*/
SELECT `user_name`, substring(`email`, locate('@', email) + 1) AS 'Email Provider' 
FROM `users` 
ORDER BY `Email Provider`, `user_name`;

/*13** User email name, without provider*/
SELECT `user_name`, substring(`email`, 1,  locate('@', email) -1) AS 'Email Name' 
FROM `users` 
ORDER BY `Email Provider`, `user_name`;

/*14. Get Users with IP Address Like Pattern "___.1%.%.___". */
SELECT user_name, ip_address FROM users
WHERE ip_address LIKE "___.1%.%.___"
ORDER BY user_name ASC;

/*15. Show All Games with Duration 
Parts of the day should be Morning (start time is >= 0 and < 12), 
Afternoon (start time is >= 12 and < 18), Evening (start time is >= 18 and < 24).... */
SELECT name AS 'game', 
CASE
WHEN HOUR(start) >=0 AND HOUR(start) < 12 THEN "Morning"
WHEN HOUR(start) >=12 AND HOUR(start) < 18 THEN "Afternoon"
ELSE "Evening" 
END 
AS 'Part of the Day',
CASE
WHEN duration <=3 THEN 	"Extra Short"
WHEN duration <= 6 THEN "Short"
WHEN duration <= 10 THEN "Long"
ELSE "Extra Long"
END
AS 'Duration'
FROM games;








