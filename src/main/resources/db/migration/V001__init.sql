create table subscription_list(
    id bigint not null,
    title varchar(255) unique not null,

    primary key(id)
);

create table subscriber(
    id bigint not null,
    email varchar(255) not null,
    voornaam varchar(255),
    achternaam varchar(255),
    subscription_date timestamp without time zone,
    token varchar(16),
    active boolean,

    primary key(id)
);

create table subscription_list_subscribers(
    subscriptions_id bigint not null,
    subscribers_id bigint not null,

    primary key(subscriptions_id, subscribers_id),
    constraint fk_subscription_list
        foreign key (subscriptions_id)
        references subscription_list(id),
    constraint fk_subscribers
        foreign key (subscribers_id)
        references subscriber(id)
);

create sequence hibernate_sequence
increment 1
start 400; -- Prevent collision with existing data
