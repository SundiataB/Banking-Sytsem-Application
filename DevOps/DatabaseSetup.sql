CREATE DATABASE IF NOT EXISTS BankingSystemDB;
USE BankingSystemDB;

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
    transaction_id int auto_increment,
    account_id int NOT NULL,
    transaction_timestamp datetime NOT NULL,
    transaction_description varchar(10) NOT NULL,
    transaction_category varchar(10) NOT NULL,
    monetary_value int NOT NULL,
    payment_due_date datetime NOT NULL,
    primary key (transaction_id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
	user_id int auto_increment,
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

INSERT INTO account_types (account_name, balance_lower_limit, balance_upper_limit, minimum_spend, annualfee)
/*Need to design what type of accounts and enter info in to the following to add it to the table*/
VALUES
('Savings', 0, 1000000000, 0, 0),
('Credit', 0, 1000000000, 0, 0),
('Checking', 0, 1000000000, 0, 0);

insert into users (user_id, username, account_password,
                   first_name, last_name,
                   date_of_birth, social_security_num,
                   email, contact_number, street_address,
                   postcode, activity_status)
values (1, 'test', 'abcde', 'Test', 'User', '2022-01-01', 123456789,
        'test@test.com', 123456789, '1234 Test St.', 12345, 1);

insert into accounts (account_id, account_number, type_id, 
                      routing_number)
values (1, '0123456789', 3, '999999999'),
       (2, '9876543210', 2, '888888888'),
       (3, '1111111111', 1, '777777777');

insert into transaction (transaction_id, account_id, transaction_timestamp,
                         transaction_description, transaction_category,
                         monetary_value, payment_due_date)
values 	(1, 1,  '2022-01-02 10:10:10', 'Cash', 'Deposit', 1, '2022-01-04 10:10:10'),
	(2, 2,  '2022-01-02 10:10:10', 'Cash', 'Credit', -5, '2022-01-04 10:10:10'),
	(3, 3,  '2022-01-02 10:10:10', 'Cash', 'Checking', 50, '2022-01-04 10:10:10'),
	(4, 1,  '2022-01-02 10:10:10', 'Cash', 'Deposit', 10, '2022-01-04 10:10:10'),
	(5, 1,  '2022-01-02 10:10:10', 'Card', 'Withdraw', -6, '2022-01-04 10:10:10');


insert into account_ownership (account_id, user_id)
values (1, 1),
       (2, 1),
       (3, 1);
