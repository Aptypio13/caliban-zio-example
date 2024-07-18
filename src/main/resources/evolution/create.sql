-- Creating the 'users' table
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       birthday DATE NOT NULL,
                       films_id BIGINT[], -- Storing list of film IDs as a comma-separated string
                       role VARCHAR(255) NOT NULL -- Assuming 'role' can be represented as a string
);

-- Creating the 'films' table
CREATE TABLE films (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       rating INT, -- Optional field
                       description VARCHAR(255) -- Optional field
);
