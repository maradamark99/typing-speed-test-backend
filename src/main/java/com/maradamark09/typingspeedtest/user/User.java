package com.maradamark09.typingspeedtest.user;

import com.maradamark09.typingspeedtest.result.Result;
import com.maradamark09.typingspeedtest.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Result> results;

}
