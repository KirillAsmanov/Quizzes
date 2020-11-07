package ru.asmanov.quizzes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.asmanov.quizzes.model.Question;
import ru.asmanov.quizzes.model.Quiz;
import ru.asmanov.quizzes.service.QuestionService;
import ru.asmanov.quizzes.service.QuizService;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller containing methods for working with questions
 * @author Kirill Asmanov
 * @since 13.10.2020
 */

@Controller
public class QuestionController {
    private final QuizService quizService;
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @GetMapping("/edit/{id}/questions")
    public String questionsOnQuizForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questionsList = questionService.findQuestionsByQuizId(id);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questionsList", questionsList);
        model.addAttribute("question", new Question());
        return "QuestionsAdd";
    }

    @PostMapping("/edit/{quiz_id}/questions/add")
    public String addQuestion(@PathVariable("quiz_id") Long quiz_id,
                              @ModelAttribute @Valid Question question,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return String.format("redirect:/edit/%s/questions", quiz_id);
        }
        Quiz quiz = quizService.findQuizById(quiz_id);
        question.setQuiz(quiz);
        questionService.saveQuestion(question);
        return String.format("redirect:/edit/%s/questions", quiz_id);
    }

    @GetMapping("/edit/{quiz_id}/questions/{question_id}")
    public String deleteQuestion(@PathVariable("quiz_id") Long quiz_id,
                                 @PathVariable("question_id") Long question_id) {
        questionService.deleteQuestionById(question_id);
        return String.format("redirect:/edit/%s/questions", quiz_id);
    }
}
