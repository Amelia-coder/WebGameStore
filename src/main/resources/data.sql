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