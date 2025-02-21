CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
)

INSERT INTO users (id, name, email, password, date_of_birth, created_at, updated_at) VALUES
(1, 'Abbas', 'abbasgame@gmail.com', '$2a$10$bIL/5G0xL3tCZEIjMKZxXudRglfwGfSGkBbvQwB2D8A1V3YRyzgPS', '1947-12-27', '2024-12-13 10:46:48', '2024-12-13 10:46:48'),
(2, 'John Doe', 'johndoe@example.com', '$10$ZUNxj1bRvLQUsL5KfNkpuemGzBOrAOD4JQiwlDk7eYbRkiHSR3pAe', '1990-01-01', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
(3, 'Jane Smith', 'janesmith@example.com', '$2a$10$PQT9e9Hhft7bsf/FNw3pWec4/jB34kN8RKuBC9K4LvHpkPvE.oHvu', '1992-05-15', '2023-03-01 10:00:00', '2023-03-01 10:00:00'),
(4, 'Bob Johnson', 'bobjohnson@example.com', '$10$8TyCfEDxd2/H2ppzCV1nAeVYtOnDrhRm/03Cgnj9uhHlvPLTW9cQ2', '1985-07-20', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
(5, 'Alice Brown', 'alicebrown@example.com', '$2a$10$yJ8.rFdz.PG/FGKJfL1Im2QEu3JZGqH5T6HKmO4ENHwvFqUnEqPEa', '2000-08-30', '2023-02-01 10:00:00', '2023-02-01 10:00:00'),
(6, 'Charlie White', 'charliewhite@example.com', '$10$C7K7yBsU94.VW2.nHGRbcueZBHiP/GZFE5lAZ7EWo0CFtx1r/A2Oe', '2003-03-25', '2023-03-25 10:00:00', '2023-03-25 10:00:00'),
(7, 'Abbas Nassar', 'HelloFighter@gmail.com', '$10$BVu/OgJIZhsdurOG.Fpl8OWOhGvvPTOIoQDpoqI3EQOJ9P63Vo.Im', '1981-09-18', '2025-02-19 15:46:47', '2025-02-19 15:46:47');


CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    retweet_id INT DEFAULT -1,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE posts
ADD COLUMN retweet_id INT DEFAULT -1;

INSERT INTO posts (user_id, content, created_at, updated_at, retweet_id) VALUES 
(1, 'This is the first dummy post.', '2024-12-20 10:00:00', '2024-12-20 12:00:00'),
(1, 'Another post by the same user.', '2024-12-20 11:00:00', '2024-12-20 13:00:00'),
(2, 'This is a post by user 2.', '2024-12-21 09:00:00', '2024-12-21 11:00:00'),
(3, 'User 3 has written this post.', '2024-12-21 10:00:00', '2024-12-21 12:00:00'),
(2, 'Another contribution by user 2.', '2024-12-21 08:00:00', '2024-12-21 10:00:00'),
(4, 'Post by a new user, user 4.', '2024-12-22 14:00:00', '2024-12-22 16:00:00');