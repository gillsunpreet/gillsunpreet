-- Retrieve for each state the average payroll
-- in the "mining" sector (total vs number of counties),
-- ordered by that average payroll
-- Hint: you may need the COALESCE function
-- 1.02 marks: <15 operators
-- 1.00 marks: <18 operators
-- 0.80 marks: correct answer

SELECT state.abbr, 
       CAST(COALESCE(SUM(countyindustries.payroll) 
       / (SELECT COUNT(*) FROM county 
       WHERE county.state = state.id), 0) AS SIGNED) AS AvgPayroll
       
FROM state
JOIN county ON state.id = county.state
LEFT JOIN countyindustries ON county.fips = countyindustries.county AND countyindustries.industry = (SELECT id FROM industry WHERE name LIKE '%Mining%')
GROUP BY state.abbr, county.state, state.id
ORDER BY Avgpayroll DESC;
