package com.auth1.auth.learning.repositories;

import com.auth1.auth.learning.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByValueAndDeletedEquals(String token, boolean b);

    Optional<Token> findByValueAndDeletedEqualsAndExpireAtGreaterThan(String token, boolean isDeleted, Date date);
}
