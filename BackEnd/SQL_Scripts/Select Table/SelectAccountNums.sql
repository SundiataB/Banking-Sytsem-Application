SELECT ACC.account_number 
FROM accounts AS ACC 
WHERE ACC.account_id IN (SELECT AO.account_id 
			FROM account_ownership AS AO 
			WHERE AO.user_id = (	SELECT US.user_id 
						FROM users AS US 
						WHERE US.username = '"+username+"')) 
ORDER BY ACC.account_number DESC;