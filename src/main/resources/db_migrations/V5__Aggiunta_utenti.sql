INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role)
VALUES ('luca.simonetto.94@gmail.com', 'Luca', 'Simonetto', 'HitLuca', 'password', '+39 3421678682', '1994/09/24', '130.00', '2');
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role)
VALUES ('bosottiluca@gmail.com', 'Luca', 'Bosotti', 'Etrunon', 'blablabla', '+39 3334782359', '1994/12/20', '87.78', '0');
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role)
VALUES ('luca.alberigo217@gmail.com', 'Luca', 'Alberigo', 'fuffaknight',  'open', '+39 3460865707', '1992/02/12', '100', '1');
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role)
VALUES ('alessandro.bacchiega@studenti.unitn.it',  'Alessandro', 'Bacchiega', 'lapaladinadellagiustizia99', 'pippo', '+39 3481330281', '1994/06/28', '90.30', '0');
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role)
VALUES ('mion00@gmail.com', 'Carlo', 'Mion', 'ilmionome', 'ilmiocognome', '+39 3466252794', '1994/12/09', '87.30', '0');
INSERT INTO users (email, name, surname, username, password, phone_number, birthday, residual_credit, role)
VALUES ('marco.federici@studenti.unitn.it', 'Marco', 'Federici', 'M4rcOSX', 'belloemonello', '+39 3473552131', '1994/01/14', '30.00', '1');
INSERT INTO user_credit_cards (id_user, credit_card_number) SELECT id_user, '1234123412341234' FROM users WHERE username = 'HitLuca';
INSERT INTO user_credit_cards (id_user, credit_card_number) SELECT id_user, '1111222233334444' FROM users WHERE username = 'Etrunon';
INSERT INTO user_credit_cards (id_user, credit_card_number) SELECT id_user, '9999888877776666' FROM users WHERE username = 'ilmionome';
INSERT INTO user_credit_cards (id_user, credit_card_number) SELECT id_user, '0000000000000000' FROM users WHERE username = 'HitLuca';
INSERT INTO user_credit_cards (id_user, credit_card_number) SELECT id_user, '1598746253120256' FROM users WHERE username = 'lapaladinadellagiustizia99';
INSERT INTO user_credit_cards (id_user, credit_card_number) SELECT id_user, '9191211631516488' FROM users WHERE username = 'fuffaknight';
INSERT INTO user_credit_cards (id_user, credit_card_number) SELECT id_user, '3213213213213210' FROM users WHERE username = 'lapaladinadellagiustizia99';
