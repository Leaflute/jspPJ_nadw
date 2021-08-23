/* Create, Grant Account */

create user leafcom identified by tiger default tablespace users;
grant connect, resource, create view to leafcom;
grant create view to leafcom;
alter user jsp_88 account unlock;

/* COMMIt */
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

INSERT INTO categories
VALUES(1, 'CPU');

INSERT INTO categories
VALUES(2, 'RAM');

INSERT INTO categories
VALUES(3, '메인보드');

INSERT INTO categories
VALUES(4, '그래픽카드');

INSERT INTO categories
VALUES(5, '파워서플라이');

INSERT INTO categories
VALUES(6, 'SSD');

INSERT INTO categories
VALUES(7, 'HDD');

INSERT INTO categories
VALUES(8, '케이스');

INSERT INTO categories
VALUES(9, '모니터');
COMMIT;

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

DELETE FROM item WHERE item_id = 10001;

CREATE OR REPLACE VIEW item_v
AS
SELECT i.item_id, c.category_id, c.category_name, i.item_name, i.item_company, i.item_small_img, i.item_large_img, 
    i.item_detail_img, i.item_regdate, i.item_info, i.item_quantity, i.item_cost, i.item_price, i.item_grade, ROWNUM rNum
FROM item i JOIN categories c
ON i.category_id = c.category_id;

WHERE rNum >= ? AND rNum <=?;
ORDER BY i.item_regdate DESC;

DROP SEQUENCE cpu_num_seq;
CREATE SEQUENCE cpu_num_seq
 START WITH 10000
 INCREMENT BY 1
 MAXVALUE 19999;

DROP SEQUENCE ram_num_seq;
CREATE SEQUENCE ram_num_seq
 START WITH 20000
 INCREMENT BY 1
 MAXVALUE 29999;

DROP SEQUENCE mb_num_seq;
CREATE SEQUENCE mb_num_seq
 START WITH 30000
 INCREMENT BY 1
 MAXVALUE 39999;
 
DROP SEQUENCE gpu_num_seq;
CREATE SEQUENCE gpu_num_seq
 START WITH 40000
 INCREMENT BY 1
 MAXVALUE 49999;

DROP SEQUENCE powsup_num_seq;
CREATE SEQUENCE powsup_num_seq
 START WITH 50000
 INCREMENT BY 1
 MAXVALUE 59999;

DROP SEQUENCE ssd_num_seq;
CREATE SEQUENCE ssd_num_seq
 START WITH 60000
 INCREMENT BY 1
 MAXVALUE 69999;

DROP SEQUENCE hdd_num_seq;
CREATE SEQUENCE hdd_num_seq
 START WITH 70000
 INCREMENT BY 1
 MAXVALUE 79999;

DROP SEQUENCE case_num_seq;
CREATE SEQUENCE case_num_seq
 START WITH 80000
 INCREMENT BY 1
 MAXVALUE 89999;

DROP SEQUENCE mon_num_seq;
CREATE SEQUENCE mon_num_seq
 START WITH 90000
 INCREMENT BY 1
 MAXVALUE 99999;
 
 

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

INSERT INTO members VALUES('admin','admin','관리자','leafcom@gmail.com','01000000000',sysdate,1,0);
commit;

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

SELECT * FROM
    (SELECT rownum rNum, p.* 
       FROM (SELECT * 
               FROM post 
              WHERE board_id = 1 
                AND post_ref IN (SELECT post_ref 
                                  FROM post 
                                 WHERE post_writer = 'test1234') 
              ORDER BY post_ref DESC, post_ref_step ASC) p) 
      WHERE rNum >= 1 AND rNum <= 4;

SELECT * FROM
    (SELECT rownum rNum, p.* 
       FROM (SELECT * 
               FROM post 
              WHERE board_id = 1 
              ORDER BY post_ref DESC, post_ref_step ASC) p) 
      WHERE rNum >= 1 AND rNum <= 5;      


SELECT * FROM members;
SELECT * FROM post;
SELECT * FROM item;
SELECT * FROM categories;
SELECT * FROM item_v;
SELECT COUNT(*) cnt FROM item WHERE category_id = 2;