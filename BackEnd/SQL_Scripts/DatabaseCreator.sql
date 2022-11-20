create database BankingSystemDB;
use BankingSystemDB;

/*CREATE TABLES*/

DROP TABLE IF EXISTS account_ownership;
CREATE TABLE account_ownership
(
    account_id int NOT NULL,
    user_id int NOT NULL,
    primary key (account_id, user_id)
);

DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts
(
    account_id int auto_increment,
    account_number varchar(10) NOT NULL,
    type_id int NOT NULL,
    routing_number varchar(9) NOT NULL,
    current_balance int NOT NULL,
    primary key (account_id)
);

DROP TABLE IF EXISTS account_types;
CREATE TABLE account_types
(
    type_id int auto_increment,
    account_name varchar(10) NOT NULL,
    balance_lower_limit int NOT NULL,
    balance_upper_limit int NOT NULL,
    minimum_spend int NOT NULL,
    annualFee int NOT NULL,
    primary key (type_id)
);

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction
(
    account_id int,
    transaction_timestamp datetime NOT NULL,
    monetary_value int NOT NULL,
    payment_due_date datetime NOT NULL,
    primary key (account_id)
);

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

/*ALTER TABLES*/

ALTER TABLE account_ownership
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id);

ALTER TABLE account_ownership
ADD FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE accounts
ADD FOREIGN KEY (type_id) REFERENCES account_types(type_id);

ALTER TABLE accounts
ADD CONSTRAINT UC_accountnumber UNIQUE (account_number);

ALTER TABLE account_types
ADD CONSTRAINT UC_accName UNIQUE (account_name);

ALTER TABLE transaction
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id);

ALTER TABLE users
ADD CONSTRAINT UC_users UNIQUE (username);

ALTER TABLE users
ADD CONSTRAINT UC_ssn UNIQUE (social_security_num);