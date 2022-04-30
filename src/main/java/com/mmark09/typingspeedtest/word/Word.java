package com.mmark09.typingspeedtest.word;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class Word {
    @Id
    String id;
    String word;
    Difficulty difficulty;
}
