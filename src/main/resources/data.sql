-- Insert admin user if not exists
INSERT INTO users (username, email, password, role)
SELECT 'admin', 'admin@gamestore.com', '$2a$10$dL4az.HhQJ0UGT.xN3.wR.oGr8Jg1oG3VCxDEKxYG6vSk.OEpxKIS', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- The password for admin is 'admin123' (hashed with BCrypt) 