package com.sbs.qna_service.boundedContext.question;

import com.sbs.qna_service.boundedContext.answer.AnswerRepository;
import com.sbs.qna_service.boundedContext.user.SiteUser;
import com.sbs.qna_service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Question create(String subject, String content, SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setAuthor(author);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);

        return q;
    }

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 작성일자 순으로 정렬

        // Pageable : 페이징 정보를 담는 객체
        // page : 요청된 페이지 번호 (0부터 시작)
        // 10 : 한 페이지에 표시할 데이터 개수
        // Sort.by(sorts) : 정렬 기준, 'createDate'를 기준으로 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10개씩
        return questionRepository.findAll(pageable);
    }
}
