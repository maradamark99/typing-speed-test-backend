package com.maradamark09.typingspeedtest.role;


import com.maradamark09.typingspeedtest.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

@Entity
@Table(name = "user_roles")
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String value;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<User> users;

}
