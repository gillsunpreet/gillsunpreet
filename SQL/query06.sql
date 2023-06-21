-- Retrieve alphabetically all pairs of counties (along with their ids)
-- that have the same name but voted for different parties
-- in 2020 Break any ties with the first county's id and
-- then the second county's id
-- 1.02 marks: <7 operators
-- 1.00 marks: <8 operators
-- 0.90 marks: <9 operators
-- 0.80 marks: correct answer
SELECT
	c1.fips,
    c1.name,
	c2.fips,
    c2.name
FROM 
	county c1 
JOIN
	county c2 on c1.name = c2.name and c1.fips < c2.fips
JOIN 
	electionresult e1 on c1.fips = e1.county
JOIN 
	electionresult e2 on c2.fips = e2.county
  
WHERE 
	((e1.dem > e1.gop AND e2.dem < e2.gop)
    OR (e1.dem < e1.gop AND e2.dem > e2.gop))
    AND e1.year = 2020 AND e2.year = 2020 
ORDER BY 
    -- CASE WHEN c1.name = c2.name THEN c1.fips END,
    c1.name, c1.fips, c2.fips;