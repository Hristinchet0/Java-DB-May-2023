/*Part II – Queries for Geography Database*/
USE geography;

/*10. Countries Holding 'A'*/
SELECT country_name, iso_code FROM countries
WHERE country_name LIKE '%A%A%A%'
ORDER BY iso_code;

/*11. Mix of Peak and River Names*/
SELECT peak_name, river_name,
CONCAT(LOWER(peak_name), SUBSTRING(LOWER(river_name), 2)) AS `mix` 
FROM peaks, rivers
WHERE RIGHT(peak_name, 1) = LEFT(river_name, 1)
ORDER BY mix;



