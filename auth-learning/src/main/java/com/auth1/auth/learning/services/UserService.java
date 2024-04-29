package com.auth1.auth.learning.services;

import com.auth1.auth.learning.dtos.SignupRequestDto;
import com.auth1.auth.learning.models.Token;
import com.auth1.auth.learning.models.User;
import com.auth1.auth.learning.repositories.TokenRepository;
import com.auth1.auth.learning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenRepository tokenRepository;
    public User signUp(String email,String password,String name){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        Optional<User> optionaluser = userRepository.findByEmail(email);
        if(optionaluser.isEmpty()){
            throw new RuntimeException("Invalid userOptional or password");
        }
        User user = optionaluser.get();
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Invalid userOptional or password");
        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(UUID.randomUUID().toString());

        Date expireDate = getExpiredDate();

        token.setExpireAt(expireDate);

        return tokenRepository.save(token);
    }

    private Date getExpiredDate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(new Date());

        //add(Calendar.DAY_OF_MONTH, -5).
        calendarDate.add(Calendar.DAY_OF_MONTH, 30);

        Date expiredDate = calendarDate.getTime();
        return expiredDate;
    }

    public void logout(String token) {
        // '1', '2024-04-25 21:58:17.639000', '65c338ac-69f6-43a4-b460-dd4dd3e39534', '1'

        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(token, false);

        if (tokenOptional.isEmpty()) {
            throw new RuntimeException("Token is invalid ");
        }

        Token tokenObject = tokenOptional.get();
        tokenObject.setDeleted(true);

        tokenRepository.save(tokenObject);
    }

    public boolean validateToken(String token) {

        /*
          To validate token
          1. Check if token value is present
          2. Check if token is not deleted
          3. Check if token is not expired
         */

        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEqualsAndExpireAtGreaterThan(
                token, false, new Date()
        );

        if (tokenOptional.isEmpty()) {
            return false;
        }
        return true;
    }
}
