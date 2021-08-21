/* Create, Grant Account */

create user leafcom identified by tiger default tablespace users;
grant connect, resource, create view to leafcom;
grant create view to leafcom;
alter user jsp_88 account unlock;

/* Recyclebean */
PURGE RECYCLEBIN;

/* Drop Tables */

DROP TABLE item CASCADE CONSTRAINTS;
DROP TABLE members CASCADE CONSTRAINTS;
DROP TABLE post CASCADE CONSTRAINTS;


/* Create Tables */

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
	item_content varchar2(4000) NOT NULL,
	item_quantity number(3) NOT NULL,
	item_cost number(8) NOT NULL,
	item_price number(8) NOT NULL,
	item_grade number(1,2),
	PRIMARY KEY (item_id)
);

SELECT * FROM
    (SELECT item_id, category_id, item_name, item_company, item_small_img, item_large_img, item_detail_img, 
            item_detail_img, item_regdate, item_content, item_quantity, item_cost, item_price, item_grade,
            ROWNUM rNum
            FROM (SELECT * FROM item WHERE category_id = ? ORDER BY item_regdate DESC))
WHERE rNum >= ? AND rNum <=?;


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

DROP SEQUENCE post_num_seq;
CREATE SEQUENCE post_num_seq;
 START WITH 1
 INCREMENT BY 1
 MAXVALUE 999999;


INSERT INTO members VALUES('admin','admin','°ü¸®ÀÚ','leafcom@gmail.com','01000000000',sysdate,1,0);
commit;

SELECT * FROM members;
SELECT * FROM post;
SELECT * FROM item;


