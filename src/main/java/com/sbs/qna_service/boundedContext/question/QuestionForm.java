package com.sbs.qna_service.boundedContext.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionForm {
    private String subject;
    private String content;
}
