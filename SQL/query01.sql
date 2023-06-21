-- Retrieve the name of all counties, ordered alphabetically,
-- that had a six-figure average income and voted Republican in 2020
-- 1.1 marks: < 8 operators
-- 1.0 marks: < 9 operators
-- 0.8 marks: correct answer
SELECT c.name 
FROM county c
JOIN electionresult er ON c.fips = er.county
WHERE c.avg_income >= 100000
	AND er.gop > er.dem
    AND er.year = 2020
ORDER BY c.name;