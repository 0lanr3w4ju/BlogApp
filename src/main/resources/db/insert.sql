SET FOREIGN_KEY_CHECKS = 0;

truncate table post;
truncate table author;
truncate table comment;
truncate table author_posts;

INSERT INTO post(id, title, content)
VALUES (41, 'Title post 1', 'Post content1'),
       (42, 'Title post 2', 'Post content2'),
       (43, 'Title post 3', 'Post content3');

SET FOREIGN_KEY_CHECKS = 1;