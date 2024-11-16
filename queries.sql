-- Part 1

-- Table job
-- id int AUTO_INCREMENT (AI) PK 
-- employer varchar(255) 
-- name varchar(255) 
-- skills varchar(255)

-- Part 2
--Write a query to list the names of the employers in St. Louis City.
--Do NOT specify an ordering for the query results.
SELECT name FROM employer WHERE location = "St. Louis City";


-- Part 3
DROP TABLE job;

-- Part 4
SELECT * FROM skill
INNER JOIN job_skills ON skill.id = job_skills.skills_id
WHERE job_skills.jobs_id IS NOT NULL
ORDER BY name ASC;
