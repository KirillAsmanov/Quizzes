package ru.asmanov.quizzes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.asmanov.quizzes.model.PickedAnswerMapWrapper;
import ru.asmanov.quizzes.model.Question;
import ru.asmanov.quizzes.model.Quiz;
import ru.asmanov.quizzes.repository.AnswerMapDao;
import ru.asmanov.quizzes.service.QuestionService;
import ru.asmanov.quizzes.service.QuizService;

import javax.validation.Valid;
import java.util.*;

@Controller
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private AnswerMapDao answerMapDao;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerMapDao = new AnswerMapDao();
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
    public String addQuiz(@ModelAttribute @Valid Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "QuizAdd";
        }
        quizService.saveQuiz(quiz);
        return String.format("redirect:/edit/%s/questions", quiz.getId());
    }

    @GetMapping("/edit/{id}")
    public String editQuizForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questionList = questionService.findQuestionsByQuizId(id);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questionsList", questionList);
        return "QuizEdit";
    }

    @PostMapping("/edit")
    public String editQuiz(@Valid Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "QuizEdit";
        }
        quizService.saveQuiz(quiz);
        return String.format("redirect:/edit/%s/questions", quiz.getId());
    }


    @GetMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable("id") Long id) {
        List<Question> questionList = questionService.findQuestionsByQuizId(id);
        for (Question q : questionList) {
            questionService.deleteQuestionById(q.getId());
        }
        quizService.deleteQuizById(id);
        return "redirect:/";
    }

    /* ---- Passing ---- */

    @GetMapping("/{id}")
    public String quizPassingForm(@PathVariable("id") Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questionList = questionService.findQuestionsByQuizId(id);
        Map<Long, Integer> pickedAnswers = new HashMap<>();
        for (Question q : questionList) {
            pickedAnswers.put(q.getId(), 0);
        }
        answerMapDao.setAnswerMap(pickedAnswers);
        PickedAnswerMapWrapper pickedMapWrapper = answerMapDao.getPickedAnswerMapWrapper();
        model.addAttribute("pickedMapWrapper", pickedMapWrapper);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questionsList", questionList);
        return "QuizPassing";
    }

    @PostMapping("/{id}/submit")
    public String submitQuizResult(@PathVariable("id") Long id,
                                   @ModelAttribute PickedAnswerMapWrapper pickedAnswerMapWrapper) {
        answerMapDao.setPickedAnswerMapWrapper(pickedAnswerMapWrapper);
        return String.format("redirect:/%s/result", id);
    }

    @GetMapping("/{id}/result")
    public String quizResultPage(@PathVariable("id") Long id,
                                 Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questionList = questionService.findQuestionsByQuizId(id);
        Map<Long, Integer> pickedAnswersMap = answerMapDao.getAnswerMap();
        Map<Question, Boolean> resultMap = new LinkedHashMap<>();

        System.out.println(pickedAnswersMap);
        
        int rightAnswers = 0;

        for (Question question : questionList) {
            Integer pickedOption = pickedAnswersMap.get(question.getId());
            boolean questionResult = false;
            if (pickedOption.equals(question.getRightAnswerNumber())) {
                questionResult = true;
                rightAnswers++;
            }
            resultMap.put(question, questionResult);
        }
        String score = rightAnswers + "/" + questionList.size();

        model.addAttribute("quiz", quiz);
        model.addAttribute("score", score);
        model.addAttribute("resultQuestionsMap", resultMap);
        return "QuizResult";
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
