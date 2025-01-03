package com.sbs.qna_service.boundedContext.question;

import com.sbs.qna_service.boundedContext.answer.AnswerRepository;
import com.sbs.qna_service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> oq = this.questionRepository.findById(id);

        if(oq.isEmpty()) throw new DataNotFoundException("question not found");

        return oq.get();
    }

    public Question create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);

        return q;
    }

    public Page<Question> getList(int page) {
        // Pageable : 페이징 정보를 담는 객체
        Pageable pageable = PageRequest.of(page, 10); // 한 페이지에 10개씩
        return questionRepository.findAll(pageable);
    }
}
