package com.labgenomics.practice.user.mapper;

import com.labgenomics.practice.user.model.User;
import com.labgenomics.practice.user.model.UserInfo;
import jdk.dynalink.linker.LinkerServices;

/**
 * @author kaeun0320
 * @apiNote :
 * @since 2024-06-26
 */

public interface UserMapper {
    UserInfo loadUserByUsername(String loginId);
    void insertUser(User user);
}
