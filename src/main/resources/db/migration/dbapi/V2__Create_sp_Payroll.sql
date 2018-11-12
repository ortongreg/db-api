DELIMITER //
CREATE PROCEDURE dbapi.sp_Payroll
(IN startdt DATE)
BEGIN
  select P.NAME, '8' AS HOURS, '2018-11-09' AS END_DATE, '0' AS AMOUNT
  from dbo.Person P;
END //
DELIMITER ;
