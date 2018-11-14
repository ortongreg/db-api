DELIMITER $$

CREATE FUNCTION dbapi.fn_Wage(personname VARCHAR(100)) RETURNS FLOAT
    DETERMINISTIC
BEGIN
    DECLARE PID INT;
    SET PID = (SELECT P.ID FROM dbo.Person P WHERE personname = P.NAME);
    RETURN (
      SELECT EH.WAGE
      FROM dbo.EmploymentHistory EH
      WHERE EH.PERSON = PID
    );
END