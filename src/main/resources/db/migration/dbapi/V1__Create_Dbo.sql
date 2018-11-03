create table dbo.Person (
    ID int not null,
    NAME varchar(100) not null,
    PRIMARY KEY (ID)
);

create table dbo.Employment (
    ID int not null,
    PERSON int not null,
    PRIMARY KEY (ID),
    FOREIGN KEY (PERSON) REFERENCES dbo.Person(ID)
);
