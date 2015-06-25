CREATE TABLE cinema_rooms (
  room_number INT,
  length INT,
  width  INT,
  PRIMARY KEY (room_number)
);

CREATE TABLE seats (
  id_seat  SERIAL,
  room_number INT,
  row      INT,
  "column" INT,
  status   TEXT,
  PRIMARY KEY (id_seat),
  FOREIGN KEY (room_number) REFERENCES cinema_rooms (room_number)
);

CREATE TABLE films (
  id_film   SERIAL,
  film_title TEXT,
  poster    TEXT,
  duration  SMALLINT,
  trailer   TEXT,
  metascore SMALLINT,
  rating    REAL,
  year      SMALLINT,
  plot      TEXT,
  director  TEXT,
  VM        INT,
  PRIMARY KEY (id_film)
);

CREATE TABLE film_genres (
  id_film SERIAL,
  genre TEXT,
  PRIMARY KEY (id_film),
  FOREIGN KEY (id_film) REFERENCES films (id_film)
);

CREATE TABLE actors (
  actor_name TEXT,
  URL_photo TEXT,
  PRIMARY KEY (actor_name)
);

CREATE TABLE film_actors (
  id_film INT,
  actor_name TEXT,
  role    TEXT,
  PRIMARY KEY (id_film, actor_name, role),
  FOREIGN KEY (id_film) REFERENCES films (id_film),
  FOREIGN KEY (actor_name) REFERENCES actors (actor_name)
);

CREATE TABLE users (
  id_user         SERIAL,
  email           TEXT,
  name            TEXT,
  surname         TEXT,
  username        TEXT,
  password        TEXT,
  phone_number    TEXT,
  birthday        DATE,
  residual_credit REAL DEFAULT(0.0),
  role            INT DEFAULT 0,
  PRIMARY KEY (id_user)
);

CREATE TABLE prices (
  ticket_type TEXT,
  price REAL,
  PRIMARY KEY (ticket_type)
);

CREATE TABLE shows (
  id_show   SERIAL,
  room_number INT,
  id_film   INT,
  show_date DATE,
  show_time TIME,
  PRIMARY KEY (id_show),
  FOREIGN KEY (room_number) REFERENCES cinema_rooms (room_number),
  FOREIGN KEY (id_film) REFERENCES films (id_film)
);

CREATE TABLE payments (
  id_user     INT,
  payment_date DATE,
  payment_time TIME,
  ticket_type TEXT,
  id_seat     INT,
  id_show     INT,
  PRIMARY KEY (id_user, payment_date, payment_time, ticket_type, id_seat, id_show),
  FOREIGN KEY (id_user) REFERENCES users (id_user),
  FOREIGN KEY (ticket_type) REFERENCES prices (ticket_type),
  FOREIGN KEY (id_seat) REFERENCES seats (id_seat),
  FOREIGN KEY (id_show) REFERENCES shows (id_show)
);

CREATE TABLE seat_reservations (
  id_show INT,
  id_seat INT,
  status TEXT,
  PRIMARY KEY (id_show, id_seat),
  FOREIGN KEY (id_show) REFERENCES shows (id_show),
  FOREIGN KEY (id_seat) REFERENCES seats (id_seat)
);

CREATE TABLE user_credit_cards (
  id_user INT,
  credit_card_number INT,
  PRIMARY KEY (id_user, credit_card_number),
  FOREIGN KEY (id_user) REFERENCES users (id_user)
)