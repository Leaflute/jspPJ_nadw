/* COMMIT */
SAVEPOINT s1;
COMMIT;

INSERT INTO categories
VALUES(1, 'CPU');

INSERT INTO categories
VALUES(2, 'RAM');

INSERT INTO categories
VALUES(3, '���κ���');

INSERT INTO categories
VALUES(4, '�׷���ī��');

INSERT INTO categories
VALUES(5, '�Ŀ����ö���');

INSERT INTO categories
VALUES(6, 'SSD');

INSERT INTO categories
VALUES(7, 'HDD');

INSERT INTO categories
VALUES(8, '���̽�');

INSERT INTO categories
VALUES(9, '�����');
COMMIT;

INsert INTO board
VALUES(1, '', '������');

INSERT INTO CART VALUES (1, 'test1234', 10000, 1, '2021-08-29 09:59:47.696', 0);

delete from members where me_id = 'admin';
INSERT INTO members VALUES('admin','admin','������','leafcom@gmail.com','01000000000',sysdate,1,1,'0');
commit; 