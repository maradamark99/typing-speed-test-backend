package com.maradamark09.typingspeedtest.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByValueIgnoreCase(String value);
}
