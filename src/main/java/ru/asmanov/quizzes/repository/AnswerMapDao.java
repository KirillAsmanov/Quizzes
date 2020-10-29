package ru.asmanov.quizzes.repository;

import ru.asmanov.quizzes.model.PickedAnswerMapWrapper;

import java.util.Map;

/**
 * DAO for working with selected answers
 * @author Kirill Asmanov
 * @since 16.10.2020
 */

public class AnswerMapDao {
    private PickedAnswerMapWrapper pickedAnswerMapWrapper;

    public AnswerMapDao() {
        this.pickedAnswerMapWrapper = new PickedAnswerMapWrapper();
    }

    public PickedAnswerMapWrapper getPickedAnswerMapWrapper() {
        return pickedAnswerMapWrapper;
    }

    public void setPickedAnswerMapWrapper(PickedAnswerMapWrapper pickedAnswerMapWrapper) {
        this.pickedAnswerMapWrapper = pickedAnswerMapWrapper;
    }

    public void setAnswerMap(Map<Long, Integer> pickedAnswers) {
        pickedAnswerMapWrapper.setPickedAnswerMap(pickedAnswers);
    }

    public Map<Long, Integer> getAnswerMap() {
        return pickedAnswerMapWrapper.getPickedAnswerMap();
    }
}
