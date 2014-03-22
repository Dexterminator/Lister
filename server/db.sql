#mysql --host=mysql-vt2013.csc.kth.se  --user=dexteradmin --password=B3E2RaX5
-- select * from lists where id IN (SELECT list_id FROM collaborators WHERE uid=3);
use dexter; # Byt till din egen

drop table users; # Radera om redan finns
drop table lists;
drop table list_items;
drop table collaborators;

create table users (
	id int NOT NULL AUTO_INCREMENT,
    name varchar(64),
    password varchar(64),
	PRIMARY KEY (id)
);

create table lists (
	id int NOT NULL AUTO_INCREMENT,
    title varchar(64) NOT NULL,
    author varchar(64) NOT NULL,
    last_change timestamp,
    deadline timestamp NULL DEFAULT NULL,
	PRIMARY KEY (id)
);

create table list_items (
	id int NOT NULL AUTO_INCREMENT,
	list_id int NOT NULL,
	content varchar(64) NOT NULL,
	checked boolean NOT NULL,
	PRIMARY KEY (id)
);

create table collaborators (
	uid int NOT NULL,
	list_id int NOT NULL,
	date_created timestamp NOT NULL,
	FOREIGN KEY (uid) REFERENCES users (id),
	FOREIGN KEY (list_id) REFERENCES lists (id)
);

INSERT INTO users (name, password) VALUES ('qwe', 'qwe');
INSERT INTO users (name, password) VALUES ('Annica', 'ilovecats');
INSERT INTO users (name, password) VALUES ('Francis', 'power');

INSERT INTO lists (title, author, last_change, deadline) VALUES ('Groceries', 1, '2014-01-01 00:00:01', '2015-01-01 00:00:01');
INSERT INTO lists (title, author, last_change, deadline) VALUES ('Packing list', 1, '2014-01-01 00:00:01', '2015-01-01 00:00:01');
INSERT INTO lists (title, author, last_change, deadline) VALUES ('Cleaning', 1, '2014-01-01 00:00:01', '2015-01-01 00:00:01');
INSERT INTO lists (title, author, last_change, deadline) VALUES ('Beefcake workout', 2, '2014-01-01 00:00:01', '2015-01-01 00:00:01');
INSERT INTO lists (title, author, last_change, deadline) VALUES ('Become president', 3, '2014-01-01 00:00:01', '2015-01-01 00:00:01');

INSERT INTO list_items (list_id, content, checked) VALUES (1, 'eggs', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'milk', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'beer', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'butter', true);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'bread', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'jam', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'beef', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'tomatoes', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'potatoes', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'lettuce', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'bacon', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'meatballs', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'pasta', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'rice', false);
INSERT INTO list_items (list_id, content, checked) VALUES (1, 'salmon', false);

INSERT INTO list_items (list_id, content, checked) VALUES (2, 'toothbrush', false);
INSERT INTO list_items (list_id, content, checked) VALUES (2, 'pillow', true);
INSERT INTO list_items (list_id, content, checked) VALUES (2, 'passport', true);
INSERT INTO list_items (list_id, content, checked) VALUES (2, 'sunscreen', false);

INSERT INTO list_items (list_id, content, checked) VALUES (3, 'kitchen', false);
INSERT INTO list_items (list_id, content, checked) VALUES (3, 'bathroom', false);
INSERT INTO list_items (list_id, content, checked) VALUES (3, 'living room', false);

INSERT INTO list_items (list_id, content, checked) VALUES (4, 'situps', false);
INSERT INTO list_items (list_id, content, checked) VALUES (4, 'pushups', false);
INSERT INTO list_items (list_id, content, checked) VALUES (4, 'chinups', false);
INSERT INTO list_items (list_id, content, checked) VALUES (4, 'cardio', false);
INSERT INTO list_items (list_id, content, checked) VALUES (4, 'squats', false);

INSERT INTO list_items (list_id, content, checked) VALUES (5, 'Become vice president', false);

INSERT INTO collaborators (uid, list_id, date_created) VALUES (1, 1, '2014-01-01 00:00:01');
INSERT INTO collaborators (uid, list_id, date_created) VALUES (1, 2, '2014-01-01 00:00:01');
INSERT INTO collaborators (uid, list_id, date_created) VALUES (1, 3, '2014-01-01 00:00:01');
INSERT INTO collaborators (uid, list_id, date_created) VALUES (2, 4, '2014-01-01 00:00:01');
INSERT INTO collaborators (uid, list_id, date_created) VALUES (3, 5, '2014-01-01 00:00:01');
INSERT INTO collaborators (uid, list_id, date_created) VALUES (1, 5, '2014-01-01 00:00:01');
