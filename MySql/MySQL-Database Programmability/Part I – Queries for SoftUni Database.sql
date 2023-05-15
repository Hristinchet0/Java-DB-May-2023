#Part I – Queries for SoftUni Database
#01. Employees with Salary Above 35000
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above_35000()
BEGIN
	SELECT first_name, last_name FROM employees
    WHERE salary > 35000
    ORDER BY first_name, last_name, employee_id;
END$$

#02. Employees with Salary Above Number
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above (target_salary DECIMAL(19, 4))
BEGIN
	SELECT first_name, last_name FROM employees
    WHERE salary >= target_salary
    ORDER BY first_name, last_name, employee_id;
END$$

#03. Town Names Starting With
DELIMITER $$
CREATE PROCEDURE usp_get_towns_starting_with (starting_text VARCHAR(50))
BEGIN
	SELECT name FROM towns
    WHERE name LIKE CONCAT(starting_text, '%')
    ORDER BY name;
END$$

#04. Employees from Town
DELIMITER $$
CREATE PROCEDURE usp_get_employees_from_town (searched_name VARCHAR(50))
BEGIN
	SELECT first_name, last_name FROM employees AS e
    JOIN addresses AS a USING (address_id) /* ON e.address_id = a.address_id*/
    JOIN towns AS t USING (town_id)
    WHERE t.name = searched_name
    ORDER BY first_name, last_name;
END$$

#05. Salary Level Function
DELIMITER $$
CREATE FUNCTION ufn_get_salary_level (salary DECIMAL(19, 4))
RETURNS VARCHAR(10)
DETERMINISTIC 
BEGIN
	DECLARE salary_level VARCHAR(10); #ТЕКСТ С НИВОТО НА ЗАПЛАТАТА
    IF salary < 30000 THEN SET salary_level := 'Low';
    ELSEIF salary <= 50000 THEN SET salary_level := 'Average';
    ELSE SET salary_level := 'High';
    END IF;
    RETURN salary_level;
END$$

#06. Employees by Salary Level
DELIMITER $$
CREATE PROCEDURE usp_get_employees_by_salary_level (salary_level VARCHAR(10))
BEGIN 
	SELECT first_name, last_name FROM employees
    WHERE ufn_get_salary_level(salary) = salary_level
    ORDER BY first_name DESC, last_name DESC;
END$$

#07. Define Function
DELIMITER $$
CREATE FUNCTION ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50))
RETURNS INT # 0 -> word не е съставена от set_of_letters
			# 1 -> word е съставена от set_of_letters
DETERMINISTIC
BEGIN
	RETURN word REGEXP (CONCAT('^[', set_of_letters, ']+$'));
END$$







