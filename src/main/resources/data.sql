-- SQL queries to add data into the tables

-- Insert a dummy room into the rooms table
INSERT INTO rooms (number, room_type, current_capacity, available, created_at)
VALUES (101, 'NORMAL_SINGLE_SHARING', 1, false, NOW());

-- Insert dummy users into the users table
INSERT INTO users (name, email, password, phone_number, user_type, room_id, created_at)
VALUES
    ('Dharshan', 'dharshankumar55@gmail.com', 'test123', 999999999, 'RESIDENT', 1, NOW()),
    ('MBD', 'mbd@delhi.com', 'pass123', 888888888, 'ADMIN', 1, NOW());

