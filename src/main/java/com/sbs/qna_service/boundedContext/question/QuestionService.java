package com.sbs.qna_service.boundedContext.question;

import com.sbs.qna_service.boundedContext.answer.Answer;
import com.sbs.qna_service.boundedContext.answer.AnswerRepository;
import com.sbs.qna_service.boundedContext.user.SiteUser;
import com.sbs.qna_service.exception.DataNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 작성일자 순으로 정렬
        
        // Pageable : 페이징 정보를 담는 객체
        // page : 요청된 페이지 번호 (0부터 시작)
        // 10 : 한 페이지에 표시할 데이터 개수
        // Sort.by(sorts) : 정렬 기준, 'createDate'를 기준으로 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10개씩+

        // 검색어가 없는 경우 유효성 검사, 일반 리스트를 출력
        if (kw == null || kw.trim().isEmpty()) {
            return questionRepository.findAll(pageable);
        }
        
        Specification<Question> spec = search(kw);
        return questionRepository.findAll(spec, pageable);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.addVoter(siteUser);
        questionRepository.save(question);
    }

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
}
