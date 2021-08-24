/* Create, Grant Account */

create user leafcom identified by tiger default tablespace users;
grant connect, resource, create view to leafcom;
grant create view to leafcom;
alter user leafcom account unlock;

/* COMMIT */
SAVEPOINT s1;
COMMIT;

/* Recyclebean */
PURGE RECYCLEBIN;

/* Drop Tables */

DROP TABLE item CASCADE CONSTRAINTS;
DROP TABLE members CASCADE CONSTRAINTS;
DROP TABLE post CASCADE CONSTRAINTS;
DROP TABLE categories CASCADE CONSTRAINTS;

/* Create Tables */
CREATE TABLE categories
(
    category_id number(1),
    category_name varchar2(50),
    PRIMARY KEY (category_id)
);
truncate table categories;


CREATE TABLE item
(
	item_id number(5) NOT NULL,
	category_id number(1) NOT NULL,
	item_name varchar2(100) NOT NULL,
	item_company varchar2(50) NOT NULL,
	item_small_img varchar2(100) NOT NULL,
	item_large_img varchar2(100) NOT NULL,
	item_detail_img varchar2(100) NOT NULL,
	item_regdate timestamp DEFAULT SYSDATE NOT NULL,
	item_info varchar2(4000) NOT NULL,
	item_quantity number(3) NOT NULL,
	item_cost number(8) NOT NULL,
	item_price number(8) NOT NULL,
	item_grade number(3,2) DEFAULT 0,
	PRIMARY KEY (item_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE OR REPLACE VIEW item_v
AS
SELECT i.item_id, c.category_id, c.category_name, i.item_name, i.item_company, i.item_small_img, i.item_large_img, 
    i.item_detail_img, i.item_regdate, i.item_info, i.item_quantity, i.item_cost, i.item_price, i.item_grade, ROWNUM rNum
FROM item i JOIN categories c
ON i.category_id = c.category_id;

CREATE TABLE members
(
	mem_id varchar2(20) NOT NULL,
	mem_pw varchar2(20) NOT NULL,
	mem_name varchar2(40) NOT NULL,
	mem_email varchar2(60) NOT NULL,
	mem_phone varchar2(11) NOT NULL,
	mem_regdate timestamp NOT NULL,
	mem_role number(1) DEFAULT 0 NOT NULL,
	mem_condition number(1) DEFAULT 0 NOT NULL,
	PRIMARY KEY (mem_id)
);



CREATE TABLE post
(
	post_num number(6) NOT NULL,
	board_id number(5) NOT NULL,
	post_writer varchar2(60) NOT NULL,
	post_title varchar2(80) NOT NULL,
	post_content varchar2(4000) NOT NULL,
	post_regdate timestamp NOT NULL,
	post_hit number(6) DEFAULT 0 NOT NULL,
	post_ref number(2) DEFAULT 0,
	post_ref_level number(3) DEFAULT 0,
	post_ref_step number(3) DEFAULT 0,
	writer_ip varchar2(15) NOT NULL,
	post_condition number(1) DEFAULT 0 NOT NULL,
	PRIMARY KEY (post_num)
);

SELECT * FROM
    (SELECT rownum rNum, p.* 
       FROM (SELECT * 
               FROM post 
              WHERE bd_id = 1 
                AND po_ref IN (SELECT po_ref 
                                  FROM po 
                                 WHERE po_writer = 'test1234') 
              ORDER BY po_ref DESC, po_ref_step ASC) p) 
      WHERE rNum >= 1 AND rNum <= 4;

SELECT * FROM
    (SELECT rownum rNum, p.* 
       FROM (SELECT * 
               FROM post 
              WHERE bo_id = 1 
              ORDER BY po_ref DESC, po_ref_step ASC) p) 
      WHERE rNum >= 1 AND rNum <= 5;      


SELECT * FROM members;
SELECT * FROM post;
SELECT * FROM item;
SELECT * FROM categories;
SELECT * FROM item_v;
SELECT COUNT(*) cnt FROM item WHERE cg_id = 1;