package ru.asmanov.quizzes.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

/**
 * Wrapper for a map containing user-selected answers for correct work with the views layer
 * @author Kirill Asmanov
 * @since 16.10.2020
 */
public class PickedAnswerMapWrapper {
    private Map<Long, Integer> pickedAnswerMap;
}
