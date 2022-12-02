CREATE TABLE if not exists priorities (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL,
   position INT
);

INSERT INTO priorities (name, position) VALUES ('urgently', 1);
INSERT INTO priorities (name, position) VALUES ('normal', 2);
INSERT INTO priorities (name, position) VALUES ('default', 3);

ALTER TABLE tasks ADD COLUMN priority_id int REFERENCES priorities(id);

UPDATE tasks SET priority_id = (SELECT id FROM priorities WHERE name = 'urgently');