--CREATE SCHEMA IF NOT EXISTS KNAPSACKDB;
--DELIMITED BY 'EOF';
--
--BEGIN
--   EXECUTE IMMEDIATE 'DROP TABLE ' || KNAPSACK;
--EXCEPTION
--   WHEN OTHERS THEN
--      IF SQLCODE != -942 THEN
--         RAISE;
--      END IF;
--END;
--
--EOF

CREATE TABLE KNAPSACK(
    TASK_ID VARCHAR(8)  PRIMARY KEY,
    TASK_STATUS VARCHAR(25) NOT NULL,
    TASK_TIMESTAMPS CLOB ,
    TASK_PROBLEM CLOB ,
    TASK_SOLUTION CLOB

);

CREATE SEQUENCE knapsackSeq INCREMENT BY 1 START WITH 1;

--CREATE SEQUENCE IF NOT EXISTS knapsackSeq
--  START WITH 1
--  INCREMENT BY 1
--  ;