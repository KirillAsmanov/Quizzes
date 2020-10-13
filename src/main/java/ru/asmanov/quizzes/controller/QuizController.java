package ru.asmanov.quizzes.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.asmanov.quizzes.model.PickedAnswerMapWrapper;
import ru.asmanov.quizzes.model.Question;
import ru.asmanov.quizzes.model.Quiz;
import ru.asmanov.quizzes.service.QuestionService;
import ru.asmanov.quizzes.service.QuizService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    /* ---- Quizzes ---- */

    @GetMapping("/")
    public String findAllQuizzes(Model model) {
        List<Quiz> quizzesList = quizService.findAllQuizzes();
        model.addAttribute("quizzesList", quizzesList);
        return "QuizList";
    }

    @GetMapping("/add")
    public String addQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("questions", new HashSet<Question>());
        return "QuizAdd";
    }

    @PostMapping ("/add")
    public String addQuiz(@ModelAttribute Quiz quiz) {
        quizService.saveQuiz(quiz);
        return String.format("redirect:/edit/%s/questions", quiz.getId());
    }

    @GetMapping("/edit/{id}")
    public String editQuizForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questions = questionService.findQuestionsByQuizId(id);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questionsList", questions);
        return "QuizEdit";
    }

    @PostMapping("/edit")
    public String editQuiz(Quiz quiz) {
        quizService.saveQuiz(quiz);
        return String.format("redirect:/edit/%s/questions", quiz.getId());
    }


    @GetMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable("id") Long id) {
        List<Question> questions = questionService.findQuestionsByQuizId(id);
        for (Question q : questions) {
            questionService.deleteQuestionById(q.getId());
        }
        quizService.deleteQuizById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String testPassingStartForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questions = questionService.findQuestionsByQuizId(id);
        Map<Question, Integer> pickedAnswers = new HashMap<>();
        for (Question q : questions) {
            pickedAnswers.put(q, 0);
        }
        PickedAnswerMapWrapper pickedMapWrapper = new PickedAnswerMapWrapper(pickedAnswers);
        model.addAttribute("pickedMapWrapper", pickedMapWrapper);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questionsList", questions);
        return "TestPassingStart";
    }

    @PostMapping("/testSubmit")
    public String submitResult(@ModelAttribute PickedAnswerMapWrapper pickedAnswerMapWrapper) {
        Map<Question, Integer> pickedAnswers = pickedAnswerMapWrapper.getPickedAnswerMap();
        pickedAnswers.forEach((q, i) -> System.out.println(q.getRightAnswerId() + " / " + i + System.lineSeparator()));
        return "redirect:/";
    }



    /* ---- Questions ----*/

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
                              @ModelAttribute Question question) {
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
