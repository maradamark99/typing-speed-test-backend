package com.maradamark09.typingspeedtest.role;


import com.maradamark09.typingspeedtest.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "user_roles")
@ToString(exclude = {"id", "users"})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String value;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

}
