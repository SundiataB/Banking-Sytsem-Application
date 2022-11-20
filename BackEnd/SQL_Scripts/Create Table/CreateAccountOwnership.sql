DROP TABLE IF EXISTS account_ownership;
CREATE TABLE account_ownership
(
    account_id int NOT NULL,
    user_id int NOT NULL,
    primary key (account_id, user_id)
);
