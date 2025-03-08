CREATE TABLE reactions (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    likes INT DEFAULT 0,
    retweets INT DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

INSERT INTO reactions (post_id, likes, retweets)
SELECT id, 0, 0 FROM posts;
