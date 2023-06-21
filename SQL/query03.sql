-- Retrieve the names of all counties, ordered by id,
-- that had either less than USD $1M of total payroll in "Real Estate"
-- or no data on that industry altogether.
-- 1.1 marks: <9 operators
-- 1.0 marks: <11 operators
-- 0.8 marks: correct answer
(
  SELECT c.name, c.fips
  FROM county c
  JOIN countyindustries ci ON c.fips = ci.county
  JOIN industry i ON ci.industry = i.id
  WHERE i.name LIKE '%Real Estate%'
  GROUP BY c.name, c.fips
  HAVING SUM(ci.payroll) < 1000000
)
UNION
(
  SELECT c.name, c.fips
  FROM county c
  WHERE NOT EXISTS (
    SELECT 1
    FROM countyindustries ci
    JOIN industry i ON ci.industry = i.id
    WHERE ci.county = c.fips AND i.name LIKE '%Real Estate%'
  )
)
ORDER BY fips