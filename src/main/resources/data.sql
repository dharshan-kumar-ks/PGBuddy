-- SQL queries to add data into the tables

/*
-- Insert a dummy room into the rooms table
INSERT INTO rooms (number, room_type, current_capacity, available, created_at)
VALUES (101, 'NORMAL_SINGLE_SHARING', 1, false, NOW());
*/

-- Insert dummy users into the users table
INSERT INTO users (name, email, password, phone_number, user_type, room_id, created_at)
VALUES
    ('Dharshan', 'dharshankumar55@gmail.com', 'test123', 999999999, 'RESIDENT', 1, NOW()),
    ('MBD', 'mbd@delhi.com', 'pass123', 888888888, 'ADMIN', 1, NOW());

-- Insert dummy notice into the notices table
INSERT INTO notices (id, created_at, last_modified_at, bookmarked, content, title, author_id) VALUES
(17, '2025-03-31 22:10:55.000000', NULL, false, NULL, 'Guest Speaker Session: Career Guidance at 6 PM', 1),
(16, '2025-03-31 21:05:50.000000', NULL, false, NULL, 'Festival Celebration: Decoration Competition at 5 PM', 1),
(15, '2025-03-31 20:00:45.000000', NULL, false, NULL, 'Library Update: New Books Added to the Collection', 1),
(14, '2025-03-30 11:55:35.000000', NULL, false, NULL, 'Health Checkup Camp: Free Checkups from 10 AM - 4 PM', 1),
(13, '2025-03-28 09:50:30.000000', NULL, false, NULL, 'Maintenance Work: Elevator Out of Service from 2 PM - 5 PM', 1),
(12, '2025-03-25 15:45:20.000000', NULL, false, NULL, 'Water Tank Cleaning: No Water Supply from 11 AM - 1 PM', 1),
(11, '2025-03-20 07:40:10.000000', NULL, false, NULL, 'Cultural Event: Music Night at 7 PM in the Common Hall', 1),
(10, '2025-03-15 18:35:05.000000', NULL, false, NULL, 'Newspaper Delivery Delay: Expected by 9 AM', 1),
(9, '2025-03-10 12:30:55.000000', NULL, false, NULL, 'Fire Drill Scheduled: Evacuation Practice at 4 PM', 1),
(8, '2025-03-05 17:25:50.000000', NULL, false, NULL, 'Gym Equipment Maintenance: Closed from 10 AM - 12 PM', 1),
(7, '2025-03-01 13:10:40.000000', NULL, false, NULL, 'Power Cut Notice: No Electricity from 1 PM - 2 PM Due to Maintenance', 1),
(6, '2025-02-25 08:05:35.000000', NULL, false, NULL, 'New PG Rules: Lights-Out Extended to 11:30 PM on Weekends', 1),
(5, '2025-02-20 11:50:25.000000', NULL, false, NULL, 'WiFi Maintenance: Network Downtime from 2 AM - 4 AM', 1),
(4, '2025-02-15 16:45:15.000000', NULL, false, NULL, 'Laundry Service Delay: Collection at 6 PM Instead of 5 PM', 1),
(3, '2025-02-10 09:30:00.000000', NULL, false, NULL, 'Room Cleaning Schedule: 2nd Floor Cleaning at 3 PM', 1),
(2, '2025-02-05 14:20:45.000000', NULL, false, NULL, 'Hot Water Supply Interruption: 9 AM - 11 AM', 1),
(1, '2025-02-01 10:15:30.000000', NULL, false, NULL, 'Dinner Menu Update: Special Paneer Dish Added Tonight', 1);