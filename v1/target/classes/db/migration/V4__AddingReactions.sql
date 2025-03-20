CREATE TABLE reactions (
    post_id INT PRIMARY KEY,
    likes INT DEFAULT 0,
    retweets INT DEFAULT 0,
    comments INT DEFAULT 0,
    views INT DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

INSERT INTO reactions (post_id, likes, retweets, comments, views)
VALUES
  (1, 150, 30, 0, 1500),
  (2, 300, 45, 0, 3000),
  (3, 75, 15, 0, 800),
  (4, 980, 220, 0, 12000),
  (5, 420, 90, 0, 4500),
  (6, 650, 150, 0, 7000),
  (7, 230, 60, 0, 2500),
  (8, 800, 180, 0, 9500),
  (9, 50, 5, 0, 600),
  (10, 1200, 350, 0, 15000),
  (11, 350, 75, 0, 3800),
  (12, 90, 20, 0, 1000);