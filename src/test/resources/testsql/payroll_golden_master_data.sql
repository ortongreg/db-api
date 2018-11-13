DELETE FROM dbo.EmploymentHistory;
DELETE FROM dbo.WorkSchedule;
DELETE FROM dbo.Location;
DELETE FROM dbo.Person;

INSERT INTO dbo.Location (ID, NAME) VALUES (1, 'homebase');

INSERT INTO dbo.Person (ID, NAME) VALUES (1, 'Bob Jones');
INSERT INTO dbo.WorkSchedule (PERSON, DATE, HOURS) VALUES (1, '2018-11-02', 8);
INSERT INTO dbo.EmploymentHistory (PERSON, WAGE, LOCATION, STARTDATE) VALUES (1, 20, 1, '2018-10-26');

INSERT INTO dbo.Person (ID, NAME) VALUES (2, 'Sally Smith');
INSERT INTO dbo.WorkSchedule (PERSON, DATE, HOURS) VALUES (2, '2018-11-04', 4);
INSERT INTO dbo.EmploymentHistory (PERSON, WAGE, LOCATION, STARTDATE) VALUES (2, 22, 1, '2017-01-01');