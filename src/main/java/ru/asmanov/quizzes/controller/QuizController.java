package ru.asmanov.quizzes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.asmanov.quizzes.model.Question;
import ru.asmanov.quizzes.model.Quiz;
import ru.asmanov.quizzes.service.QuestionService;
import ru.asmanov.quizzes.service.QuizService;

import java.util.List;

@Controller
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String findAllQuizzes(Model model) {
        List<Quiz> quizzesList = quizService.findAllQuizzes();
        model.addAttribute("quizzesList", quizzesList);
        return "QuizList";
    }

    @GetMapping("/add")
    public String addQuizForm(Quiz quiz) {
        return "QuizAdd";
    }

    @PostMapping ("/add")
    public String addQuiz(Quiz quiz) {
        quizService.saveQuiz(quiz);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editQuizForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        model.addAttribute("quiz", quiz);
        return "QuizEdit";
    }

    @PostMapping("/edit")
    public String editQuiz(Quiz quiz) {
        quizService.saveQuiz(quiz);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}/questions")
    public String questionsOnQuizForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questions = questionService.findQuestionsByQuizId(id);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questionsList", questions);
        return "QuestionsAdd";
    }

    @GetMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable("id") Long id, Model model) {
        quizService.deleteQuizById(id);
        return "redirect:/";
    }
}
