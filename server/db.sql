#mysql --host=mysql-vt2013.csc.kth.se  --user=dexteradmin --password=B3E2RaX5

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
    deadline timestamp,
	PRIMARY KEY (id)
);

create table list_items (
	list_id int NOT NULL,
	content varchar(64) NOT NULL,
	checked boolean
);

create table collaborators (
	uid int NOT NULL,
	list_id int NOT NULL,
	date_created timestamp NOT NULL
);

INSERT INTO users (name, password) VALUES ('Dexter', 'bananpaj');
INSERT INTO users (name, password) VALUES ('Annica', 'ilovecats');
INSERT INTO users (name, password) VALUES ('Putin', 'iloveukraine');

INSERT INTO lists (title, author, last_change, deadline) VALUES ('Party shopping', 1, '2015-01-01 00:00:01');
INSERT INTO lists (title, author, last_change, deadline) VALUES ('Party shopping', 1, '2016-01-01 00:00:01');
INSERT INTO lists (title, author, last_change, deadline) VALUES ('Party shopping', 2, '2017-01-01 00:00:01');
