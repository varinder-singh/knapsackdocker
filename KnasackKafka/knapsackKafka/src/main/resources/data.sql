--CREATE SCHEMA IF NOT EXISTS OPTIMAL;



--DROP TABLE IF EXISTS KNAPSACK;

--CREATE TABLE KNAPSACK(
--    TASK_ID VARCHAR  PRIMARY KEY,
--    TASK_STATUS VARCHAR NOT NULL,
--    TASK_TIMESTAMPS CLOB ,
--    TASK_PROBLEM CLOB ,
--    TASK_SOLUTION CLOB
--
--);

--CREATE SEQUENCE IF NOT EXISTS knapsackSeq
--  START WITH 1
--  INCREMENT BY 1
--  ;

DELETE FROM KNAPSACK WHERE TASK_ID='default';

INSERT INTO KNAPSACK (TASK_ID, TASK_STATUS, TASK_TIMESTAMPS, TASK_PROBLEM, TASK_SOLUTION) VALUES('default',
'Submitted',
'{"submitted":1505225308, "started": null, "completed": null}',
'{"capacity": 60, "weights": [10, 20, 33], "values": [10, 3, 30]}',
'{"packedItems": [0, 2], "totalValue": 40}}');