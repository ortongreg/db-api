DELIMITER //
CREATE PROCEDURE dbapi.sp_Payroll
(IN startdt DATE, IN enddt DATE)
BEGIN


  select P.NAME,
    (SELECT SUM(HOURS) FROM dbo.WorkSchedule WS WHERE WS.PERSON = P.ID) AS HOURS,
    startdt AS START_DATE,
    enddt AS END_DATE,
    (SELECT WAGE FROM dbo.EmploymentHistory EH WHERE EH.PERSON = P.ID)*(SELECT SUM(HOURS) FROM dbo.WorkSchedule WS WHERE WS.PERSON = P.ID) AS AMOUNT
  from dbo.Person P;

END //
DELIMITER ;
