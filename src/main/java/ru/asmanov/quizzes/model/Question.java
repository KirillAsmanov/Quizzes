package ru.asmanov.quizzes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.validator.constraints.NotBlank;

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
}