package com.maradamark09.typingspeedtest.result;
public record ResultResponse(Long id,
                             Short wpm,
                             Double accuracy,
                             String username) { }
