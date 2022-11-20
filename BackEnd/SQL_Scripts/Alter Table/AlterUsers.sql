ALTER TABLE users
ADD CONSTRAINT UC_users UNIQUE (username);

ALTER TABLE users
ADD CONSTRAINT UC_ssn UNIQUE (social_security_num);