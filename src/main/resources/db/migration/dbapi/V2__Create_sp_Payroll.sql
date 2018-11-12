DELIMITER //
CREATE PROCEDURE dbapi.sp_Payroll
(IN startdt DATE)
BEGIN


  select P.NAME,
    (SELECT SUM(HOURS) FROM dbo.WorkSchedule) AS HOURS,
    '2018-11-09' AS END_DATE,
    (SELECT WAGE FROM dbo.EmploymentHistory)*(SELECT SUM(HOURS) FROM dbo.WorkSchedule) AS AMOUNT
  from dbo.Person P;

END //
DELIMITER ;
