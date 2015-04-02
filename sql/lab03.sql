/*
    Two SQL statements in a file (lab03.sql)

    It must create two tables
    user
       userId, int (auto increment, pk)
       username, varchar (must be unique)
       password, varchar
    user_profile
       userId, int (pk, fk references user table)
       firstName, varchar
       lastName, varchar
*/

CREATE TABLE `user`
(
    `userID` int AUTO_INCREMENT,
    `username` varchar(50) UNIQUE,
    `password` varchar(50),

    PRIMARY KEY (`userID`)
);

CREATE TABLE `user_profile`
(
    `userID` int,
    `firstName` varchar(50),
    `lastName` varchar(50),
    `checkingBal` decimal(10,2),
    `savingsBal` decimal(10,2),

    PRIMARY KEY (`userID`),
    FOREIGN KEY `user_profile`(`userID`) REFERENCES `user`(`userID`)
);
