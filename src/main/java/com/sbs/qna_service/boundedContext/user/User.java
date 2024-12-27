package com.sbs.qna_service.boundedContext.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 데이터베이스 테이블과 매핑되는 엔티티 클래스라는 것을 명시
@Data // Getter, Setter 등 메서드 제공
@NoArgsConstructor // 매개변수가 없는 생성자 생성
public class User {
    @Id // 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 자동으로 증가
    private Integer id;

    @Column(length = 10, nullable = false, unique = true) // 글자 수를 10으로 제한하고 null 값을 받지 않으며 고유해야함
    private String name;
}
