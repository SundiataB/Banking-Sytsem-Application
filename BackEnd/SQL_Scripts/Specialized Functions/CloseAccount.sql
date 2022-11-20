/*Two Options*/

/*Option One - do not allow users to close accounts by entering their account number but rather from a form*/
/*This functionality should only be implemented when a user is logged in*/
/*Users should not be able to enter in their account number for the sake of security*/
/*NEEDS TO BE DONE IN THIS ORDER*/

DELETE FROM transaction as TR
WHERE TR.account_id = (SELECT ACC.account_id
                    FROM accounts AS ACC
                    WHERE ACC.account_number = 'ACC Number returned from form');

DELETE FROM accounts as ACC
WHERE ACC.account_number = 'ACC Number returned from form';

/*Option Two - Allow users to close by entering their account number but make sure valid permissions
  with cross reference for users and account numbers in account ownership*/
  /*NEEDS TO BE DONE IN THIS ORDER*/

DELETE FROM transaction as TR
WHERE TR.account_id =  (SELECT ACC.account_id
                        FROM accounts AS ACC
                        WHERE ACC.account_id IN (SELECT AO.account_id
                                                 FROM account_ownership as AO
                                                 WHERE AO.user_id = 'current userID stored in java class from login')
                        AND ACC.account_number = 'Account number specified on form');

DELETE FROM accounts as ACC
WHERE ACC.account_number = 'ACC Number returned from form'
AND ACC.account_id IN ( SELECT AO.account_id
                        FROM account_ownership as AO
                        WHERE AO.user_id = 'current userID stored in java class from login');