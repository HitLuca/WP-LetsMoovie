ALTER TABLE users DROP CONSTRAINT users_pkey CASCADE;
ALTER TABLE users ADD PRIMARY KEY(username);
ALTER TABLE users ALTER COLUMN email SET NOT NULL;

ALTER TABLE user_credit_cards ADD COLUMN "username" TEXT;
ALTER TABLE user_credit_cards ADD FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE;
ALTER TABLE user_credit_cards DROP CONSTRAINT user_credit_cards_pkey CASCADE;

UPDATE user_credit_cards SET username=(SELECT username FROM users WHERE user_credit_cards.id_user=users.id_user);
ALTER TABLE user_credit_cards ADD PRIMARY KEY (username, credit_card_number);
ALTER TABLE user_credit_cards DROP id_user;
ALTER TABLE users DROP id_user;