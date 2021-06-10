SET FOREIGN_KEY_CHECKS = 0;

truncate table post;
truncate table author;
truncate table comment;
truncate table author_posts;

INSERT INTO post(id, title, content, date_created)
VALUES (41, 'Title post 1', 'Post content1', '2021-06-03T11:57:03'),
       (42, 'Title post 2', 'Post content2', '2021-06-03T12:57:03'),
       (43, 'Title post 3', 'Post content3', '2021-06-03T01:57:03');

SET FOREIGN_KEY_CHECKS = 1;