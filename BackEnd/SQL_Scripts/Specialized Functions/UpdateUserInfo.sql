INSERT INTO users (username, account_password, first_name, last_name, email, contact_number, street_address, postcode, date_of_birth, social_security_num)
/*The values that are passed into the next line will be the member variables of the users class*/
/*The following are just stubs*/
VALUES (username, new_password, first_name, last_name, new_email, new_contact_num, new_street_address, new_postcode, date_of_birth, social_security_num)
ON CONFLICT (username)
DO UPDATE SET
              account_password = new_password,
              email = new_email,
              contact_number = new_contact_num,
              street_address = new_street_address,
              postcode = new_postcode;

