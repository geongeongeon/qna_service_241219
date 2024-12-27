package com.sbs.qna_service.boundedContext.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/home/main")
    @ResponseBody
    public String showHome() {
        return "홈";
    }

    @GetMapping("/")
    public String root() {
        // redirect: 302
        // 302는 '이 경로를 찾아가보세요.'라고 응답
        return "redirect:/question/list";
    }
}
