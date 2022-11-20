/* This SQL Query is used to retrieve the account balance for user accounts*/

SELECT ACC.account_number, sum(TSN.monetary_value) 
FROM transaction AS TSN 
INNER JOIN accounts AS ACC ON ACC.account_id = TSN.account_id 
WHERE ACC.account_id IN (SELECT AO.account_id 
			FROM account_ownership as AO 
			WHERE AO.user_id = (	SELECT US.user_id 
						FROM users as US 
						WHERE US.username = '"+username+"')) 
GROUP BY ACC.account_number 
ORDER BY ACC.account_number DESC;