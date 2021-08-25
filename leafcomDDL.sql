/* Create, Grant Account */
drop user leafcom cascade;
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

ALTER TABLE address
RENAME COLUMN add_recipient to ad_recipient; 

CREATE OR REPLACE VIEW item_v
AS
SELECT i.it_id, c.cg_id, c.cg_name, i.it_name, i.it_company, i.it_small_img, i.it_large_img, 
    i.it_detail_img, i.it_regdate, i.it_info, i.it_stock, i.it_cost, i.it_price, i.it_grade, ROWNUM rNum
FROM item i JOIN categories c
ON i.cg_id = c.cg_id;

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