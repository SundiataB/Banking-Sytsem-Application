ALTER TABLE accounts
ADD FOREIGN KEY (type_id) REFERENCES account_types(type_id);

ALTER TABLE accounts
ADD CONSTRAINT UC_accountnumber UNIQUE (account_number);