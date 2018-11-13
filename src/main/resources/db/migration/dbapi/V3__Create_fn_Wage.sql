DELIMITER $$

CREATE FUNCTION dbapi.fn_Wage(personname VARCHAR(100), dt DATE) RETURNS INT
    DETERMINISTIC
BEGIN
    RETURN (
      SELECT WAGE
      FROM dbo.EmploymentHistory
    );
END