package ru.asmanov.quizzes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Quiz model
 * @author Kirill Asmanov
 * @since 16.10.2020
 */

@Entity
@Getter
@Setter
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Это поле не может быть пустым")
    @Size(min=3, max=30, message = "Недопустимое количество символов")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Это поле не может быть пустым")
    @Size(min=3, max=110, message = "Недопустимое количество символов")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

    public Quiz() {
    }

    public Quiz(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(id, quiz.id) &&
                Objects.equals(name, quiz.name) &&
                Objects.equals(description, quiz.description) &&
                Objects.equals(questions, quiz.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
