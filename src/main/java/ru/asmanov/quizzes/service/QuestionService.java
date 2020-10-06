package ru.asmanov.quizzes.service;

import org.springframework.stereotype.Service;
import ru.asmanov.quizzes.model.Question;
import ru.asmanov.quizzes.model.Quiz;

import java.util.List;

@Service
public interface QuestionService {
    Question findQuestionById(Long id);

    List<Question> findAllQuestion();

    void deleteQuestionById(Long id);

    void saveQuestion(Question question);

    List<Question> findQuestionsByQuizId(Long quizId);
}
