/* Recyclebean */
PURGE RECYCLEBIN;

-- 주문승인시 재고수량 변경(구매수량만큼 차감) 프로시저
DROP procedure item_stock_minus;
SET serveroutput ON
CREATE OR REPLACE PROCEDURE item_stock_minus(
    v_it_id IN orders.it_id%type,
    v_od_quantity IN orders.od_quantity%type
)
IS
BEGIN
    UPDATE item 
       SET it_stock = it_stock - v_od_quantity
     WHERE it_id = v_it_id;
END;
/

-- 환불승인시 재고수량 변경(수량만큼 증가) 프로시저
DROP procedure item_stock_plus;
SET serveroutput ON
CREATE OR REPLACE PROCEDURE item_stock_plus(
    v_it_id IN orders.it_id%type,
    v_od_quantity IN orders.od_quantity%type
)
IS
BEGIN
    UPDATE item 
       SET it_stock = it_stock + v_od_quantity
     WHERE it_id = v_it_id;
END;
/

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

DROP SEQUENCE mbd_num_seq;
CREATE SEQUENCE mbd_num_seq
 START WITH 30000
 INCREMENT BY 1
 MAXVALUE 39999;
 
DROP SEQUENCE gpu_num_seq;
CREATE SEQUENCE gpu_num_seq
 START WITH 40000
 INCREMENT BY 1
 MAXVALUE 49999;

DROP SEQUENCE pws_num_seq;
CREATE SEQUENCE pws_num_seq
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

DROP SEQUENCE cse_num_seq;
CREATE SEQUENCE cse_num_seq
 START WITH 80000
 INCREMENT BY 1
 MAXVALUE 89999;

DROP SEQUENCE mnt_num_seq;
CREATE SEQUENCE mnt_num_seq
 START WITH 90000
 INCREMENT BY 1
 MAXVALUE 99999;

DROP SEQUENCE po_num_seq;
CREATE SEQUENCE po_num_seq
 START WITH 1
 INCREMENT BY 1
 MAXVALUE 999999;

DROP SEQUENCE cart_num_seq;
CREATE SEQUENCE cart_num_seq
 START WITH 1
 INCREMENT BY 1
 CYCLE
 MAXVALUE 999999;
 
DROP SEQUENCE address_num_seq;
CREATE SEQUENCE address_num_seq
 START WITH 1
 INCREMENT BY 1
 CYCLE
 MAXVALUE 999999;
 
DROP SEQUENCE order_num_seq;
CREATE SEQUENCE order_num_seq
 START WITH 1
 INCREMENT BY 1
 CYCLE
 MAXVALUE 999999;
