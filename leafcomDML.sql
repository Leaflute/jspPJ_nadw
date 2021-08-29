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

DROP SEQUENCE powsup_num_seq;
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

INSERT INTO CART VALUES (1, 'test1234', 10000, 1, '2021-08-29 09:59:47.696', 0);

delete from members where me_id = 'admin';
INSERT INTO members VALUES('admin','admin','관리자','leafcom@gmail.com','01000000000',sysdate,1,1,'0');
commit; 