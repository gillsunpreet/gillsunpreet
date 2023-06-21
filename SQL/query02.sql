-- Order by total payroll all states
-- that have fewer than 50 counties
-- and at least ten industries of payroll data
-- 1.02 marks: <11 operators
-- 1.00 marks: <12 operators
-- 0.90 marks: <14 operators
-- 0.80 marks: correct answer


SELECT s.abbr, SUM(ci.payroll) AS "State Payroll"
FROM state s
JOIN county c ON s.id = c.state
JOIN (
    SELECT county, COUNT(DISTINCT industry) AS industry_count
    FROM countyindustries
    WHERE payroll IS NOT NULL
    GROUP BY county
    HAVING COUNT(DISTINCT industry) >= 10
) filtered_counties ON c.fips = filtered_counties.county
JOIN countyindustries ci ON c.fips = ci.county
GROUP BY s.id
HAVING COUNT(DISTINCT c.fips) < 50
ORDER BY SUM(ci.payroll) DESC;
