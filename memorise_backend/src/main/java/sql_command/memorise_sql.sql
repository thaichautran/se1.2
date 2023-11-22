use memorise;

create table role(
	id int auto_increment,
    role_name varchar(20),
    create_date timestamp,
    
    primary key(id)
);


create table user(
	id int auto_increment,
    username varchar(100),
    password varchar(255),
    name varchar(100), 
    avatar text,
    create_date timestamp,
    update_date timestamp,
    role_id int,
    
    primary key(id)
);

create table image(
	id int auto_increment,
    url text,
    description varchar(255),
    name varchar(100),
    favourite varchar(255),
    sharing_url text,
    location varchar(255),
    create_date timestamp,
    update_date timestamp,
    is_public boolean,
    is_remove boolean,
    user_id int,
    
    primary key(id)
    
);

create table album(
	id int auto_increment,
    name varchar(100),
    description varchar(255),
    create_date timestamp,
    update_date timestamp,
    cover_photo text,
    is_public boolean,
    sharing_url text,
    user_id int,
    
    primary key(id)
);

create table image_album(
	album_id int,
    image_id int,
    
    primary key(album_id, image_id)
);

alter table user add constraint fk_user_role_id foreign key(role_id) references role(id);
alter table album add constraint fk_album_user_id foreign key(user_id) references user(id);
alter table image add constraint fk_image_user_id foreign key(user_id) references user(id);
alter table image_album add constraint fk_image_album_album_id foreign key(album_id) references album(id);
alter table image_album add constraint fk_image_album_image_id foreign key(image_id) references image(id);