select * from person


SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';

CREATE TABLE 
deneme (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    age INTEGER,
    email TEXT UNIQUE
)

drop table deneme

CREATE TABLE
 doas ( 
    date TEXT UNIQUE,
    close_price REAL
    )

drop table doas

select * from doas
select * from isctr
select * from thyao
select * from person


insert into doas (date, close_price) values("aaa", 32.5)
DELETE from doas where date = "aaa" and close_price = 32.5
