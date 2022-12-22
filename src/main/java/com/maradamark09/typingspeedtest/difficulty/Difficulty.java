package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.word.Word;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name="difficulties")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(unique = true)
    String value;

    @NotNull
    Byte maxWordLength;

    @OneToMany(mappedBy = "difficulty")
    private Set<Word> words;
}
