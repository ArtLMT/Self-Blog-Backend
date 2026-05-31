-- Update admin password to the new hash
UPDATE users 
SET password = '$2a$10$uUB.ZJgsbKVuNQN1Yfud/OJKLAOcsNzxiXyHlLiXfFU9wOuU4o772' 
WHERE username = 'admin';
