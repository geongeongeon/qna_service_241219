# DB 삭제, 생성, 선택
DROP DATABASE IF EXISTS qna_service;
CREATE DATABASE qna_service;
USE qna_service;

// 외래키 제약 비활성화
SET FOREIGN_KEY_CHECKS = 0;

// TRUNCATE로 초기화한 데이터는 데이터 추가 시 1번부터 시작한다.
TRUNCATE question;
TRUNCATE answer;

// 외래키 제약 활성화
SET FOREIGN_KEY_CHECKS = 1;