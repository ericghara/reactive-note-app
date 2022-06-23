
CREATE TABLE to_do_lists (
    list_id bigserial PRIMARY KEY,
    list_name varchar(255),
    user_id bigint
);

CREATE TABLE to_do_items(
    item_id bigserial PRIMARY KEY,
    list_id bigint REFERENCES to_do_lists ON DELETE CASCADE,
    item_index integer NOT NULL,
    item_text text NOT NULL
);
