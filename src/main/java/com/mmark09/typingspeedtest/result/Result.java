package com.mmark09.typingspeedtest.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@RequiredArgsConstructor
@Getter
@Setter
public class Result {
    @Id
    private String id;
    private String username;
    private int wpm;
    private float accuracy;
    private String date;
}
