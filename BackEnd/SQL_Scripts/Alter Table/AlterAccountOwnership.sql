ALTER TABLE account_ownership
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id);

ALTER TABLE account_ownership
ADD FOREIGN KEY (user_id) REFERENCES users(user_id);