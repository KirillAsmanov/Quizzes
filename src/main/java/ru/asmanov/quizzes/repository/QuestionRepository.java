package ru.asmanov.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asmanov.quizzes.model.Question;

import java.util.List;

/**
 * Repository for Question
 * @author Kirill Asmanov
 * @since 16.10.2020
 */

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    /**
     * Finds all questions related to a specific quiz
     * @param quizId - id of quiz in database
     * @return list of finded questions
     */
    List<Question> findQuestionsByQuizId(Long quizId);
}
