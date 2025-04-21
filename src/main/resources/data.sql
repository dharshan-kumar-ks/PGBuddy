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


-- drop meal_name column from meals table
ALTER TABLE meal_votes DROP COLUMN user_id;

-- drop meal_meal_add_on table
DROP TABLE meal_votes;

-- populate meals table with dummy data for 6 rows
INSERT INTO meals (id, created_at, last_modified_at, meal_type, meal_image_url) VALUES
(1, '2025-03-31 10:00:00.000000', NULL, 'VEG', 'meals-1.jpg'),
(2, '2025-03-31 12:00:00.000000', NULL, 'VEG', 'meals-1.jpg'),
(3, '2025-03-31 18:00:00.000000', NULL, 'NONVEG', 'meals-1.jpg'),
(4, '2025-03-31 16:00:00.000000', NULL, 'VEG', 'meals-1.jpg'),
(5, '2025-03-31 11:00:00.000000', NULL, 'VEG', 'meals-1.jpg'),
(6, '2025-03-31 20:00:00.000000', NULL, 'NONVEG', 'meals-1.jpg');

-- populate meal_items table with dummy data for 6 rows
INSERT INTO meal_items (meal_id, meal_items) VALUES
(1, 'Rice'),
(1, 'Dal'),
(1, 'Vegetable Curry'),
(2, 'Rice'),
(2, 'Paneer Curry'),
(3, 'Chicken Curry'),
(4, 'Rice'),
(4, 'Vegetable Curry'),
(5, 'Rice'),
(5, 'Dal'),
(6, 'Chicken Curry');

-- populate meal_add_ons table with dummy data for 6 rows
INSERT INTO meal_add_on (meal_id, meal_add_on) VALUES
(1, 'Salad'),
(1, 'Raita'),
(2, 'Salad'),
(3, 'Raita'),
(4, 'Salad'),
(5, 'Raita'),
(6, 'Salad');

-- populate meal_votes table with dummy data for 6 rows
INSERT INTO meal_votes (id, created_at, last_modified_at, meal_date, meal_day_type, meal_time_type, meal_id) VALUES
(1, '2025-03-31 10:00:00.000000', NULL, '2025-03-31', 'MONDAY', 'BREAKFAST', 1),
(2, '2025-03-31 12:00:00.000000', NULL, '2025-03-31', 'MONDAY', 'LUNCH', 2),
(3, '2025-03-31 18:00:00.000000', NULL, '2025-03-31', 'MONDAY', 'DINNER', 3),
(4, '2025-04-01 10:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'BREAKFAST', 4),
(5, '2025-04-01 12:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'LUNCH', 5),
(6, '2025-04-01 18:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'DINNER', 6);


INSERT INTO meal_votes (id, created_at, last_modified_at, meal_date, meal_day_type, meal_time_type, meal_id) VALUES
(4, '2025-04-01 10:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'BREAKFAST', 4),
(5, '2025-04-01 12:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'LUNCH', 5),
(6, '2025-04-01 18:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'DINNER', 6),
(7, '2025-04-01 10:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'BREAKFAST', 1),
(8, '2025-04-01 12:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'LUNCH', 2),
(9, '2025-04-01 18:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'DINNER', 3),
(10, '2025-04-01 10:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'BREAKFAST', 2),
(11, '2025-04-01 12:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'LUNCH', 3),
(12, '2025-04-01 18:00:00.000000', NULL, '2025-04-01', 'TUESDAY', 'DINNER', 4),
(13, '2025-04-02 10:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'BREAKFAST', 1),
(14, '2025-04-02 12:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'LUNCH', 2),
(15, '2025-04-02 18:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'DINNER', 3),
(16, '2025-04-02 10:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'BREAKFAST', 4),
(17, '2025-04-02 12:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'LUNCH', 5),
(18, '2025-04-02 18:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'DINNER', 6),
(19, '2025-04-02 10:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'BREAKFAST', 2),
(20, '2025-04-02 12:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'LUNCH', 3),
(21, '2025-04-02 18:00:00.000000', NULL, '2025-04-02', 'WEDNESDAY', 'DINNER', 4),
(22, '2025-04-03 10:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'BREAKFAST', 4),
(23, '2025-04-03 12:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'LUNCH', 5),
(24, '2025-04-03 18:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'DINNER', 6),
(25, '2025-04-03 10:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'BREAKFAST', 1),
(26, '2025-04-03 12:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'LUNCH', 2),
(27, '2025-04-03 18:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'DINNER', 3),
(28, '2025-04-03 10:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'BREAKFAST', 2),
(29, '2025-04-03 12:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'LUNCH', 3),
(30, '2025-04-03 18:00:00.000000', NULL, '2025-04-03', 'THURSDAY', 'DINNER', 4),
(31, '2025-04-04 10:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'BREAKFAST', 1),
(32, '2025-04-04 12:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'LUNCH', 2),
(33, '2025-04-04 18:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'DINNER', 3),
(34, '2025-04-04 10:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'BREAKFAST', 4),
(35, '2025-04-04 12:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'LUNCH', 5),
(36, '2025-04-04 18:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'DINNER', 6),
(37, '2025-04-04 10:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'BREAKFAST', 2),
(38, '2025-04-04 12:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'LUNCH', 3),
(39, '2025-04-04 18:00:00.000000', NULL, '2025-04-04', 'FRIDAY', 'DINNER', 4),
(40, '2025-04-05 10:00:00.000000', NULL, '2025-04-05', 'SATURDAY', 'BREAKFAST', 4),
(41, '2025-04-05 12:00:00.000000', NULL, '2025-04-05', 'SATURDAY', 'LUNCH', 5),
(42, '2025-04-05 18:00:00.000000', NULL, '2025-04-05', 'SATURDAY', 'DINNER', 6),
(43, '2025-04-05 10:00:00.000000', NULL, '2025-04-05', 'SATURDAY', 'BREAKFAST', 1),
(44, '2025-04-05 12:00:00.000000', NULL, '2025-04-05', 'SATURDAY', 'LUNCH', 2),
(45, '2025-04-05 18:00:00.000000', NULL, '2025-04-05', 'SATURDAY', 'DINNER', 3);


INSERT INTO cafe_menu (id, created_at, last_modified_at, description, image_url, is_available, is_non_veg, is_spicy, name, price, total_orders) VALUES
(2, NOW(), NOW(), 'Beaten eggs, served plain or with filling.', '/meals-1.jpg', true, true, false, 'Omelette', 39, 0),
(3, NOW(), NOW(), 'Spicy chicken with garlic noodles.', '/meals-1.jpg', true, true, true, 'Chilli Chicken & Noodles', 135, 0),
(4, NOW(), NOW(), 'Garlic-flavored noodles with vegetables.', '/meals-1.jpg', true, false, false, 'Veg Garlic Noodles', 40, 0),
(5, NOW(), NOW(), 'Fluffy pancakes served with syrup.', '/meals-2.jpg', true, false, false, 'Pancakes', 50, 0),
(6, NOW(), NOW(), 'Grilled sandwich with cheese and vegetables.', '/meals-2.jpg', true, false, false, 'Grilled Sandwich', 60, 0),
(7, NOW(), NOW(), 'Creamy butter chicken served with naan.', '/meals-3.jpg', true, true, true, 'Butter Chicken', 150, 0),
(8, NOW(), NOW(), 'Aromatic rice with mixed vegetables.', '/meals-3.jpg', true, false, false, 'Veg Biryani', 120, 0),
(9, NOW(), NOW(), 'Spiced rice with tender chicken pieces.', '/meals-4.jpg', true, true, true, 'Chicken Biryani', 140, 0),
(10, NOW(), NOW(), 'Fresh lettuce with Caesar dressing.', '/meals-4.jpg', true, false, false, 'Caesar Salad', 80, 0),
(11, NOW(), NOW(), 'Classic pizza with cheese and tomato.', '/meals-5.jpg', true, false, false, 'Margarita Pizza', 100, 0),
(12, NOW(), NOW(), 'Pizza topped with pepperoni slices.', '/meals-5.jpg', true, true, true, 'Pepperoni Pizza', 120, 0),
(13, NOW(), NOW(), 'Burger with vegetable patty and cheese.', '/meals-6.jpg', true, false, false, 'Veg Burger', 70, 0),
(14, NOW(), NOW(), 'Burger with chicken patty and cheese.', '/meals-6.jpg', true, true, true, 'Chicken Burger', 90, 0),
(15, NOW(), NOW(), 'Crispy golden French fries.', '/meals-7.jpg', true, false, false, 'French Fries', 40, 0),
(16, NOW(), NOW(), 'Rich chocolate brownie with nuts.', '/meals-7.jpg', true, false, false, 'Chocolate Brownie', 60, 0);


SHOW INDEX FROM cafe_order_items;

DROP TABLE bookings_payments;
DROP TABLE payments;


