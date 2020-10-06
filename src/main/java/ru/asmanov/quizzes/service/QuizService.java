package ru.asmanov.quizzes.service;

import ru.asmanov.quizzes.model.Quiz;

import java.util.List;

public interface QuizService {
    public Quiz findQuizById(Long id);

    public List<Quiz> findAllQuizzes();

    public void deleteQuizById(Long id);

    public void saveQuiz(Quiz quiz);
}
