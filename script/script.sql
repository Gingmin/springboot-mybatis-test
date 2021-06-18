DROP TABLE IF EXISTS TEST;
DROP TABLE IF EXISTS MEMBER;

CREATE TABLE TEST(
	TESTINT INTEGER
);

CREATE TABLE MEMBER(
	NO INTEGER AUTO_INCREMENT PRIMARY KEY COMMENT '사번',
	ID VARCHAR(50) NOT NULL UNIQUE COMMENT '아이디',
	PWD VARCHAR(255) NOT NULL COMMENT '비밀번호',
	ENROLLDATE DATE DEFAULT CURRENT_TIMESTAMP COMMENT '가입일' 
);

INSERT INTO test
(
  TESTINT
)
VALUES
(
  20
);
