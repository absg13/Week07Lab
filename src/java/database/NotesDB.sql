DROP DATABASE if exists NotesDB;
CREATE DATABASE NotesDB;

USE NotesDB;


-- DROP TABLE User;

-- CREATE TABLE User( 
--     username VARCHAR(10) NOT NULL,
--     password VARCHAR(10) NOT NULL,
--     email VARCHAR(30) NOT NULL,
--     active BIT NOT NULL,
--     firstname VARCHAR(50) NOT NULL,
--     lastname VARCHAR(50) NOT NULL,
--     PRIMARY KEY (username)
-- );

DROP TABLE Notes;

CREATE TABLE Notes (
    noteId INT NOT NULL AUTO_INCREMENT,
    dateCreated DATETIME NOT NULL,
    contents NVARCHAR(1000) CHARACTER SET UTF8 NOT NULL,
    PRIMARY KEY (noteId)
);

-- INSERT INTO User values('admin', 'password', 'test@test.com', 1, 'Bob', 'Bobberson');
