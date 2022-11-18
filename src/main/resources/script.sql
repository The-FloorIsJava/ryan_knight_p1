create table profiles (
    username varchar(50) primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    password varchar(50) not null,
    admin bool default false
);

create type ticket_status as enum('pending','declined','approved');

create table tickets (
	ticket_id serial primary key,
	owner varchar(50) references profiles(username),
	amount numeric not null default 0,
	submission_date date not null default current_date,
	status ticket_status not null default 'pending'
);