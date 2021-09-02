-- 게시판
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

-- 게시판 
SELECT * FROM
    (SELECT rownum rNum, p.* 
       FROM (SELECT * 
               FROM post 
              WHERE bo_id = 1 
              ORDER BY po_ref DESC, po_ref_step ASC) p) 
      WHERE rNum >= 1 AND rNum <= 5;   

-- orders view
CREATE OR REPLACE VIEW order_v
AS
SELECT rownum rnum,
       o.od_id, 
       o.me_id,
       o.it_id,
       o.ad_id,
       o.od_quantity,
       o.od_regDate,
       o.od_condition,
       i.it_price,
       i.it_name,
       i.it_small_img
  FROM orders o, item i
 WHERE o.it_id = i.it_id;

-- item view
CREATE OR REPLACE VIEW item_v
AS
SELECT i.it_id, 
       c.cg_id, 
       c.cg_name, 
       i.it_name, 
       i.it_company, 
       i.it_small_img, 
       i.it_large_img, 
       i.it_detail_img, 
       i.it_regdate, 
       i.it_info, 
       i.it_stock, 
       i.it_cost, 
       i.it_price, 
       i.it_grade, 
       ROWNUM rNum
  FROM item i JOIN categories c
    ON i.cg_id = c.cg_id;
 

-- 오늘부터 5일 전 매출액과 영업이익 날짜별로 구하기
SELECT SUM(i.it_price * o.od_quantity) sales, 
       SUM((i.it_price - i.it_cost) * o.od_quantity) margin,
       TO_CHAR(o.od_regdate,'yyyy') year,
       TO_CHAR(o.od_regdate,'mm') month,
       TO_CHAR(o.od_regdate,'dd') day
  FROM orders o, item i
 WHERE o.it_id = i.it_id
   AND o.od_condition = 9
   AND o.od_regdate BETWEEN sysdate-5 AND sysdate
 GROUP BY o.od_regdate(o.od_regdate,'yyyy-mm-dd'); 

SELECT * FROM user_sequences; 
SELECT * FROM cart;
SELECT * FROM orders;
SELECT * FROM members;
SELECT * FROM address;
SELECT * FROM post;
SELECT * FROM item;
SELECT * FROM categories;
SELECT * FROM item_v;
SELECT * FROM order_v; 