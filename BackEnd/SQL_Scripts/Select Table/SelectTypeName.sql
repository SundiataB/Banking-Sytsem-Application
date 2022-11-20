SELECT AT.account_name
FROM account_types AS AT
WHERE AT.type_id IN (SELECT ACC.type_id
			FROM accounts as ACC
			WHERE ACC.account_number = '');