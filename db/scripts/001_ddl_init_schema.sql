DROP TABLE IF EXISTS tasks;

CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);