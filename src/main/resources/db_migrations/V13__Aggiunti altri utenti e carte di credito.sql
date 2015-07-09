INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) VALUES
  ('mario.rossi@gmail.com', 'Mario', 'Rossi', 'MarioRossi', '1mariorossi', '+39 3216598564', '1990-05-13', 12.50, 0)
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) VALUES
  ('andrea.bianchi@gmail.com', 'Andrea', 'Bianchi', 'AndreaBianchi', '2andreabianchi', '+39 3678542102', '1989-01-01',
   3.00, 0);
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) VALUES
  ('mattia.guarise@hotmail.it', 'Mattia', 'Guarise', 'MattiaGuarise', '555mattia222', '+39 3265547895', '1994-12-31',
   20.0, 0);
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) VALUES
  ('giorgio.zorzi@gmail.com', 'Giorgio', 'Zorzi', 'GiorgioZorzi', 'ilragazzomolesto23', '+39 3656998652', '1992-02-20',
   25.50, 0);
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role) VALUES
  ('alessio.renzi@gmail.com', 'Alessio', 'Renzi', 'AlessioRenzi', 'v0gli4mol3rusp3', '+39 2555411789', '1972-07-28',
   90.50, 0);

INSERT INTO user_credit_cards (credit_card_number, username) VALUES ('1346295016738245', 'MarioRossi');
INSERT INTO user_credit_cards (credit_card_number, username) VALUES ('9768346521012457', 'AlessioRenzi');
INSERT INTO user_credit_cards (credit_card_number, username) VALUES ('9876543211234567', 'GiorgioZorzi');
INSERT INTO user_credit_cards (credit_card_number, username) VALUES ('7412589632147856', 'MattiaGuarise');
INSERT INTO user_credit_cards (credit_card_number, username) VALUES ('9999999999666666', 'AndreaBianchi');
