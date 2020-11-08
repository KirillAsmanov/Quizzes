package ru.asmanov.quizzes.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.asmanov.quizzes.model.PickedAnswer;
import ru.asmanov.quizzes.model.Question;
import ru.asmanov.quizzes.model.Quiz;
import ru.asmanov.quizzes.repository.PickedAnswerRepository;
import ru.asmanov.quizzes.repository.QuestionRepository;
import ru.asmanov.quizzes.repository.QuizRepository;
import ru.asmanov.quizzes.service.QuestionService;
import ru.asmanov.quizzes.service.QuizService;
import ru.asmanov.quizzes.service.ResultService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuizController quizController;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuizService quizService;
    @MockBean
    private ResultService resultService;
    @MockBean
    private QuizRepository quizRepository;
    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private PickedAnswerRepository pickedAnswerRepository;

    @Test
    public void contextLoad() {
        assertThat(quizController).isNotNull();
    }

    @Test
    void findAllQuizzes() throws Exception {
        when(quizService.findAllQuizzes()).thenReturn(List.of(
                new Quiz(1L, "TestQuiz", "TestDescription1"),
                new Quiz(2L, "TestQuiz2", "TestDescription2")
        ));
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("QuizList"))
                .andExpect(content().string(containsString("TestQuiz")))
                .andExpect(content().string(containsString("TestQuiz2")))
                .andExpect(content().string(containsString("TestDescription1")))
                .andExpect(content().string(containsString("TestDescription2")));
    }

    @Test
    void addQuizForm() throws Exception {
        this.mockMvc.perform(get("/add")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("QuizAdd"));
    }

    @Test
    void addQuizWithInvalidValue_thenHasErrors() throws Exception {
        this.mockMvc.perform(post("/add")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
    }

    @Test
    void editQuizForm() throws Exception {
        when(quizService.findQuizById(10L)).thenReturn(
                new Quiz(10L, "TestQuiz", "TestDescription1")
        );

        this.mockMvc.perform(get("/edit/10")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("QuizEdit"))
                .andExpect(content().string(containsString("TestQuiz")))
                .andExpect(content().string(containsString("TestDescription1")));
    }

    @Test
    void editQuizWithInvalidValue_thenHasErrors() throws Exception {
        this.mockMvc.perform(post("/edit")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
    }

    @Test
    void quizPassingForm() throws Exception {
        Quiz quiz = new Quiz(0L, "TestQuiz", "TestDescription1");
        Map<Integer, String> answersForQuestion1 = new HashMap<>();
        answersForQuestion1.put(1, "TrueAnswer1");
        answersForQuestion1.put(2, "FalseAnswer1");
        Map<Integer, String> answersForQuestion2 = new HashMap<>();
        answersForQuestion2.put(2, "TrueAnswer2");
        answersForQuestion2.put(1, "FalseAnswer2");
        Question question1 = new Question(1L, "TestQuestion1", 1, quiz, answersForQuestion1);
        Question question2 = new Question(2L, "TestQuestion2", 2, quiz, answersForQuestion2);
        Set<Question> questions = Set.of(question1, question2);
        quiz.setQuestions(questions);

        when(quizService.findQuizById(0L)).thenReturn(quiz);
        when(questionService.findQuestionsByQuizId(0L)).thenReturn(new ArrayList<>(questions));

        this.mockMvc.perform(get("/0")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("QuizPassing"))
                .andExpect(content().string(containsString("TestQuiz")))
                .andExpect(content().string(containsString("TestDescription1")))
                .andExpect(content().string(containsString("TestQuestion1")))
                .andExpect(content().string(containsString("FalseAnswer1")))
                .andExpect(content().string(containsString("TrueAnswer1")))
                .andExpect(content().string(containsString("TestQuestion2")))
                .andExpect(content().string(containsString("TrueAnswer2")))
                .andExpect(content().string(containsString("FalseAnswer2")));

    }

    @Test
    void quizResultPageTest() throws Exception {
        Quiz quiz = new Quiz(0L, "TestQuiz", "TestDescription1");
        Map<Integer, String> answersForQuestion1 = new HashMap<>();
        answersForQuestion1.put(1, "TrueAnswer1");
        answersForQuestion1.put(2, "FalseAnswer1");
        Map<Integer, String> answersForQuestion2 = new HashMap<>();
        answersForQuestion2.put(1, "FalseAnswer2");
        answersForQuestion2.put(2, "TrueAnswer2");
        Question question1 = new Question(1L, "TestQuestion1", 1, quiz, answersForQuestion1);
        Question question2 = new Question(2L, "TestQuestion2", 2, quiz, answersForQuestion2);
        question1.setPickedAnswer(new PickedAnswer(1));
        question2.setPickedAnswer(new PickedAnswer(1));
        Set<Question> questions = Set.of(question1, question2);
        quiz.setQuestions(questions);

        when(quizService.findQuizById(0L)).thenReturn(quiz);
        when(questionService.findQuestionsByQuizId(0L)).thenReturn(new ArrayList<>(questions));
        when(resultService.countScore(0L)).thenReturn("1/2");

        this.mockMvc.perform(get("/0/result")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("QuizResult"))
                .andExpect(content().string(containsString("1/2")))
                .andExpect(content().string(containsString("TestQuestion1")))
                .andExpect(content().string(containsString("FalseAnswer1")))
                .andExpect(content().string(containsString("TrueAnswer1")))
                .andExpect(content().string(containsString("TestQuestion2")))
                .andExpect(content().string(containsString("TrueAnswer2")))
                .andExpect(content().string(containsString("FalseAnswer2")));

    }
}
