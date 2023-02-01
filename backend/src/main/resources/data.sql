INSERT INTO application_user (id, email, username, password, role, active)
VALUES
  (1, 'john.doe@example.com', 'johndoe', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','ADMIN', true),
  (2, 'jane.doe@example.com', 'janedoe', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (3, 'bob.smith@example.com', 'bobsmith', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (4, 'alice.johnson@example.com', 'alicejohnson', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (5, 'tom.jones@example.com', 'tomjones', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (6, 'sarah.lee@example.com', 'sarahlee', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (7, 'mike.brown@example.com', 'mikebrown', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (8, 'emily.davis@example.com', 'emilydavis', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (9, 'david.miller@example.com', 'davidmiller', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (10, 'jennifer.williams@example.com', 'jenniferwilliams', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (11, 'joseph.taylor@example.com', 'josephtaylor', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (12, 'margaret.johnson@example.com', 'margaretjohnson', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (13, 'andrew.lee@example.com', 'andrewlee', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (14, 'susan.jones@example.com', 'susanjones', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (15, 'paul.smith@example.com', 'paulsmith', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (16, 'jessica.davis@example.com', 'jessicadavis', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (17, 'charles.miller@example.com', 'charlesmiller', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (18, 'karen.williams@example.com', 'karenwilliams', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (19, 'brian.taylor@example.com', 'briantaylor', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true),
  (20, 'emma.johnson@example.com', 'emmajohnson', '$2a$10$XE2p91vD0ltPUorOCpJqbeTRQBnmJBsOX4QBA2uzZ.sHAncqYGCP6','USER', true);

INSERT INTO coffee_bean (id, name, price, origin, height, coffee_roast, bean_blend, url_to_coffee, description, strength, user_id)
VALUES
  (1, 'Elevation Blend', 12.99, 'Ethiopia and Brazil', 900, 'MEDIUM', 'Blend of Arabica and Robusta beans', 'https://www.elevationblend.com', 'A well-balanced medium roast coffee with bright acidity, sweet and fruit flavors', 'Balanced', 1),
  (2, 'Night Owl', 10.99, 'Colombia', 1000, 'DARK', '100% Arabica Beans', 'https://www.nightowlcoffee.com', 'A dark and bold coffee with hints of chocolate and a full body', 'Strong', 1),
  (3, 'Early Bird Espresso', 15.99, 'Ethiopia and Indonesia', 1100, 'ESPRESSO', 'Blend of Arabica and Robusta Beans', 'https://www.earlybirdespresso.com', 'A smooth and rich espresso with a fruity aroma and a dark chocolate aftertaste', 'Intense', 2),
  (4, 'Sumatra Tiger', 13.99, 'Sumatra, Indonesia', 1200, 'DARK', '100% Sumatra Beans', 'https://www.sumatratiger.com', 'A full-bodied dark roast with notes of earthy and herbal flavors', 'Robust', 3),
  (5, 'African Sunrise', 12.99, 'Kenya, Ethiopia', 1000, 'LIGHT', '100% African Beans', 'https://www.africansunrise.com', 'A bright and light roast with hints of lemon and berry flavors', 'Mild', 4),
  (6, 'Java Jolt', 11.99, 'Java, Indonesia and Colombia', 950, 'MEDIUM', 'Blend of Indonesian and South American Beans', 'https://www.javajolt.com', 'A medium roast coffee with a bold flavor and a smooth finish', 'Balanced', 5),
  (7, 'Aloha Kona', 16.99, 'Kona, Hawaii', 900, 'MEDIUM', '100% Kona Beans', 'https://www.alohakona.com', 'A medium roast coffee with a nutty and chocolate flavor', 'Balanced', 6),
  (8, 'Columbian Peak', 14.99, 'Colombia', 1100, 'MEDIUM', '100% Colombian Beans', 'https://www.columbianpeak.com', 'A medium roast coffee with a well-rounded flavor and a bright acidity', 'Balanced', 7),
  (9, 'Mexican Mocha', 12.99, 'Mexico and Colombia', 950, 'DARK', 'Blend of Mexican and South American Beans', 'https://www.mexicanmocha.com', 'A dark roast coffee with hints of chocolate and caramel flavors', 'Strong', 8),
  (10, 'Peruvian Puro', 13.99, 'Peru', 1000, 'LIGHT', '100% Peruvian Beans', 'https://www.peruvianpuro.com', 'A light roast coffee with a sweet and fruity flavor', 'Mild', 9),
  (11, 'Bali Blue Moon', 15.99, 'Bali, Indonesia', 1100, 'MEDIUM', '100% Bali Beans', 'https://www.balibluedark.com', 'A medium roast coffee with a smooth and creamy flavor', 'Balanced', 10),
  (12, 'Brazilian Bold', 12.99, 'Brazil', 900, 'DARK', '100% Brazilian Beans', 'https://www.brazilianbold.com', 'A dark roast coffee with a strong and bold flavor', 'Robust', 11),
  (13, 'Java Bean', 12.99, 'Indonesia', 45, 'DARK', 'Arabian, Robusta', 'https://coffeebean.com/14', 'Rich and full-bodied coffee with notes of dark chocolate and roasted nuts', 'Strong', 10),
  (14, 'Colombian Supreme', 14.49, 'Colombia', 48, 'MEDIUM', 'Colombian Arabica', 'https://coffeebean.com/15', 'Balanced and smooth with a mild acidity and sweet fruity notes', 'Balanced', 20),
  (15, 'Kenyan AA', 16.99, 'Kenya', 50, 'LIGHT', 'Kenyan Arabica', 'https://coffeebean.com/16', 'Crisp and bright with a citrus acidity and wine-like flavors', 'Robust', 11),
  (16, 'Ethiopian Yirgacheffe', 15.99, 'Ethiopia', 52, 'LIGHT', 'Ethiopian Arabica', 'https://coffeebean.com/17', 'Distinctive floral and fruity flavor profile with a light body and delicate acidity', 'Robust', 6),
  (17, 'Sumatra Mandheling', 14.49, 'Indonesia', 55, 'DARK', 'Sumatra Arabica', 'https://coffeebean.com/18', 'Intense and earthy with a low acidity and notes of dark chocolate and spice', 'Strong', 17),
  (18, 'Guatemalan Antigua', 13.99, 'Guatemala', 48, 'MEDIUM', 'Guatemalan Arabica', 'https://coffeebean.com/19', 'Mild and sweet with a medium body and nutty flavors', 'Mild', 8),
  (19, 'Mexican Chiapas', 12.99, 'Mexico', 45, 'MEDIUM', 'Mexican Arabica', 'https://coffeebean.com/20', 'Mild and smooth with a chocolatey flavor and low acidity', 'Mild', 4),
  (20, 'Peruvian Pangoa', 12.49, 'Peru', 50, 'LIGHT', 'Peruvian Arabica', 'https://coffeebean.com/21', 'Bright and clean with a delicate acidity and citrusy notes', 'Mild', 13),
  (21, 'Brazilian Santos', 13.99, 'Brazil', 52, 'MEDIUM', 'Brazilian Arabica', 'https://coffeebean.com/22', 'Mild and sweet with a medium body and nutty flavor', 'Mild', 19),
  (22, 'Nicaraguan Segovia', 14.99, 'Nicaragua', 55, 'MEDIUM', 'Nicaraguan Arabica', 'https://coffeebean.com/23', 'Balanced and smooth with a medium body and chocolatey flavors', 'Balanced', 2),
  (23, 'Honduran Comayagua', 12.99, 'Honduras', 45, 'LIGHT', 'Honduran Arabica', 'https://coffeebean.com/24', 'Crisp and bright with a medium body and citrusy notes', 'Strong', 12),
  (24, 'Papua New Guinean Kimel', 15.49, 'Papua New Guinea', 48, 'MEDIUM', 'Papua New Guinean Arabica', 'https://coffeebean.com/25', 'Balanced and smooth with a medium body and nutty flavors', 'Balanced', 5),
  (25, 'Costa Rican Tarrazu', 16.99, 'Costa Rica', 50, 'LIGHT', 'Costa Rican Arabica', 'https://coffeebean.com/26', 'Bright and crisp with a medium body and citrusy notes', 'Mild', 15),
  (26, 'Jamaican Blue Mountain', 19.99, 'Jamaica', 52, 'LIGHT', 'Jamaican Arabica', 'https://coffeebean.com/27', 'Mild and sweet with a medium body and bright, fruity flavors', 'Mild', 7),
  (27, 'El Salvadorian Santa Ana', 14.99, 'El Salvador', 55, 'MEDIUM', 'El Salvadorian Arabica', 'https://coffeebean.com/28', 'Balanced and smooth with a medium body and nutty flavors', 'Balanced', 9),
  (28, 'Vietnamese Weasel', 18.99, 'Vietnam', 45, 'DARK', 'Vietnamese Robusta', 'https://coffeebean.com/29', 'Intense and earthy with a full body and notes of dark chocolate and spices', 'Strong', 16),
  (29, 'Tanzanian Peaberry', 17.49, 'Tanzania', 48, 'MEDIUM', 'Tanzanian Arabica', 'https://coffeebean.com/30', 'Mild and sweet with a medium body and fruity flavors', 'Mild', 3);

INSERT INTO coffee_extraction (id, extraction_date, brew_method, grind_setting, water_temperature, dose, water_amount, brew_time, body, acidity, aromatics, sweetness, aftertaste, coffee_bean_id)
VALUES
  (1, '2023-01-20T08:30:00', 'DRIP', 'MEDIUM', 96.0, 18.0, 300.0, 225000, 3, 2, 4, 3, 2, 1),
  (2, '2023-01-20T11:45:00', 'DRIP', 'FINE', 96.0, 18.0, 300.0, 225000, 3, 2, 3, 2, 2, 1),
  (3, '2023-01-21T08:00:00', 'DRIP', 'MEDIUM_COARSE', 96.0, 17.0, 290.0, 215000, 2, 3, 4, 2, 2, 1),
  (4, '2023-01-21T13:30:00', 'DRIP', 'MEDIUM_FINE', 96.0, 17.0, 290.0, 215000, 3, 3, 3, 3, 3, 1),
  (5, '2023-01-21T17:00:00', 'DRIP', 'EXTRA_FINE', 96.0, 18.0, 300.0, 225000, 2, 2, 3, 3, 2, 1),
  (6, '2023-01-22T09:00:00', 'DRIP', 'COARSE', 96.0, 18.0, 300.0, 225000, 3, 3, 3, 2, 2, 1),
  (7, '2023-01-22T14:30:00', 'DRIP', 'EXTRA_FINE', 96.0, 17.0, 290.0, 215000, 2, 2, 2, 2, 2, 1),
  (8, '2023-01-22T18:00:00', 'DRIP', 'MEDIUM_FINE', 96.0, 18.0, 300.0, 225000, 3, 2, 2, 2, 2, 1),
  (9, '2023-01-23T07:30:00', 'DRIP', 'MEDIUM_COARSE', 96.0, 18.0, 300.0, 225000, 3, 3, 3, 2, 2, 1),
  (10, '2023-01-23T12:00:00', 'DRIP', 'COARSE', 96.0, 17.0, 290.0, 215000, 2, 2, 2, 2, 2, 1),
  (11, '2023-01-24T07:00:01', 'DRIP', 'COARSE', 96.0, 16.0, 360.0, 300000, 3, 2, 4, 5, 1, 1),
  (12, '2023-01-24T10:30:00', 'DRIP', 'MEDIUM_FINE', 96.0, 18.0, 400.0, 310000, 3, 3, 5, 4, 1, 1),
  (13, '2023-01-24T14:00:01', 'DRIP', 'FINE', 96.0, 17.0, 450.0, 290000, 4, 4, 4, 5, 2, 1),
  (14, '2023-01-25T07:00:01', 'DRIP', 'MEDIUM_COARSE', 96.0, 16.0, 360.0, 290000, 2, 2, 3, 4, 1, 1),
  (15, '2023-01-25T10:30:00', 'DRIP', 'MEDIUM', 96.0, 18.0, 400.0, 320000, 3, 3, 4, 5, 2, 1),
  (16, '2023-01-25T14:00:01', 'DRIP', 'EXTRA_FINE', 96.0, 17.0, 450.0, 310000, 4, 4, 5, 5, 3, 1),
  (17, '2023-01-26T07:00:01', 'DRIP', 'MEDIUM_COARSE', 96.0, 16.0, 360.0, 300000, 2, 2, 3, 4, 1, 1),
  (18, '2023-01-26T10:30:00', 'DRIP', 'MEDIUM', 96.0, 18.0, 400.0, 290000, 3, 3, 4, 5, 2, 1),
  (19, '2023-01-26T14:00:01', 'DRIP', 'EXTRA_FINE', 96.0, 17.0, 450.0, 320000, 4, 4, 5, 5, 3, 1),
  (20, '2023-01-27T07:00:01', 'DRIP', 'COARSE', 96.0, 16.0, 360.0, 310000, 3, 2, 4, 5, 1, 1),
  (21, '2023-01-27T05:30:00', 'DRIP', 'MEDIUM', 96.7, 20.0, 250.0, 30000, 4, 4, 4, 5, 2, 1),
  (22, '2023-01-27T07:45:00', 'DRIP', 'FINE', 96.7, 22.0, 300.0, 32000, 2, 2, 3, 4, 1, 1),
  (23, '2023-01-27T10:00:00', 'DRIP', 'EXTRA_FINE', 96.7, 25.0, 350.0, 35000, 3, 3, 4, 5, 2, 1),
  (25, '2023-01-27T14:30:00', 'DRIP', 'MEDIUM_COARSE', 96.7, 18.0, 225.0, 28000, 2, 2, 3, 4, 1, 1),
  (26, '2023-01-27T16:45:00', 'DRIP', 'MEDIUM', 96.7, 20.0, 250.0, 30000, 3, 3, 4, 5, 2, 1),
  (27, '2023-01-27T19:00:00', 'DRIP', 'MEDIUM_FINE', 96.7, 22.0, 275.0, 32000, 4, 4, 5, 5, 3, 1),
  (28, '2023-01-27T21:15:00', 'DRIP', 'FINE', 96.7, 25.0, 300.0, 35000, 3, 2, 4, 5, 1, 1),
  (29, '2023-01-27T23:30:00', 'DRIP', 'EXTRA_FINE', 96.7, 28.0, 325.0, 38000, 4, 4, 4, 5, 2, 1),
  (30, '2023-01-28T05:34:02', 'DRIP', 'COARSE', 92.0, 20.0, 250.0, 3602000, 4, 2, 4, 4, 4, 1),
  (31, '2023-01-29T09:45:12', 'DRIP', 'MEDIUM', 92.0, 22.0, 300.0, 4003000, 3, 3, 3, 3, 3, 1),
  (32, '2023-01-29T14:00:06', 'DRIP', 'MEDIUM_FINE', 93.0, 21.0, 200.0, 4503000, 2, 2, 2, 2, 2, 1),
  (33, '2023-01-30T16:15:34', 'DRIP', 'FINE', 93.5, 20.0, 300.0, 3102000, 1, 1, 1, 1, 1, 1),
  (34, '2023-01-31T19:35:00', 'DRIP', 'EXTRA_FINE', 93.0, 19.0, 250.0, 2901000, 5, 5, 5, 5, 5, 1),
  (35, '2023-02-01T07:45:00', 'DRIP', 'FINE', 96.7, 22.0, 300.0, 32000, 2, 2, 3, 4, 1, 1),
  (36, '2023-02-01T10:00:00', 'DRIP', 'EXTRA_FINE', 96.7, 25.0, 350.0, 35000, 3, 3, 4, 5, 2, 1),
  (37, '2023-02-02T09:45:12', 'DRIP', 'MEDIUM', 92.0, 22.0, 300.0, 4003000, 3, 3, 3, 3, 3, 1),
  (38, '2023-01-23T08:23:45', 'DRIP', 'MEDIUM_COARSE', 96.7, 45063, 300, 70000, 4, 3, 4, 4, 4, 2),
  (39, '2023-01-23T10:11:12', 'DRIP', 'MEDIUM', 96.2, 45155, 325, 67000, 3, 2, 3, 3, 3, 2),
  (40, '2023-01-23T15:30:50', 'DRIP', 'FINE', 95.6, 44975, 275, 72000, 2, 2, 2, 2, 2, 2),
  (41, '2023-01-24T07:35:12', 'DRIP', 'EXTRA_FINE', 96.9, 18.0, 250, 71000, 4, 4, 4, 4, 4, 2),
  (42, '2023-01-24T09:52:30', 'DRIP', 'COARSE', 96.4, 14, 325, 69000, 5, 5, 5, 5, 5, 2),
  (43, '2023-01-24T14:01:01', 'DRIP', 'MEDIUM_FINE', 96.1, 15, 300, 73000, 3, 3, 3, 3, 3, 2),
  (44, '2023-01-25T08:23:45', 'DRIP', 'MEDIUM_COARSE', 96.7, 16, 300, 70000, 4, 3, 4, 4, 4, 2),
  (45, '2023-01-25T10:11:12', 'DRIP', 'MEDIUM', 96.2, 15, 325, 67000, 3, 2, 3, 3, 3, 2),
  (46, '2023-01-25T15:30:50', 'DRIP', 'FINE', 95.6, 15, 275, 72000, 2, 2, 2, 2, 2, 2),
  (47, '2023-01-26T07:35:12', 'DRIP', 'EXTRA_FINE', 96.9, 18.0, 250, 71000, 4, 4, 4, 4, 4, 2),
  (49, '2023-01-26T14:01:01', 'DRIP', 'MEDIUM_FINE', 96.1, 14, 300, 73000, 3, 3, 3, 3, 3, 2),
  (50, '2023-01-27T08:23:45', 'DRIP', 'MEDIUM_COARSE', 96.7, 16, 300, 70000, 4, 3, 4, 4, 4, 2),
  (51, '2023-01-27T10:11:12', 'DRIP', 'MEDIUM', 96.2, 15, 325, 67000, 3, 2, 3, 3, 3, 2),
  (52, '2023-01-27T15:30:50', 'DRIP', 'FINE', 95.6, 14, 275, 72000, 2, 2, 2, 2, 2, 2);

INSERT INTO coffee_extraction (id, extraction_date, brew_method, grind_setting, water_temperature, dose, water_amount, brew_time, body, acidity, aromatics, sweetness, aftertaste, coffee_bean_id, rating_notes, recipe_steps)
VALUES
  (53, '2023-01-28T08:00:00', 'DRIP', 'MEDIUM', 96.7, 18.5, 340, 6000, 4,3, 5, 2, 4, 21, 'I like this particular extraction because it creates a flavorful and balanced cup of coffee with a rich body, bright acidity, and a sweet and aromatic finish.', '<ol><li><b>Choose coffee bean:</b> Brazilian Arabica</li><li><b>Grind coffee beans:</b> Grind coffee beans to desired grind setting. For this recipe, we will use "MEDIUM_FINE" grind setting</li><li><b>Heat water:</b> Heat water to desired temperature. For this recipe, we will use 90.5°C</li><li><b>Measure dose:</b> Measure out 18.0 grams of coffee for this recipe</li><li><b>Add coffee to brew method:</b> Add coffee to the brewing method of your choice. For this recipe, we will use the drip method</li><li><b>Pour water:</b> Slowly pour heated water over coffee, making sure to wet all the coffee evenly. Use 235 ml of water</li><li><b>Brew coffee:</b> Let coffee brew for about 3 minutes</li></ol><p>Feel free to adjust the recipe to your personal preferences.</p>'),
  (48, '2023-01-26T09:52:30', 'DRIP', 'COARSE', 96.4, 14, 325, 69000, 5, 5, 5, 5, 5, 2, 'I loved the Night Owl brew for its bold, smooth and rich flavor with a perfect balance of body, sweetness and a subtle aftertaste that lingered on my palate.', '<ol><li>Grind 14g of coffee beans to a coarse grind</li><li>Place a coffee filter into a drip coffee maker</li><li>Pour the ground coffee into the filter</li><li>Heat 325ml of water to 96.4°C</li><li>Slowly pour the water over the coffee grounds in the filter, ensuring an even distribution</li><li>Allow the coffee to brew for 6 minutes</li><li>Enjoy your delicious Night Owl brew!</li></ol>'),
  (24, '2023-01-27T12:15:00', 'DRIP', 'COARSE', 96.7, 15.0, 200.0, 25000, 4, 4, 5, 5, 3, 1, 'The Elevation Blend recipe is known for its rich, full-bodied flavor with balanced acidity, hints of dark chocolate and a smooth finish, making it a well-rounded and enjoyable coffee experience for many', null);

INSERT INTO coffee_recipe (id, shared, extraction_id)
VALUES
  (1, true, 53),
  (2, true, 48),
  (3, false, 24);

INSERT INTO recipe_rating (recipe_id, rating, text, timestamp, user_id)
VALUES
  (1, 5, 'This recipe is fantastic and so easy to follow, 5 stars!', '2023-02-01T12:00:00Z', 3),
  (1, 5, 'The flavors were spot on and it was the perfect dish for a dinner party', '2023-02-01T14:00:00Z', 8),
  (1, 3, 'This recipe is a great starting point but I added a few of my own twists to make it my own', '2023-02-01T16:00:00Z', 10),
  (1, 2, 'I was disappointed with this recipe as it didn''t turn out as I expected, 2 stars', '2023-02-01T18:00:00Z', 15),
  (1, 4, 'This recipe was quick, simple and delicious. Highly recommend, 4 stars', '2023-02-01T20:00:00Z', 1);

INSERT INTO recipe_rating (recipe_id, rating, timestamp, user_id)
VALUES
  (2, 5, '2023-02-01T12:00:00Z', 4),
  (2, 4, '2023-02-01T13:00:00Z', 7);