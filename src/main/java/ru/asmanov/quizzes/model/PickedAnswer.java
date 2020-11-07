package ru.asmanov.quizzes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Picked answers model
 * @author Kirill Asmanov
 * @since 16.10.2020
 */
@Getter
@Setter
@Entity
@Table(name = "picked_answers")
public class PickedAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "picked_answer_number")
    private Integer pickedAnswerNumber;

    public PickedAnswer() {
    }

    public PickedAnswer(Integer pickedAnswerNumber) {
        this.pickedAnswerNumber = pickedAnswerNumber;
    }
}
