DROP TABLE IF EXISTS users;
CREATE TABLE users
(
	user_id int,
	username varchar(10) NOT NULL,
	account_password varchar(17) NOT NULL,
	first_name varchar(10) NOT NULL,
	last_name varchar(10) NOT NULL,
	date_of_birth date NOT NULL,
	social_security_num int NOT NULL,
	email varchar(30) NOT NULL,
	contact_number varchar(10) NOT NULL,
	street_address varchar(30) NOT NULL,
	postcode varchar(5) NOT NULL,
	activity_status BOOLEAN NOT NULL,
	primary key (user_id)
);