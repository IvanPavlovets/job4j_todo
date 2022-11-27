create table if not exists categories (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL
);

create table if not exists tasks_categories (
    id SERIAL PRIMARY KEY,
    task_id INT NOT NULL REFERENCES tasks(id),
    category_id INT NOT NULL REFERENCES categories(id)
);