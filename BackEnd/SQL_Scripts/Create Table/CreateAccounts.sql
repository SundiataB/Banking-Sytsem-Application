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
