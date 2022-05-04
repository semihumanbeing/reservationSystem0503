CREATE TABLE RESERVE
(
	CFMNUMBER INT,
	NAME VARCHAR2(100),
	ROOMNUMBER NUMBER(4)
)

ALTER TABLE RESERVE
	ADD CONSTRAINT RESERVE_CFMNUMBER_PK PRIMARY KEY(CFMNUMBER)
	

ALTER TABLE RESERVE
	ADD CONSTRAINT RESERVE_ROOMNUMBER_FK FOREIGN KEY(ROOMNUMBER)
	REFERENCES HOTEL(ROOMNUMBER)
	

SELECT * FROM RESERVE