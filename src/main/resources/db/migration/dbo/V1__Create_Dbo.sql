create table dbo.Person (
    ID int not null AUTO_INCREMENT,
    NAME varchar(100) not null,
    PRIMARY KEY (ID)
);

create table dbo.Location (
    ID int not null AUTO_INCREMENT,
    NAME varchar(100) not null,
    PRIMARY KEY (ID)
);

create table dbo.EmploymentHistory (
    EMPLOYEENUM int not null AUTO_INCREMENT,
    PERSON int not null,
    WAGE float not null,
    LOCATION int not null,
    STARTDATE DATE not null,
    ENDDATE DATE null,
    PRIMARY KEY (EMPLOYEENUM),
    FOREIGN KEY (PERSON) REFERENCES dbo.Person(ID),
    FOREIGN KEY (LOCATION) REFERENCES dbo.Location(ID)
);

create table dbo.WorkSchedule (
    ID int not null AUTO_INCREMENT,
    PERSON int not null,
    DATE date not null,
    HOURS int not null,
    PRIMARY KEY (ID),
    FOREIGN KEY (PERSON) REFERENCES dbo.Person(ID)
);
