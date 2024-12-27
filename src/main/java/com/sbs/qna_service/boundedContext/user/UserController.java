package com.sbs.qna_service.boundedContext.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/create")
    public void createUser() {
        userService.createUser();
    }

    @GetMapping("/read")
    public List<User> readUser() {
        return userService.readUser();
    }

    @GetMapping("/update")
    public void updateUser() {
        userService.updateUser();
    }

    @GetMapping("/delete")
    public void deleteUser() {
        userService.deleteUser();
    }
}
