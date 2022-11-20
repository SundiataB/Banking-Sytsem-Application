/* This SQL Query is used to retrieve the transaction log for user accounts*/

SELECT ACC.account_number, TSN.transaction_timestamp, TSN.transaction_description, TSN.transaction_category, TSN.monetary_value 
FROM transaction AS TSN 
INNER JOIN accounts AS ACC ON ACC.account_id = TSN.account_id 
WHERE ACC.account_number = '"+accountNumbers.get(i)+"';";
