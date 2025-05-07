-- Insert roles if they don't exist
INSERT INTO roles (name) 
SELECT 'ROLE_USER' 
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_USER');

INSERT INTO roles (name) 
SELECT 'ROLE_ADMIN' 
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

-- Insert default admin user if no users exist
-- Password is 'admin'
INSERT INTO users (email, first_name, last_name, password) 
SELECT 'admin@example.com', 'Admin', 'User', '$2a$10$MBhcOgP4GdEV4Ty3FClb/uAzrS3qdWKCvMpW53V3q7QDq6V33RMMC' 
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

-- Assign admin role to admin user
INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@example.com' AND r.name = 'ROLE_ADMIN'
AND NOT EXISTS (
    SELECT 1 FROM users_roles ur 
    JOIN users u2 ON ur.user_id = u2.id 
    JOIN roles r2 ON ur.role_id = r2.id 
    WHERE u2.email = 'admin@example.com' AND r2.name = 'ROLE_ADMIN'
);

-- Insert default regular user
-- Password is 'user'
INSERT INTO users (email, first_name, last_name, password) 
SELECT 'user@example.com', 'Regular', 'User', '$2a$10$UU5pOhxqDa9q9kgK3X6XZeDQGonTU8WUtbyIWhsbGPEJvvv0TxYny' 
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'user@example.com');

-- Assign user role to regular user
INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'user@example.com' AND r.name = 'ROLE_USER'
AND NOT EXISTS (
    SELECT 1 FROM users_roles ur 
    JOIN users u2 ON ur.user_id = u2.id 
    JOIN roles r2 ON ur.role_id = r2.id 
    WHERE u2.email = 'user@example.com' AND r2.name = 'ROLE_USER'
); 