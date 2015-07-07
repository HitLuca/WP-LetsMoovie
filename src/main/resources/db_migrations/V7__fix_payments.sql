ALTER TABLE payments ADD COLUMN "username" TEXT;
ALTER TABLE payments ADD FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE;
ALTER TABLE payments DROP COLUMN "id_user";
ALTER TABLE payments ADD PRIMARY KEY (username, payment_date, payment_time, ticket_type, id_seat, id_show);
