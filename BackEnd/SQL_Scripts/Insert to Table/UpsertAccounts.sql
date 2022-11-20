INSERT INTO accounts (account_number, type_id, routing_number, current_balance)
/*Stubs used below in Values () are for the member Variables*/
VALUES (account_number, type_id, routing_number, new_balance)
ON CONFLICT (account_id)
/*The current balance after the equal sign is for the member variable in java*/
/*unsure whether the new balance would need to be checked here for the sake of balance limits*/
DO UPDATE SET current_balance = new_balance
WHERE account_id = account_id
AND new_balance <   (SELECT ATYPE.balanceUpperLimit
                     FROM account_types AS ATYPE, accounts AS ACC
                     WHERE ACC.type_id = ATYPE.type_id)
AND new_balance >   (SELECT ATYPE.balanceLowerLimit
                     FROM account_types AS ATYPE, accounts AS ACC
                     WHERE ACC.type_id = ATYPE.type_id);