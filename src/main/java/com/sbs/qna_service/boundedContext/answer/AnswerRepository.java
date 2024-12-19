package com.sbs.qna_service.boundedContext.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Modifying // INSERT, UPDATE, DELETE와 같이 데이터가 변경되는 작업에서만 사용
    @Transactional
    // nativeQuery = true여야 MySQL 쿼리 사용이 가능하다.
    @Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();
}
