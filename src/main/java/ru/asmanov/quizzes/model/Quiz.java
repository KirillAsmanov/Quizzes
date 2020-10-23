package ru.asmanov.quizzes.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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

    public Quiz(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
