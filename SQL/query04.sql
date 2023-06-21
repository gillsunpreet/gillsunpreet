-- Consider the industry with the most employees
-- nationwide. Retrieve alphabetically all counties
-- that have fewer than 10 employees in that industry
-- (ignoring those with no data on it).
-- 1.02 marks: <10 operators
-- 1.00 marks: <12 operators
-- 0.80 marks: correct answer
WITH max_industry AS (
    SELECT industry 
    FROM countyindustries  
    GROUP BY industry
    ORDER BY SUM(employees) DESC
    LIMIT 1
)
SELECT c.name as "County"
FROM county c
INNER JOIN countyindustries ci 
    ON c.fips = ci.county 
    AND ci.industry = (SELECT industry FROM max_industry)
WHERE ci.employees < 10
ORDER BY c.name;




