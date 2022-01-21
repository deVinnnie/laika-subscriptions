create table subscription_event(
    id bigint not null,
    count bigint not null,
    timestamp timestamp not null,
    subscription_list_id bigint not null,

    primary key(id)
);
