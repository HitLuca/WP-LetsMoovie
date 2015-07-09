ALTER TABLE film_genres DROP CONSTRAINT film_genres_id_film_fkey;
ALTER TABLE film_genres ADD FOREIGN KEY (id_film) REFERENCES films (id_film) ON DELETE CASCADE;

ALTER TABLE film_actors DROP CONSTRAINT film_actors_id_film_fkey;
ALTER TABLE film_actors ADD FOREIGN KEY (id_film) REFERENCES films (id_film) ON DELETE CASCADE;
ALTER TABLE film_actors DROP CONSTRAINT film_actors_actor_name_fkey;
ALTER TABLE film_actors ADD FOREIGN KEY (actor_name) REFERENCES actors (actor_name) ON DELETE CASCADE;

ALTER TABLE payments DROP CONSTRAINT payments_id_seat_fkey;
ALTER TABLE payments ADD FOREIGN KEY (id_seat) REFERENCES seats (id_seat) ON DELETE CASCADE;
ALTER TABLE payments DROP CONSTRAINT payments_id_show_fkey;
ALTER TABLE payments ADD FOREIGN KEY (id_show) REFERENCES shows (id_show) ON DELETE CASCADE;
ALTER TABLE payments DROP CONSTRAINT payments_ticket_type_fkey;
ALTER TABLE payments ADD FOREIGN KEY (ticket_type) REFERENCES prices (ticket_type) ON DELETE CASCADE;
ALTER TABLE payments DROP CONSTRAINT payments_username_fkey;
ALTER TABLE payments ADD FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE;

ALTER TABLE seat_reservations DROP CONSTRAINT seat_reservations_id_seat_fkey;
ALTER TABLE seat_reservations ADD FOREIGN KEY (id_seat) REFERENCES seats (id_seat) ON DELETE CASCADE;
ALTER TABLE seat_reservations DROP CONSTRAINT seat_reservations_id_show_fkey;
ALTER TABLE seat_reservations ADD FOREIGN KEY (id_show) REFERENCES shows (id_show) ON DELETE CASCADE;

ALTER TABLE seats DROP CONSTRAINT seats_room_number_fkey;
ALTER TABLE seats ADD FOREIGN KEY (room_number) REFERENCES cinema_rooms (room_number) ON DELETE CASCADE;

ALTER TABLE shows DROP CONSTRAINT shows_id_film_fkey;
ALTER TABLE shows ADD FOREIGN KEY (id_film) REFERENCES films (id_film) ON DELETE CASCADE;
ALTER TABLE shows DROP CONSTRAINT shows_room_number_fkey;
ALTER TABLE shows ADD FOREIGN KEY (room_number) REFERENCES cinema_rooms (room_number) ON DELETE CASCADE;

ALTER TABLE user_credit_cards DROP CONSTRAINT user_credit_cards_username_fkey;
ALTER TABLE user_credit_cards ADD FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE;





