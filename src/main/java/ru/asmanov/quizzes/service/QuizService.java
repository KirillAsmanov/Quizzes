package ru.asmanov.quizzes.service;

import org.springframework.stereotype.Service;
import ru.asmanov.quizzes.model.Quiz;

import java.util.List;

@Service
public interface QuizService {
    Quiz findQuizById(Long id);

    List<Quiz> findAllQuizzes();

    void deleteQuizById(Long id);

    void saveQuiz(Quiz quiz);
}
