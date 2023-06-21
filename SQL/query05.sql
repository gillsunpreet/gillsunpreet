-- Retrieve the name of all counties, ordered alphabetically, in Texas
-- that have seen at least 2.5% population growth every year on record
-- 1.02 marks: <9 operators
-- 1.00 marks: <12 operators
-- 0.80 marks: correct answer





WITH GrowthRate AS (
    SELECT cp1.county,
        ((CAST(cp1.population AS DECIMAL) - CAST(cp2.population AS DECIMAL)) / CAST(cp2.population AS DECIMAL)) * 100 AS growth_rate
    FROM countypopulation cp1
    JOIN countypopulation cp2
        -- cp1 is the year after cp2
        ON cp1.county = cp2.county AND cp1.year = cp2.year+1 
),
CoumtiesWithGrowth AS (
    SELECT county
    FROM GrowthRate 
    GROUP BY county
    HAVING min(growth_rate) >= 2.5
)
SELECT c.name AS "County"
FROM county c
JOIN state s ON c.state = s.id 
WHERE c.fips IN (SELECT county FROM CoumtiesWithGrowth)
	AND s.abbr = 'TX'
ORDER BY c.name;

