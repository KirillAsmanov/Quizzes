package ru.asmanov.quizzes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Question model
 * @author Kirill Asmanov
 * @since 16.10.2020
 */

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Это поле не может быть пустым")
    @Column(name = "task")
    private String task;

    @Column(name = "right_answer_number")
    private Integer rightAnswerNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ElementCollection
    @CollectionTable(name = "answers", joinColumns = @JoinColumn(name = "question_id"))
    @MapKeyColumn(name = "number")
    @Column(name = "text")
    private Map<Integer, String> answers = new HashMap<>();

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "question")
    private PickedAnswer pickedAnswer;

    public Question() {
    }

    public Question(Long id, String task, Integer rightAnswerNumber, Quiz quiz, Map<Integer, String> answers) {
        this.id = id;
        this.task = task;
        this.rightAnswerNumber = rightAnswerNumber;
        this.quiz = quiz;
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(task, question.task) &&
                Objects.equals(rightAnswerNumber, question.rightAnswerNumber) &&
                Objects.equals(quiz, question.quiz) &&
                Objects.equals(answers, question.answers) &&
                Objects.equals(pickedAnswer, question.pickedAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, rightAnswerNumber, quiz, answers);
    }
}