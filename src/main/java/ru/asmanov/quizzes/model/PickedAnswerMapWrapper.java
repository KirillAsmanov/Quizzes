package ru.asmanov.quizzes.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PickedAnswerMapWrapper {
    private Map<Question, Integer> pickedAnswerMap;

    public PickedAnswerMapWrapper(Map<Question, Integer> pickedAnswerMap) {
        this.pickedAnswerMap = pickedAnswerMap;
    }
}
