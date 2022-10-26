USE CSC536Test;

CALL WIPE();
INSERT INTO USER VALUES (null, 'User1', 'UserLastName1', '2000-5-3', '21 Test Ave Kingston RI 02882','User1');
INSERT INTO USER VALUES (null, 'User2', 'UserLastName2', '2000-5-3', '21 Test Ave Kingston RI 02882','User2');
INSERT INTO USER VALUES (null, 'User3', 'UserLastName3', '2000-5-3', '21 Test Ave Kingston RI 02882','User3');
INSERT INTO USER VALUES (null, 'User4', 'UserLastName4', '2000-5-3', '21 Test Ave Kingston RI 02882','User4');
INSERT INTO USER VALUES (null, 'User5', 'UserLastName5', '2000-5-3', '21 Test Ave Kingston RI 02882','User5');
INSERT INTO USER VALUES (null, 'User6', 'UserLastName6', '2000-5-3', '21 Test Ave Kingston RI 02882','User6');
INSERT INTO USER VALUES (null, 'User7', 'UserLastName7', '2000-5-3', '21 Test Ave Kingston RI 02882','User7');
INSERT INTO USER VALUES (null, 'User8', 'UserLastName8', '2000-5-3', '21 Test Ave Kingston RI 02882','User8');
INSERT INTO USER VALUES (null, 'User9', 'UserLastName9', '2000-5-3', '21 Test Ave Kingston RI 02882','User9');
INSERT INTO USER VALUES (null, 'User10', 'UserLastName10', '2000-5-3', '21 Test Ave Kingston RI 02882','User10');
INSERT INTO TRIP VALUES (null,'Trip1','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip2','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip3','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip4','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip5','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip6','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip7','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip8','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip9','2023-1-2','2023-1-5');
INSERT INTO TRIP VALUES (null,'Trip10','2023-1-2','2023-1-5');

SELECT * FROM TRIP;

SELECT * FROM USER;

INSERT INTO FRIENDS VALUES (null,1,2);
INSERT INTO FRIENDS VALUES (null,1,3);
INSERT INTO FRIENDS VALUES (null,1,4);

SELECT FRIENDS.friendID FROM FRIENDS JOIN USER on USER.ID = FRIENDS.friendID WHERE FRIENDS.userID = 1;
SELECT FRIENDS.friendID FROM FRIENDS JOIN USER on USER.ID = FRIENDS.friendID WHERE FRIENDS.userID = 5;

SELECT * FROM USER WHERE USER.ID =2;

INSERT INTO USERTRIP VALUES (null,1,1);
INSERT INTO USERTRIP VALUES (null,2,2);
INSERT INTO USERTRIP VALUES (null,3,3);
INSERT INTO USERTRIP VALUES (null,4,4);
INSERT INTO USERTRIP VALUES (null,5,5);
INSERT INTO USERTRIP VALUES (null,6,6);
INSERT INTO USERTRIP VALUES (null,7,7);
INSERT INTO USERTRIP VALUES (null,8,8);
INSERT INTO USERTRIP VALUES (null,9,9);
INSERT INTO USERTRIP VALUES (null,10,10);

SELECT TRIP.* FROM USER JOIN USERTRIP on USERTRIP.userID = USER.ID JOIN TRIP on TRIP.ID = USERTRIP.tripID WHERE USER.ID = 1;

INSERT INTO USERTRIP VALUES (null,2,1);

SELECT TRIP.* FROM USER JOIN USERTRIP on USERTRIP.userID = USER.ID JOIN TRIP on TRIP.ID = USERTRIP.tripID WHERE USER.ID = 2;

INSERT INTO EVENT VALUES (null,'Activity','Event1','45 Upper College RD','Kingston','RI','02881','USA',1);
INSERT INTO APIINFORMATION VALUES (null, 1, null, null, null, null, null, null, null);
INSERT INTO EVENT VALUES (null,'Transportation','Transportation1','45 Upper College RD','Kingston','RI','02881','USA',1);
INSERT INTO APIINFORMATION VALUES (null, 2, null, null, null, null, null, null, null);
INSERT INTO EVENT VALUES (null,'Activity','Event2','45 Upper College RD','Kingston','RI','02881','USA',1);
INSERT INTO APIINFORMATION VALUES (null, 3, null, null, null, null, null, null, null);
INSERT INTO EVENT VALUES (null,'Transportation','Transportation2','45 Upper College RD','Kingston','RI','02881','USA',1);
INSERT INTO APIINFORMATION VALUES (null, 4, null, null, null, null, null, null, null);
INSERT INTO EVENT VALUES (null,'Dwelling','Event3','45 Upper College RD','Kingston','RI','02881','USA',1);
INSERT INTO APIINFORMATION VALUES (null, 5, null, null, null, null, null, null, null);

SELECT * FROM Event;

SELECT * FROM APIINFORMATION;

SELECT * FROM Event WHERE EVENT.tripID = 1;

UPDATE APIINFORMATION SET temp = 66.0, des = 'Light rain', feelsLike = 66.0, UV = 1.0, wind = 2.2 WHERE eventID =1;

SELECT * FROM APIInformation WHERE APIInformation.eventID = 1;

-- Number of each type of event for a trip
SELECT EVENT.subtype, COUNT(EVENT.subtype) FROM EVENT JOIN TRIP on EVENT.tripID = TRIP.ID GROUP BY EVENT.subtype;

INSERT INTO EVENT VALUES (null,'Transportation','Transportation2','45 Upper College RD','Kingston','RI','02881','USA',2);
INSERT INTO APIINFORMATION VALUES (null, 4, null, null, null, null, null, null, null);
INSERT INTO EVENT VALUES (null,'Dwelling','Event3','45 Upper College RD','Kingston','RI','02881','USA',2);
INSERT INTO APIINFORMATION VALUES (null, 5, null, null, null, null, null, null, null);

-- Type of event by user
SELECT user.ID, event.subtype, COUNT(event.subtype) FROM USER LEFT JOIN USERTRIP on USER.ID = USERTRIP.userID LEFT JOIN TRIP on USERTRIP.tripID = trip.ID LEFT JOIN event on event.tripID = trip.ID GROUP BY User.ID, event.subtype ORDER BY USER.ID;

-- Amount of friends by user
SELECT USER.ID, COUNT(USER.ID) FROM USER JOIN (SELECT USER.ID FROM USER JOIN FRIENDS on FRIENDS.userID = USER.ID) as friend on friend.ID = USER.ID GROUP BY USER.ID;

INSERT INTO FRIENDS VALUES (null,2,1);

SELECT USER.ID, COUNT(USER.ID) FROM USER JOIN (SELECT USER.ID FROM USER JOIN FRIENDS on FRIENDS.userID = USER.ID) as friend on friend.ID = USER.ID GROUP BY USER.ID;



