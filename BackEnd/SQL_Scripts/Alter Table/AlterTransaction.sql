ALTER TABLE transaction
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id);