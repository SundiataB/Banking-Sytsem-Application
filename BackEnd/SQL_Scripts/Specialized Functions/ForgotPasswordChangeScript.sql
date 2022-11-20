/* Given an email entry find user_id */
SELECT US.user_id
FROM users as US
WHERE US.email = 'email entered into java'
AND US.activity_status = true;

/* if user_id is null then display error code */

/* Now that you have the user_id, update the account password using insert into query */

INSERT INTO users (user_id, account_password)
/*The values that are passed into the next line will be the member variables of the users class*/
/*The following are just stubs*/
VALUES (current_userID, new_password)
ON CONFLICT (user_id)
DO UPDATE SET account_password = new_password
WHERE user_id = current_userID;
