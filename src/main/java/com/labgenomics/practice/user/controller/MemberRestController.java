package com.labgenomics.practice.user.controller;

import com.labgenomics.practice.user.model.User;
import com.labgenomics.practice.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaeun0320
 * @apiNote :
 * @since 2024-06-27
 */

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;


    @PostMapping("/sign-up")
    public void insertUser(@RequestBody User user){
        memberService.insertUser(user);
   }
}
