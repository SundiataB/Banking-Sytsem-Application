SELECT US.user_id
FROM users AS US
WHERE US.username = 'Username entered on webpage'
AND US.account_password = 'Password entered on webpage'
AND US.activity_status = true;

/* If this is returned with a User ID then log in is successful 
and the information about the user should be stored in the class */