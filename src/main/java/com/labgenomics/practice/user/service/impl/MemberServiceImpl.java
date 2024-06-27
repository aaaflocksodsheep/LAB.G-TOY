package com.labgenomics.practice.user.service.impl;

import com.labgenomics.practice.user.mapper.UserMapper;
import com.labgenomics.practice.user.model.User;
import com.labgenomics.practice.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author kaeun0320
 * @apiNote :
 * @since 2024-06-27
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void insertUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userMapper.insertUser(user);
    }
}
