-- Retrieve alphabetically the abbreviations of the states
-- in which one can find the ten counties that had the
-- largest (absolute) increase in employed persons
-- between 2008 and 2016.
-- 1.02 marks: <11 operators
-- 1.00 marks: <12 operators
-- 0.90 marks: <14 operators
-- 0.80 marks: correct answer

DROP TABLE IF EXISTS temp;

CREATE TEMPORARY TABLE temp AS (
  SELECT c.state
  FROM county c
  JOIN (
    SELECT l.county,
    ABS(SUM(CASE WHEN l.year = 2016 THEN l.employed ELSE 0 END) - SUM(CASE WHEN l.year = 2008 THEN l.employed ELSE 0 END)) AS employment_increase
    FROM countylabourstats l
    WHERE l.year IN (2008, 2016)
    GROUP BY l.county
    ORDER BY employment_increase DESC
    LIMIT 10
  ) t ON c.fips = t.county
);

SELECT s.abbr
FROM state s
WHERE s.id IN (
  SELECT state
  FROM temp
)
ORDER BY s.abbr;

DROP TABLE IF EXISTS temp;