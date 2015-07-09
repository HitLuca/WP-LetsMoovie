ALTER TABLE film_genres DROP CONSTRAINT film_genres_pkey;
ALTER TABLE film_genres ADD PRIMARY KEY (id_film, genre);