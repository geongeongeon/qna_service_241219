package com.sbs.qna_service.boundedContext.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Entity // 스트링부트가 Question을 Entity로 인식
public class Question {
    @Id // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Integer id; // INT id

    @Column(length = 200) // VARCHAR(200)
    private String subject;

    @Column(columnDefinition = "TEXT") // TEXT
    private String content;

    private LocalDateTime createDate; // DATETIME
}
