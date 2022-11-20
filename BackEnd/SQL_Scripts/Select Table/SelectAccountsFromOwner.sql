/* This SQL query returns all the account information 
associated with the User that is making the query proceed */

SELECT AO.account_id, ACC.account_number, ACC.current_balance, ACC.routing_number
FROM account_ownership as AO
INNER JOIN accounts AS ACC
ON AO.account_id = ACC.account_id
WHERE AO.user_id = (SELECT US.user_id
                    FROM users AS US
                    WHERE US.username = 'stub to be replaced by member variable for username');