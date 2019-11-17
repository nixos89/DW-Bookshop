CREATE TABLE product (
    product_id serial PRIMARY KEY,  -- implicit primary key constraint
    product    text NOT NULL,
    price      numeric NOT NULL DEFAULT 0
);

CREATE TABLE bill (
    bill_id  serial PRIMARY KEY,
    bill     text NOT NULL,
    billdate date NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE bill_product (
    bill_id    int REFERENCES bill (bill_id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id int REFERENCES product (product_id) ON UPDATE CASCADE,
    amount     numeric NOT NULL DEFAULT 1,
    CONSTRAINT bill_product_pkey PRIMARY KEY (bill_id, product_id)  -- explicit pk
);