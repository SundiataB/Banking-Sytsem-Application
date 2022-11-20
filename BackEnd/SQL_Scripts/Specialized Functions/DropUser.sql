UPDATE users
SET activity_status = false
WHERE username = 'the username of the user who is ending the account';

/* Typically a bank will keep personal records on hand after they have closed 
accounts for a matter of data integrity and record keeping so changing the activity 
status will be a better option than dropping the data*/