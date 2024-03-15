-- create database
CREATE DATABASE IF NOT EXISTS medilabo_test;

USE medilabo_test;

-- create tables
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS doctor;

CREATE TABLE patient (
        id int NOT NULL AUTO_INCREMENT,
        firstname varchar(255) NOT NULL,
        lastname varchar(255) NOT NULL,
        birthdate date NOT NULL,
        gender varchar(1) NOT NULL,
        address varchar(255),
        phone varchar(20),
        PRIMARY KEY(id)
);

CREATE TABLE doctor (
        id int NOT NULL AUTO_INCREMENT,
        username varchar(255) NOT NULL,
        password varchar(255) NOT NULL,
        PRIMARY KEY(id)
);

-- Add data
INSERT INTO patient (firstname, lastname, birthdate, gender, address, phone) VALUES
('Test', 'TestNone', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
('Test', 'TestBorderline', '1945-06-24', 'M', '2 High St', '200-333-4444'),
('Test', 'TestInDanger', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
('Test', 'TestEarlyOnset', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666');

INSERT INTO doctor (username, password) VALUES
('user', '$2y$10$RBGW8BwRgecaDpWX1LNr4Oik1qe37JdAS9lpmGbO0skTnQjBuGzHa');