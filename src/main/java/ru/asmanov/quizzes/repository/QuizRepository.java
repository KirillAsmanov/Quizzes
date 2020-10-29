package ru.asmanov.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asmanov.quizzes.model.Quiz;

/**
 * Repository for Quiz
 * @author Kirill Asmanov
 * @since 16.10.2020
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
