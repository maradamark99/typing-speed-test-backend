package com.maradamark09.typingspeedtest.exception;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;
public class WordLengthGreaterThanDifficultyException extends RuntimeException {
    public WordLengthGreaterThanDifficultyException(String msg){
        super(msg);
    }

    public WordLengthGreaterThanDifficultyException(String value, Difficulty difficulty){
        this(buildMessage(value, difficulty));
    }
    private static String buildMessage(String value, Difficulty difficulty){
        return "The given word: {" + value +
                "} is not suitable for the provided difficulty: {" + difficulty + "}. " +
                "The given word's length: {" + value.length() + "} is greater than the maximum accepted" +
                " length of the given difficulty: {" +
                difficulty.getMaxWordLength() +"}.";
    }
}
