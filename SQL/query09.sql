-- Show all counties ordered by their total
-- number of employees across all industries Break any ties with the first county's id and
-- then the second county's id
-- 1.1 marks: <5 operators
-- 1.0 marks: <6 operators
-- 0.8 marks: correct answer
-- COALESCE(t.total_employees, 0) AS total_employees

SELECT c.*
FROM county c
LEFT JOIN (
    SELECT ci.county, SUM(ci.employees) AS total_employees
    FROM countyindustries ci
    GROUP BY ci.county
) t ON t.county = c.fips
WHERE t.total_employees IS NOT NULL
ORDER BY t.total_employees, c.fips;

	
    



	 

