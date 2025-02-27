ALTER TABLE users 
ADD COLUMN username VARCHAR(110) NOT NULL;

UPDATE users 
SET username = CONCAT(REPLACE(name, ' ', ''), 'Xo');



