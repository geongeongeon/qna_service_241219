package com.sbs.qna_service.boundedContext.user;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받아서 User 엔티티를 관리하는 리포지터리 생성
public interface UserRepository extends JpaRepository<User, Integer> {
}