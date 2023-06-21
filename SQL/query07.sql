-- Retrieve the five states
-- that had the largest (relative) increase in
-- votes for the democrat party from 2016 to 2020
-- 1.02 marks: <9 operators
-- 1.00 marks: <11 operators
-- 0.80 marks: correct answer

SELECT 
    s2020.abbr AS "State",
    ((s2020.dem_votes - s2016.dem_votes) / s2016.dem_votes) AS VoteChange
FROM 
    (SELECT s.abbr, SUM(er.dem) AS dem_votes
    FROM state s
    JOIN county c ON s.id = c.state
    JOIN electionresult er ON c.fips = er.county
    WHERE er.year = 2020
    GROUP BY s.id) AS s2020
JOIN 
    (SELECT s.abbr, SUM(er.dem) AS dem_votes
    FROM state s
    JOIN county c ON s.id = c.state
    JOIN electionresult er ON c.fips = er.county
    WHERE er.year = 2016
    GROUP BY s.id) AS s2016
ON s2020.abbr = s2016.abbr
ORDER BY VoteChange DESC
LIMIT 5;
