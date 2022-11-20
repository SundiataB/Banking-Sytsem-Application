DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction
(
    account_id int,
    transaction_timestamp datetime NOT NULL,
    monetary_value int NOT NULL,
    payment_due_date datetime NOT NULL,
    primary key (account_id)
);