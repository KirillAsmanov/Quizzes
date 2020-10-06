package ru.asmanov.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asmanov.quizzes.model.Quiz;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
