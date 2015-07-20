CREATE TABLE config (
  parameter TEXT,
  value     TEXT,
  PRIMARY KEY (parameter, value)
);

INSERT INTO config (parameter, value) VALUES ('API_USER', 'lets-movie');
INSERT INTO config (parameter, value) VALUES ('API_KEY', 'lets-moovie05web');
INSERT INTO config (parameter, value) VALUES ('SEND_MAIL', 'TRUE');