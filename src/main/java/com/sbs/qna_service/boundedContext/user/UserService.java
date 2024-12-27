package com.sbs.qna_service.boundedContext.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser() {
        User user = new User();
        user.setName("홍길동");
        userRepository.save(user);
    }

    public List<User> readUser() {
        return userRepository.findAll();
    }

    public void updateUser() {
        Optional<User> user = userRepository.findById(1);
        user.ifPresent(usr -> {
            usr.setName("홍길순");
            userRepository.save(usr);
        });
    }

    public void deleteUser() {
        userRepository.deleteAll();
    }
}
