/*Part IV – Date Functions Queries*/

/*16. Orders Table*/
SELECT product_name, order_date,
 adddate(order_date, interval 3 day) AS 'pay_due', 
 adddate(order_date, interval 1 month) AS 'deliver_due'
 FROM orders;