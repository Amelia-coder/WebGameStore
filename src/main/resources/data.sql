-- Insert admin user if not exists
INSERT INTO users (username, email, password, registration_date)
SELECT 'admin', 'admin@gamestore.com', '$2a$10$dL4az.HhQJ0UGT.xN3.wR.oGr8Jg1oG3VCxDEKxYG6vSk.OEpxKIS', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- Insert admin role for admin user
INSERT INTO user_roles (user_id, role)
SELECT u.id, 'ADMIN'
FROM users u
WHERE u.username = 'admin'
AND NOT EXISTS (
    SELECT 1 FROM user_roles ur 
    WHERE ur.user_id = u.id AND ur.role = 'ADMIN'
);

-- Insert USER role for admin user (admin should have both roles)
INSERT INTO user_roles (user_id, role)
SELECT u.id, 'USER'
FROM users u
WHERE u.username = 'admin'
AND NOT EXISTS (
    SELECT 1 FROM user_roles ur 
    WHERE ur.user_id = u.id AND ur.role = 'USER'
);

-- The password for admin is 'admin123' (hashed with BCrypt)

-- Insert publishers if they don't exist
INSERT INTO publishers (name, description, founded_year, website_url)
SELECT 'Valve', 'Video game publisher and digital distribution company.', 1996, 'https://www.valvesoftware.com'
WHERE NOT EXISTS (SELECT 1 FROM publishers WHERE name = 'Valve');

INSERT INTO publishers (name, description, founded_year, website_url)
SELECT 'CD Projekt', 'Polish video game publisher and distributor.', 1994, 'https://www.cdprojekt.com'
WHERE NOT EXISTS (SELECT 1 FROM publishers WHERE name = 'CD Projekt');

-- Insert developers if they don't exist
INSERT INTO developers (name, description, founded_year, website_url)
SELECT 'Valve Corporation', 'American video game developer, publisher, and digital distribution company.', 1996, 'https://www.valvesoftware.com'
WHERE NOT EXISTS (SELECT 1 FROM developers WHERE name = 'Valve Corporation');

INSERT INTO developers (name, description, founded_year, website_url)
SELECT 'CD Projekt Red', 'Polish video game developer, publisher and distributor.', 1994, 'https://www.cdprojektred.com'
WHERE NOT EXISTS (SELECT 1 FROM developers WHERE name = 'CD Projekt Red');

-- Insert genres if they don't exist
INSERT INTO genres (name, description)
SELECT 'ACTION', 'Action games focus on physical challenges that require hand-eye coordination and motor skills.'
WHERE NOT EXISTS (SELECT 1 FROM genres WHERE name = 'ACTION');

INSERT INTO genres (name, description)
SELECT 'RPG', 'Role-playing games where players assume the roles of characters in a fictional setting.'
WHERE NOT EXISTS (SELECT 1 FROM genres WHERE name = 'RPG');

INSERT INTO genres (name, description)
SELECT 'STRATEGY', 'Strategy games focus on skillful thinking and planning to achieve victory.'
WHERE NOT EXISTS (SELECT 1 FROM genres WHERE name = 'STRATEGY'); 