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

create TABLE
    symbols (
        symbol TEXT UNIQUE
        )

insert into symbols (symbol) values ("doas");
insert into symbols (symbol) values ("garan");
insert into symbols (symbol) values ("isctr");
insert into symbols (symbol) values ("kchol");
insert into symbols (symbol) values ("pgsus");
insert into symbols (symbol) values ("sahol");
insert into symbols (symbol) values ("thyao");    

select * from doas
select * from isctr
select * from thyao
select * from person
select * from symbols
select * from last_operations
select * from portfolio


insert into doas (date, close_price) values("aaa", 32.5)
DELETE from doas where date = "aaa" and close_price = 32.5


create table 
    portfolio (
        symbol TEXT UNIQUE,
        amount INTEGER,
        avgPrice REAL
        )

create table
    last_operations (
        symbol TEXT,
        date TEXT,
        amount INTEGER,
        price REAL,
        side TEXT
        )

drop table last_operations
drop table portfolio

insert into last_operations (symbol, date, amount, price, side) values ("doas" , "01/01/2024" , 5 , 31.31 ,"BUY")
insert into portfolio (symbol, amount, avgPrice) values ("doas", 5 , 101.32)


select exists(select 1 from portfolio where symbol = "doas")