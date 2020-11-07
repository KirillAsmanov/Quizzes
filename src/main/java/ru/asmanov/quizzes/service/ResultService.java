package ru.asmanov.quizzes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.asmanov.quizzes.model.PickedAnswer;
import ru.asmanov.quizzes.model.Question;
import ru.asmanov.quizzes.repository.PickedAnswerRepository;
import ru.asmanov.quizzes.repository.QuestionRepository;

import java.util.List;
import java.util.Map;

/**
 * Service for working with the result of passing the quiz
 */
@Service
public class ResultService {
    private final QuestionRepository questionRepository;
    private final PickedAnswerRepository pickedAnswerRepository;

    @Autowired
    public ResultService(QuestionRepository questionRepository,
                         PickedAnswerRepository pickedAnswerRepository) {
        this.questionRepository = questionRepository;
        this.pickedAnswerRepository = pickedAnswerRepository;
    }

    /**
     * Adds picked answer into db
     * @param question question
     * @param pickedAnswerNum number of picked answer
     */
    public void addPickedAnswer(Question question, int pickedAnswerNum) {
        PickedAnswer pickedAnswer = pickedAnswerRepository.getFirstByQuestion_Id(question.getId());
        if (pickedAnswer == null) {
            pickedAnswer = new PickedAnswer(pickedAnswerNum);
            question.setPickedAnswer(pickedAnswer);
            pickedAnswer.setQuestion(question);
            questionRepository.save(question);
        } else {
            pickedAnswer.setPickedAnswerNumber(pickedAnswerNum);
            pickedAnswerRepository.save(pickedAnswer);
        }
    }

    /**
     * Counts the points scored in the quiz
     * @param quizId - id of passed quiz
     * @return string score like "7/10"
     */
    public String countScore(Long quizId) {
        List<Question> questionList = questionRepository.findQuestionsByQuizId(quizId);
        int rightAnswers = 0;
        for (Question question : questionList) {
            Integer pickedOption = question.getPickedAnswer().getPickedAnswerNumber();
            if (pickedOption.equals(question.getRightAnswerNumber())) {
                rightAnswers++;
            }
        }
        return rightAnswers + "/" + questionList.size();
    }

    /**
     * adds values to the database from the map of selected answers
     * @param pickedAnswerMap map with picked answers
     * @param quizId id of quiz
     */
    public void addPickedAnswerFromMap(Map<Long, Integer> pickedAnswerMap, Long quizId) {
        List<Question> questionList = questionRepository.findQuestionsByQuizId(quizId);
        for (Question question : questionList) {
            int pickedAnswerNumber = pickedAnswerMap.get(question.getId());
            addPickedAnswer(question, pickedAnswerNumber);
        }
    }

}
