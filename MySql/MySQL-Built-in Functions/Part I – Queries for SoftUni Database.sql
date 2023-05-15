/*Part I – Queries for SoftUni Database*/
USE soft_uni;

/*01. Find Names of All Employees by First Name*/
SELECT first_name, last_name FROM employees
WHERE first_name LIKE "Sa%"
ORDER BY employee_id;

/*02. Find Names of All Employees by Last Name*/
SELECT first_name, last_name FROM employees
WHERE last_name LIKE "%ei%"
ORDER BY employee_id;

/*03. Find First Names of All Employess*/
SELECT first_name FROM employees
WHERE department_id IN (3, 10) /*WHERE (department_id = 3 OR department_id = 10)*/
AND
YEAR (hire_date) BETWEEN 1995 AND 2005
ORDER BY employee_id;

/*04. Find All Employees Except Engineers*/
SELECT first_name, last_name FROM employees
WHERE job_title NOT LIKE '%engineer%'
ORDER BY employee_id;

/*05. Find Towns with Name Length*/
SELECT name FROM towns
WHERE char_length(name) = 5 OR char_length(name) = 6
ORDER BY name ASC;

/*06. Find Towns Starting With*/
SELECT * FROM towns
WHERE name LIKE 'M%' 
	OR name LIKE 'K%' 
    OR name LIKE 'B%'
    OR name LIKE 'E%'
ORDER BY name ASC;

/*07. Find Towns Not Starting With letters R, B or D */
SELECT * FROM towns
WHERE name NOT LIKE 'R%' 
	AND name NOT LIKE 'B%' 
    AND name NOT LIKE 'D%'
ORDER BY name ASC;

/*08. Create View Employees Hired After*/
CREATE VIEW v_employees_hired_after_2000 AS
SELECT first_name, last_name FROM employees
WHERE YEAR(hire_date) > 2000;

SELECT * FROM v_employees_hired_after_2000;

/*09. Length of Last Name*/
SELECT first_name, last_name FROM employees
WHERE char_length(last_name) = 5;





