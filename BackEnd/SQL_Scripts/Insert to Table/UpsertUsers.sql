INSERT INTO users (username, account_password, first_name, last_name, email, contact_number, street_address, postcode, date_of_birth, social_security_num, activity_status)
/*The values that are passed into the next line will be the member variables of the users class*/
/*The following are just stubs*/
VALUES (username, account_password, first_name, last_name, email, contact_number, street_address, postcode, date_of_birth, social_security_num, TRUE)
ON CONFLICT (username)
DO NOTHING;