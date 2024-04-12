-- Create Database
CREATE DATABASE IF NOT EXISTS PremierLeagueDB;
USE PremierLeagueDB;

-- Create Player Table
CREATE TABLE IF NOT EXISTS Player (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
    rating FLOAT NOT NULL,
    age INT NOT NULL
    );

-- Populate Player Table
INSERT INTO Player (name, rating, age) VALUES
                                           ('Bruno Fernandes', 87.6, 27),
                                           ('Kevin De Bruyne', 89.0, 30),
                                           ('Mohamed Salah', 90.5, 29),
                                           ('Harry Kane', 89.2, 28),
                                           ('Jack Grealish', 86.7, 26),
                                           ('Marcus Rashford', 86.3, 24),
                                           ('Virgil van Dijk', 88.9, 30),
                                           ('Ruben Dias', 87.5, 25),
                                           ('Bukayo Saka', 84.2, 20),
                                           ('N''Golo Kanté', 88.1, 30);

-- Create Team Table
CREATE TABLE IF NOT EXISTS Team (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
    coach VARCHAR(255) NOT NULL
    );

-- Populate Team Table
INSERT INTO Team (name, coach) VALUES
                                   ('Manchester United', 'Ole Gunnar Solskjær'),
                                   ('Manchester City', 'Pep Guardiola'),
                                   ('Liverpool', 'Jurgen Klopp'),
                                   ('Tottenham Hotspur', 'Antonio Conte'),
                                   ('Chelsea', 'Thomas Tuchel'),
                                   ('Arsenal', 'Mikel Arteta'),
                                   ('Leicester City', 'Brendan Rodgers'),
                                   ('West Ham United', 'David Moyes'),
                                   ('Aston Villa', 'Steven Gerrard'),
                                   ('Leeds United', 'Marcelo Bielsa');

-- Create FootballMatch Table
CREATE TABLE IF NOT EXISTS FootballMatch (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             date TIMESTAMP NOT NULL,
                                             team1_id INT,
                                             team2_id INT,
                                             score_team1 INT,
                                             score_team2 INT,
                                             FOREIGN KEY (team1_id) REFERENCES Team(id),
    FOREIGN KEY (team2_id) REFERENCES Team(id)
    );

-- Populate FootballMatch Table
INSERT INTO FootballMatch (date, team1_id, team2_id, score_team1, score_team2) VALUES
                                                                                   ('2024-03-10 12:00:00', 1, 2, 2, 1),
                                                                                   ('2024-03-11 13:30:00', 3, 4, 0, 3),
                                                                                   ('2024-03-12 15:15:00', 5, 1, 1, 1),
                                                                                   ('2024-03-13 16:45:00', 2, 3, 2, 0),
                                                                                   ('2024-03-14 18:00:00', 4, 5, 1, 2),
                                                                                   ('2024-03-15 14:30:00', 6, 7, 2, 2),
                                                                                   ('2024-03-16 16:00:00', 8, 9, 1, 3),
                                                                                   ('2024-03-17 17:45:00', 10, 1, 0, 2),
                                                                                   ('2024-03-18 19:15:00', 3, 4, 3, 1),
                                                                                   ('2024-03-19 20:30:00', 2, 5, 2, 2);

-- Create League Table
CREATE TABLE IF NOT EXISTS League (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL
    );

-- Populate League Table
INSERT INTO League (name) VALUES
                              ('Premier League'),
                              ('La Liga'),
                              ('Bundesliga');