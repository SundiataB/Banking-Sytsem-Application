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