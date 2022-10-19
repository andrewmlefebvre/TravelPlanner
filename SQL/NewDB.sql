-- CREATE SCHEMA CSC536;
-- CREATE SCHEMA CSC536TEST;
-- USE CSC536;
USE CSC536Test;

DROP PROCEDURE IF EXISTS WIPE;

DELIMITER $$
CREATE PROCEDURE WIPE()
BEGIN
	DROP TABLE IF EXISTS USERTRIP;
	DROP TABLE IF EXISTS FRIENDS;
	DROP TABLE IF EXISTS NEARBY;
	DROP TABLE IF EXISTS EVENT, APIINFORMATION;
	DROP TABLE IF EXISTS EVENT_SUBTYPES;
	DROP TABLE IF EXISTS TRIP;
	DROP TABLE IF EXISTS USER;



CREATE TABLE IF NOT EXISTS USER(
	ID int NOT NULL AUTO_INCREMENT,
	firstName varchar(255) not null,
	lastName varchar(255) not null,
	dob date not null,
	homeAddress varchar(255) not null,

	PRIMARY KEY (ID)
);

-- INSERT INTO USER VALUES (null, 'Andrew', 'Lefebvre', '1999-05-04', 'Smithfield');
-- Select * FROM User;
-- Select * FROM User WHERE USER.firstName = 'Andrew' and USER.lastName = 'Lefebvre';


CREATE TABLE IF NOT EXISTS FRIENDS(
	ID int NOT NULL AUTO_INCREMENT,
	userID int not null,
	friendID int not null,

	PRIMARY KEY (ID),
	FOREIGN KEY (UserID) REFERENCES USER(ID),
	FOREIGN KEY (friendID) REFERENCES USER(ID),
    UNIQUE(userID, friendID)
);

CREATE TABLE IF NOT EXISTS TRIP(
	ID int NOT NULL AUTO_INCREMENT,
	name varchar(255) not null,
	startDate date not null,
	endDate date not null,

	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS USERTRIP(
	ID int NOT NULL AUTO_INCREMENT,
	userID int NOT NULL,
	tripID int NOT NULL,

	PRIMARY KEY (ID),
	FOREIGN KEY (userID) REFERENCES USER(ID),
	FOREIGN KEY (tripID) REFERENCES TRIP(ID)
);

CREATE TABLE IF NOT EXISTS EVENT_SUBTYPES(
	subtype varchar(255) NOT NULL,
    
    PRIMARY KEY (subtype)
);
INSERT INTO EVENT_SUBTYPES VALUES ('Dwelling');
INSERT INTO EVENT_SUBTYPES VALUES ('Transportation');
INSERT INTO EVENT_SUBTYPES VALUES ('Activity');

CREATE TABLE IF NOT EXISTS EVENT(
	ID int NOT NULL AUTO_INCREMENT,
	subtype varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	street varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    state varchar(255) NOT NULL,
    postal varchar(255) NOT NULL,
	country varchar(255) NOT NULL,
    tripID int NOT NULL,
    
    lat float,
    lon float,
    
    temp float,
    des varchar(255),
    feelsLike float,
    UV float,
    wind float,
    
    
    	-- APIID int NOT NULL,
	-- contactInfo varchar(255),
	-- price int,
	-- isIndoors bit,
	-- time date,
	-- checkIn date,
	-- checkOut date,
	-- dwellingType varchar(255),
	-- transportationType varchar(255),
	-- flightNumber varchar(255),

	PRIMARY KEY (ID),
	FOREIGN KEY (subtype) REFERENCES EVENT_SUBTYPES(subtype),
	FOREIGN KEY (tripID) REFERENCES TRIP(ID)
);

CREATE TABLE IF NOT EXISTS NEARBY(
	ID int NOT NULL AUTO_INCREMENT,
	address varchar(255),
	name varchar(255),
	type varchar(255),

	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS APIINFORMATION(
	ID int NOT NULL AUTO_INCREMENT,
	eventID int NOT NULL,
	weatherTemp int,
	weatherType varchar(255),
	safetyRating varchar(255),
	gasPrice float,

	PRIMARY KEY (ID),
	FOREIGN KEY (eventID) REFERENCES EVENT(ID)
);

END $$

DELIMITER ;

CALL WIPE();


-- SELECT * FROM FRIENDS;

