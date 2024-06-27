package com.labgenomics.practice.user.service;

import com.labgenomics.practice.user.mapper.UserMapper;
import com.labgenomics.practice.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.Optional;

/**
 * @author kaeun0320
 * @apiNote :
 * @since 2024-06-26
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Optional<UserInfo> user = Optional.ofNullable(userMapper.loadUserByUsername(username));
        UserInfo userInfo = user.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));

        return User.builder()
        	.username(userInfo.getLoginId())
            .password(userInfo.getPassword())
            .roles(userInfo.getRole())
            .build();
    }
}
