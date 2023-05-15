#PART II – Queries for Bank Database

#08. Find Full Name
DELIMITER $$
CREATE PROCEDURE usp_get_holders_full_name ()
BEGIN
	SELECT CONCAT(first_name, ' ', last_name) AS 'full_name' FROM account_holders
    ORDER BY full_name, id;
END$$

#9. People with Balance Higher Than
DELIMITER $$
CREATE PROCEDURE usp_get_holders_with_balance_higher_than (money DECIMAL(19, 4))
BEGIN
	SELECT ah.first_name, ah.last_name FROM account_holders AS ah
    LEFT JOIN accounts AS a ON ah.id = a.account_holder_id
    GROUP BY ah.first_name, ah.last_name
    HAVING SUM(a.balance) > money
    ORDER BY ah.id;
END$$


#10. Future Value Function
DELIMITER $$
CREATE FUNCTION ufn_calculate_future_value (sum DECIMAL(19, 4), yearly_rate DOUBLE, years INT)
RETURNS DECIMAL (19, 4)
DETERMINISTIC
BEGIN
	DECLARE future_sum DECIMAL(19, 4);
    SET future_sum := sum * POW(1 + yearly_rate, years); /* FV = I x ((1 + R) ^T) */
    RETURN future_sum;
END$$

#11. Calculating Interest
DELIMITER $$
CREATE PROCEDURE usp_calculate_future_value_for_account (id INT, rate DECIMAL(19, 4))
BEGIN
	SELECT
    a.id AS 'account_id',
    ah.first_name,
    ah.last_name, 
    a.balance AS 'current_balance',
    ufn_calculate_future_value(a.balance, rate, 5) AS 'balance_in_5_years'
	FROM account_holders AS ah
    JOIN accounts AS a ON ah.id = a.account_holder_id
    WHERE a.id = id;
END$$

#12. Deposit Money
DELIMITER $$
CREATE PROCEDURE usp_deposit_money(account_id INT, money_amount DECIMAL(19, 4))
BEGIN
	/*проверка на сумата (money_amount) -> сметката с даденото account_id да  я увеличим с money_amount*/
	START TRANSACTION;
    IF (money_amount <= 0) THEN ROLLBACK;
    ELSE
	UPDATE accounts AS a SET a.balance = a.balance + money_amount
    WHERE a.id = account_id;
    END IF;
END$$

#13. Withdraw Money
DELIMITER $$
CREATE PROCEDURE usp_withdraw_money(account_id INT, money_amount DECIMAL(19, 4))
BEGIN
	/*проверка на сумата money_amount > 0 и balance >= 0
    -> account_id balance - money_amount*/
    START TRANSACTION;
    IF (money_amount <= 0 OR (SELECT balance FROM accounts AS a WHERE a.id = account_id) < money_amount) THEN ROLLBACK;
    ELSE 
		UPDATE accounts AS ac SET ac.balance = ac.balance - money_amount
        WHERE ac.id = id;
        COMMIT;
    END IF;
END$$

#14. Money Transfer
DELIMITER $$
CREATE PROCEDURE usp_transfer_money(from_id INT, to_id INT, money_amount DECIMAL(19, 4)) 
BEGIN
	START TRANSACTION;
	/*1.валидно accountID -> from_id и to_id
    2. from_id = to_id
    3. amount > 0
    4. от сметката from_id трябва да има balance >= amount*/
    IF (money_amount <= 0 OR (SELECT balance FROM accounts WHERE id = from_id) < money_amount
		OR from_id = to_id 
        OR (SELECT COUNT(id) FROM accounts WHERE id = from_id) <> 1
        OR (SELECT COUNT(id) FROM accounts WHERE id = to_id) <> 1)
        THEN ROLLBACK; /*ако тези неща са верни прекъсни */
        ELSE
			UPDATE accounts SET balance = balance - money_amount
            WHERE id = from_id;
            UPDATE accounts SET balance = balance + money_amount
            WHERE id = to_id;
            COMMIT;
        END IF;
END$$

#15. Log Accounts Trigger
CREATE TABLE `logs` (
	log_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    old_sum DECIMAL(19, 4),
    new_sum DECIMAL (19, 4)
);

DELIMITER $$
CREATE TRIGGER tr_change_balance_account
AFTER UPDATE ON accounts
FOR EACH ROW #всеки ред който е променен
BEGIN
	INSERT INTO logs (account_id, old_sum, new_sum)
    VALUE (OLD.id, OLD.balance, NEW.balance);
END$$

#16. Emails Trigger
CREATE TABLE `notification_emails`(
    `id` INT PRIMARY KEY AUTO_INCREMENT, 
    `recipient` INT NOT NULL,
    `subject` TEXT,
    `body` TEXT
);

DELIMITER $$
CREATE TRIGGER tr_email_on_incert
AFTER INSERT ON logs
FOR EACH ROW #за всеки вмъкнат ред в logs
BEGIN
	INSERT INTO notification_emails (recipient, subject, body)
    VALUES(
		NEW.account_id,
        CONCAT('Balance change for account: ', NEW.account_id),
        CONCAT('On ', NOW(), ' your balance was changed from ', NEW.old_sum, ' to ', NEW.new_sum, '.')
        );
END$$

