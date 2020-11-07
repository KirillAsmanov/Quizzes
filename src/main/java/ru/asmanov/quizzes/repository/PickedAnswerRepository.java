package ru.asmanov.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asmanov.quizzes.model.PickedAnswer;

/**
 * Repository for picked answers
 * @author Kirill Asmanov
 * @since 16.10.2020
 */
@Repository
public interface PickedAnswerRepository extends JpaRepository<PickedAnswer, Long> {
    PickedAnswer getFirstByQuestion_Id(Long questionId);
}
